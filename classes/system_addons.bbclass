# class that handles checking and packaging the dSS add ons

SYSADDONPN_OLD = "${@bb.data.getVar('PN', d, True)[13:]}"
SYSADDONPN = "${PN}"
RDEPENDS_${SYSADDONPN}_append = " busybox libxml2-utils addon-framework"

PACKAGE_ARCH="all"
FILES_${PN} = "${datadir}/dss/add-ons/${PN} \
               ${datadir}/dss/data/subscriptions.d \
                /www/pages/add-ons/${PN} \
               "

addtask check after do_compile before do_build

system_addons_postinst() {
if [ -f ${datadir}/dss/data/savedprops/${SYSADDONPN_OLD}.xml ]; then
    echo "Migrating old configuration: ${SYSADDONPN_OLD} -> ${SYSADDONPN}.xml"
    cat ${datadir}/dss/data/savedprops/${SYSADDONPN_OLD}.xml | sed -n '1 h; 2,$ H; $ {x;s/${SYSADDONPN_OLD}/${SYSADDONPN}/;p}' > ${datadir}/dss/data/savedprops/${SYSADDONPN}.xml
    if [ -x /usr/bin/xmllint ]; then
        /usr/bin/xmllint --noout ${datadir}/dss/data/savedprops/${SYSADDONPN}.xml >/dev/null 2>&1
        if [ $? -ne 0 ]; then
            echo "Invalid XML for ${SYSADDONPN}.xml, moving to ${SYSADDONPN}.xml.error"
            mv ${datadir}/dss/data/savedprops/${SYSADDONPN}.xml ${datadir}/dss/data/savedprops/${SYSADDONPN}.xml.error
        fi
    fi
    echo "Disabling old addon (removing subscription)"
    rm -f ${datadir}/dss/data/subscriptions.d/${SYSADDONPN_OLD}.xml
fi

sv restart dss
exit 0
}

system_addons_postrm() {
sv restart dss
exit 0
}

def check_config(config, scripts, d):
    import bb
    import xml.dom.minidom
    import os
    import traceback
    import re

    rex = re.compile("filename[0-9]$")
    src = bb.data.getVar('S', d, True)
    pn = bb.data.getVar('PN', d, True)
    dsslibs = []
    l = bb.data.getVar('DSS_JS_LIBRARIES', d, True) or ""
    if (len(l) > 0):
        dsslibs = l.split()
 
    DEFAULT_PATH = os.path.join("/usr/share/dss/add-ons", pn)
    DSS_LIB_PATH = "/usr/share/dss/data/scripts"

    try:
        cfgpath = os.path.join(src, 'config')
        dom = xml.dom.minidom.parse(os.path.join(cfgpath, pn + ".xml"))

        subscriptions = dom.getElementsByTagName("subscription")
        if len(subscriptions) == 0:
            bb.fatal("Script files available but no subscriptions defined")

        found_scripts = {}
        for script in scripts:
            found_scripts[script] = False

        for subscription in subscriptions:
            if subscription.getAttribute("handler-name") == "javascript":
                params = subscription.getElementsByTagName("parameter")
                for param in params:
                    m = rex.match(param.getAttribute("name"))
                    if m is not None:
                        if not param.hasChildNodes():
                            bb.fatal("Missing script filename!")

                        if param.firstChild.nodeType == param.firstChild.TEXT_NODE:
                            name = os.path.basename(param.firstChild.data)
                            dir = os.path.dirname(param.firstChild.data)
                            if (dir == DSS_LIB_PATH):
                                if name not in dsslibs:
                                    bb.fatal("Invalid script referenced: " + param.firstChild.data)

                            else:
                                found_scripts[name] = True
                                param.firstChild.data = os.path.join(DEFAULT_PATH, name)


        for script in scripts:
            if not script in found_scripts.keys():
                bb.fatal("Script " + script + " is available in the package but missing in configuration!")

            if found_scripts[script] == False:
                bb.fatal("Script " + script + " is available in the package but missing in configuration!")

        for key in found_scripts.keys():
            if not key in scripts:
                bb.fatal("Script " + key + " is specified in configuration but is missing in the package")

        xmlstr = dom.toxml()
        f = open(os.path.join(cfgpath, pn + ".xml"), "w")
        f.write(xmlstr)
        f.close()

    except IOError:
        traceback.print_exc()
        bb.fatal("Failed to open/read configuration file")
    except (TypeError, AttributeError):
        traceback.print_exc()
        bb.fatal("Failed to parse configuration file")
    except:
        traceback.print_exc()
        bb.fatal("Failed to process configuration file")
    
def check_links_callback(arg, dirname, filenames):
    import bb
    for file in filenames:
        if os.path.islink(file):
            bb.fatal("Filesystem links are not allowed")
        if file.endswith(".hs") or file.endswith(".sh"):
            bb.fatal("Shell and haserl scripts are not allowed")
        
