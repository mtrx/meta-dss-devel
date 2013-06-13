# presence-simulator
MAINTAINER = "Roman Koehler <rk@aizo.ag>"
HOMEPAGE = "http://www.aizo.com/"
LICENSE="GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"
SRC_URI = "git://gitorious.digitalstrom.org/dss-add-ons/presence-simulator.git;protocol=git;branch=master"

inherit system_addons gitpkgv

PR = "r2"
SRCREV="${AUTOREV}"
PKGV = "1.3.8+gitr${GITPKGV}"
PV = "${PKGV}"
S="${WORKDIR}/git"

name_de_DE="Anwesenheitssimulation"
descshort_de_DE="Mit dieser App simulieren Sie glaubwürdig Ihre Anwesenheit in Ihrem Heim."
description_de_DE="Mit dieser App simulieren Sie Ihre Anwesenheit in Ihrem Heim, z. B. durch automatisches Schalten von Licht oder Öffnen und Schliessen von Rollladen und Jalousien."

name_fr_CH="Simulation de Présence"
descshort_fr_CH="Avec cette app vous simulez votre présence chez vous de façon crédible."
description_fr_CH="Avec cette app, vous simulez votre présence chez vous, par exemple en commutant automatiquement l'éclairage ou en actionnant vos volets roulants ou stores à lamelles."

name_en_US="Presence Simulation"
descshort_en_US="This app simulates someone being home."
description_en_US="This app allows one to simulate presence in a home, for example, by automatically switching lights or opening and closing shutters and blinds."

name_nl_NL="Anwesenheitssimulation"
descshort_nl_NL="Mit dieser App simulieren Sie glaubwürdig Ihre Anwesenheit in Ihrem Heim."
description_nl_NL="Mit dieser App simulieren Sie Ihre Anwesenheit in Ihrem Heim, z. B. durch automatisches Schalten von Licht oder Öffnen und Schliessen von Rollladen und Jalousien."

name_tr_TR = "Durum simülasyonu"
descshort_tr_TR = "Bu App sayesinde evinizdeki varlığınız inandırıcı bir şekilde simülasyon edilebilir."
description_tr_TR = "Bu App sayesinde evinizdeki varlığınız inandırıcı bir şekilde simülasyon edilebilir. Örneğin belirli odalardaki ışıklarınız belirli saatlerde açılıp kapatılabilir veya kepenk ve panjurlarınız sabahları açılıp akşamları belirli saatlerde kapatılabilir, ve böylece evde birilerinin kaldığı simüle edilebilir."

