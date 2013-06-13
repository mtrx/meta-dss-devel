# timed events add on
MAINTAINER="Roman Koehler <roman.koehler@aizo.com>"
LICENSE="GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"
HOMEPAGE="http://www.aizo.com/"
SRC_URI = "git://gitorious.digitalstrom.org/dss-add-ons/hle-edit.git;protocol=git;branch=master" 

inherit system_addons gitpkgv

PR="r2"
SRCREV="${AUTOREV}"
PKGV = "1.3.1+gitr${GITPKGV}"
PV = "${PKGV}"
S="${WORKDIR}/git"

name_de_DE="Benutzerdefinierte Handlungen"
descshort_de_DE="Definieren Sie mit dieser App Handlungen, welche von anderen Apps ausgeführt werden."
description_de_DE="Definieren sie mit dieser App wichtige Handlungen, welche Sie dann ganz einfach aus anderen Apps oder auf dem Smartphone aufrufen können."

name_fr_CH="Actions Personnalisées"
descshort_fr_CH="Avec cette app, vous définissez des activités à exécuter par d'autres apps."
description_fr_CH="Avec cette app, vous définissez des activités importantes que vous pouvez déclencher à partir d'autres apps ou sur votre smartphone."

name_en_US="User Defined Actions"
descshort_en_US="This app allows the user to configure actions which can be called from other apps."
description_en_US="This app allows the user to define important, user-defined actions which can easily be called by other apps or a mobile phone."

name_nl_NL="Benutzerdefinierte Handlungen"
descshort_nl_NL="Definieren Sie mit dieser App Handlungen, welche von anderen Apps ausgeführt werden."
description_nl_NL="Definieren sie mit dieser App wichtige Handlungen, welche Sie dann ganz einfach aus anderen Apps oder auf dem Smartphone aufrufen können."

name_tr_TR = "Özel eylemler"
descshort_tr_TR = "Diğer App`ler tarafından gerçekleştirilen aktiviteleri bu App üzerinden ayarlayabilirsiniz"
description_tr_TR = "Bu App ile çeşitli Dikkat etkinlikleri tanımlayabilir ve sonrada kolayca bir başka App veya akıllı telefon üzerinden çağırabilirsiniz."