python system_addons_do_check() {
    import bb
    src = bb.data.getVar('S', d, True)
    pn = bb.data.getVar('PN', d, True)

    bb.note("Checking package name")
    if not pn.startswith('system-addon-'):
        bb.fatal("""Illegal package name "%s", must start with "system-addon-" string!""" % pn)

    bb.note("Checking add-on package sanity")

    arglist = []
    os.path.walk(src, check_links_callback, arglist)

    if not os.path.isdir(os.path.join(src, 'ui')):
        bb.fatal("""Check failed, required path "ui" not in package!""")

    if not (os.path.isfile(os.path.join(os.path.join(src, 'ui'), 'index.html'))):
        bb.fatal("An entry web page is required, missing index.html");

    if not (os.path.isfile(os.path.join(os.path.join(src, 'ui'), 'default_icon.png'))):
        os.symlink('../../images/default_icon.png', os.path.join(os.path.join(src, 'ui'), 'default_icon.png'))

    scripts = []
    if os.path.isdir(os.path.join(src, 'scripts')):
        scripts = os.listdir(os.path.join(src, 'scripts'))
        if len(scripts) > 0:
            for script in scripts:
                if not script.endswith('.js'):
                    bb.fatal("""Found illegal script "%s" in scripts directory! Filename must have .js extension""" % script)
        else:
            try:
                os.rmdir(os.path.join(src, 'scripts'))
            except:
                bb.fatal("Failed to remove empty scripts directory")

    config = []
    if os.path.isdir(os.path.join(src, 'config')):
        configs = os.listdir(os.path.join(src, 'config'))
        if len(configs) == 0:
            if len(scripts) > 0:
                bb.fatal("Scripts available, but configuration file is missing!")
            try:
                os.rmdir(os.path.join(src, 'config'))
            except:
                bb.fatal("Failed to remove empty config directory")
        elif len(configs) == 1:
            if configs[0] != pn + '.xml':
                bb.fatal("Illegal configuration file name, must be: " + pn + '.xml' + " but got " + configs[0])

            config = configs[0]
        elif len(configs) > 1:
            bb.fatal("Only one configuration file is allowed!")
    else:
        if len(scripts) > 0:
            bb.fatal("Scripts available, but configuration file is missing!")

    if len(config) > 0:
        check_config(config, scripts, d)
}

system_addons_do_configure() {
    :
}

system_addons_do_install() {
    install -d ${D}${datadir}/dss/add-ons/${PN}
    if [[ -d ${S}/scripts ]]; then
    install -d ${D}${datadir}/dss/add-ons/${PN}
        install -m 0644 ${S}/scripts/*.js ${D}${datadir}/dss/add-ons/${PN}/
    fi

    if [[ -d ${S}/config ]]; then
        install -d ${D}${datadir}/dss/data/subscriptions.d/
        install -m 0644 ${S}/config/${PN}.xml ${D}${datadir}/dss/data/subscriptions.d/${PN}.xml
    fi
    install -d ${D}/www/pages/add-ons/${PN}
    cp -a ${S}/ui/* ${D}/www/pages/add-ons/${PN}/
}

system_addons_do_compile() {
    :
}

system_addons_do_build() {
    :
}


python __anonymous() {
    import urllib

    meta_fields = [ "name", "descshort", "extradesc", "description" ]
    locales = []
    tags = {}
    l = bb.data.getVar('DSS_SYSTEM_ADDON_LOCALES', d, True)
    if l is None:
        bb.fatal("Can not collect package metadata, no locales defined")

    if (len(l) > 0):
        locales = l.split()
    else:
        bb.fatal("Can not collect package metadata, no locales defined")

    for locale in locales:
        for field in meta_fields:
            tag = bb.data.getVar('%s_%s' % (field, locale), d, 1)
            if (tag is not None) and (len(tag) > 0):
                tags['%s_%s' % (field, locale)] = tag
                
    if (len(tags) > 0):
        bb.data.setVar('PACKAGE_TAGS',  '&'.join(['='.join((urllib.quote(k), urllib.quote(v))) for k,v in tags.iteritems()]), d)
}

python populate_packages_prepend () {
    packages = bb.data.getVar('PACKAGES', d, 1).split()

    for pkg in packages:
        bb.debug(1, 'adding system_addons calls to postinst/postrm for %s' % pkg)

        postinst = bb.data.getVar('pkg_postinst_%s' % pkg, d, 1) or bb.data.getVar('pkg_postinst', d, 1)
        if not postinst:
            postinst = '#!/bin/sh\n'
        postinst += bb.data.getVar('system_addons_postinst', d, 1)
        bb.data.setVar('pkg_postinst_%s' % pkg, postinst, d)

        postrm = bb.data.getVar('pkg_postrm_%s' % pkg, d, 1) or bb.data.getVar('pkg_postrm', d, 1)
        if not postrm:
            postrm = '#!/bin/sh\n'
        postrm += bb.data.getVar('system_addons_postrm', d, 1)
        bb.data.setVar('pkg_postrm_%s' % pkg, postrm, d)
}

EXPORT_FUNCTIONS do_configure do_install do_compile do_build do_check

