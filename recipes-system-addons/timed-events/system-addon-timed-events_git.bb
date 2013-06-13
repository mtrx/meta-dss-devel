# timed events add on
MAINTAINER="Roman Koehler <rk@aizo.ag>"
LICENSE="GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"
HOMEPAGE="http://www.aizo.com/"
SRC_URI = "git://gitorious.digitalstrom.org/dss-add-ons/timed-events.git;protocol=git;branch=master" 

inherit system_addons gitpkgv

PR="r2"
SRCREV="${AUTOREV}"
PKGV = "1.3.7+gitr${GITPKGV}"
PV = "${PKGV}"
S="${WORKDIR}/git"

name_de_DE="Zeitschaltuhr"
descshort_de_DE="Ereignisse wie 'Licht einschalten' werden zu festgelegten Zeiten automatisch ausgeführt."
description_de_DE="Ereignisse wie Licht ein- oder ausschalten können zu festgelegten Zeiten oder auch in Abhängigkeit von Sonnenaufgang bzw. Sonnenuntergang mit dieser App automatisch erfolgen."

name_fr_CH="Programmateur Horaire"
descshort_fr_CH="Des actions telles que "
description_fr_CH="Des événements tels que la commutation de l'éclairage peuvent être programmés en fonction du lever ou du coucher du soleil."

name_en_US="Timers"
descshort_en_US="Allows actions, such as turning lights on or off, to be performed at predetermined times."
description_en_US="This app enables the user to configure activites such as turning on or off lights, to be performed at specified times.  Times can be specified with terms such as &quot;sunrise&quot; and &quot;sunset&quot; which are location-aware."

name_nl_NL="Zeitschaltuhr"
descshort_nl_NL="Ereignisse wie 'Licht einschalten' werden zu festgelegten Zeiten automatisch ausgeführt."
description_nl_NL="Ereignisse wie Licht ein- oder ausschalten können zu festgelegten Zeiten oder auch in Abhängigkeit von Sonnenaufgang bzw. Sonnenuntergang mit dieser App automatisch erfolgen."

name_tr_TR = "Zamanlayıcı"
descshort_tr_TR = "Bu App sayesinde bir çok etkinlikleri otomatik olarak açıp kapatabilirsiniz."
description_tr_TR = "Bu App sayesinde 'Aydinlatma,' gibi bir çok etkinlikleri belirtilen zamanlarda otomatik olarak açıp kapatabilir veya gün doğumu ve gün batımı gibi zamanlamalarla kepenk ve panjurlarınız otomatik olarak açıp kapatabilirsiniz."

