PACKAGES += " userconfighandlers"

ALLOW_EMPTY_userconfighandlers = "1"

RDEPENDS_userconfighandlers += " bash python"

do_configure_prepend () {
} 

do_compile_prepend () {
}

do_install_prepend () {
	install -d -m 0755 ${D}/opt/cmframework/userconfighandlers
    find userconfighandlers -name '*.py' -exec cp {} ${D}/opt/cmframework/userconfighandlers \;
}

FILES_userconfighandlers = " \
	/opt/cmframework/userconfighandlers \
	"
