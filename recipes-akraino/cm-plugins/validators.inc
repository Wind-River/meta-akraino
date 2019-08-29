PACKAGES += " validators"

RDEPENDS_validators += " bash python"

do_configure_prepend () {
} 

do_compile_prepend () {
}

do_install_prepend () {
	install -d -m 0755 ${D}/opt/cmframework/validators
    find validators -name '*.py' -exec cp {} ${D}/opt/cmframework/validators \;
}

FILES_validators = " \
	/opt/cmframework/validators \
	"
