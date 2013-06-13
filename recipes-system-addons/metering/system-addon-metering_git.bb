# metering
LICENSE="CLOSED"
HOMEPAGE="http://www.aizo.com/"

inherit system_addons gitpkgv

SRC_URI = "git://gitorious.digitalstrom.org/dss-add-ons/power-metering.git;protocol=git;branch=master"

PR="r2"
SRCREV="${AUTOREV}"
PKGV = "1.9.90+gitr${GITPKGV}"
PV = "${PKGV}"
S="${WORKDIR}/git"

name_de_DE="Verbrauchsanzeige"
description_de_DE="Diese App zeigt den Energieverbrauch pro Stromkreis an."

name_fr_CH="Visualisation de l'Énergie Consommée"
description_fr_CH="Cette app affiche la consommation énergétique par circuit électrique."

name_en_US="Energy Graph"
description_en_US="This app displays the energy consumption per power circuit."

name_nl_NL="Verbrauchsanzeige"
description_nl_NL="Diese App zeigt den Energieverbrauch pro Stromkreis an."

name_tr_TR = "Tüketim göstergesi"
description_tr_TR = "Bu App devre başına enerji tüketimini gösterir."

