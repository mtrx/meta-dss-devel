# scene responder
LICENSE="GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"
HOMEPAGE="http://www.aizo.com/"
MAINTAINER="Reto Fluetsch <reto.fluetsch@aizo.com>"

SRC_URI = "git://gitorious.digitalstrom.org/dss-add-ons/scene-responder.git;protocol=git;branch=master" 

inherit system_addons gitpkgv

PR="r2"
SRCREV="${AUTOREV}"
PKGV = "1.3.9+gitr${GITPKGV}"
PV = "${PKGV}"
S="${WORKDIR}/git"

name_de_DE="Scene Responder"
descshort_de_DE="Mit dieser App lösen Sie mit einem Tastendruck weiterführende Aktivitäten aus."
description_de_DE="Diese App dient zur Konfiguration von Aktionen aufgrund von Aktivitäten im System."

name_fr_CH="Gestionnaire d'Événements"
descshort_fr_CH="Avec cette app, par appui sur un bouton, vous déclenchez une activité à suivre."
description_fr_CH="Cette app sert à configurer des actions en fonction d'activités ayant lieu dans l'installation."

name_en_US="Event Responder"
descshort_en_US="Allows the user to configure additional actions to be called in response to system events."
description_en_US="This app allows the user to configure additional actions to be called in response to system events."

name_nl_NL="Scene Responder"
descshort_nl_NL="Mit dieser App lösen Sie mit einem Tastendruck weiterführende Aktivitäten aus."
description_nl_NL="Diese App dient zur Konfiguration von Aktionen aufgrund von Aktivitäten im System."

name_tr_TR = "Senaryo Yanıtlayıcı"
descshort_tr_TR = "Bu App sayesinde tek bir dokunuş ile istediğiniz faaliyeti açıp kapatabilirsiniz."
description_tr_TR = "Bu App etkinliklere göre aktiviteleri yapılandırma için kullanılır."

