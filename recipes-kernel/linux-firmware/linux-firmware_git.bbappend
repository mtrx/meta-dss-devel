PRINC = "1"

PACKAGES =+ "${PN}-rt28x0 ${PN}-rt2561 ${PN}-rt2661"

LICENSE_${PN}-rt28x0 = "Firmware:LICENCE.ralink-firmware.txt"
FILES_${PN}-rt28x0 = " \
  LICENCE.ralink-firmware.txt \
  /lib/firmware/rt2860.bin \
  /lib/firmware/rt2870.bin \
  /lib/firmware/rt3070.bin \
  /lib/firmware/rt3071.bin \
  /lib/firmware/rt3090.bin \
"

LICENSE_${PN}-rt2561 = "Firmware:LICENCE.ralink-firmware.txt"
FILES_${PN}-rt2561 = " \
  /lib/firmware/rt2561.bin \
  /lib/firmware/rt2561s.bin \
"

LICENSE_${PN}-rt2661 = "Firmware:LICENCE.ralink-firmware.txt"
FILES_${PN}-rt2661 = " \
  /lib/firmware/rt2661.bin \
"

