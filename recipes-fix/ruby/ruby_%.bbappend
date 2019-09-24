SRC_URI_remove = "file://CVE-2018-1000073.patch"

DEPENDS += "libffi"
DEPENDS_class-native += "readline-native"

EXTRA_OECONF += "--with-pkg-config=pkg-config"
