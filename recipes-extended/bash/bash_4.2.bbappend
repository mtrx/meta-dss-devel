PRINC="1"

pkg_prerm_${PN}() {
    #!/bin/sh
    update-alternatives --remove sh /bin/bash
}

pkg_postrm_${PN}() {
    #!/bin/sh
    exit 0
}
