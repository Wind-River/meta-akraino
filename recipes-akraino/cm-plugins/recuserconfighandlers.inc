PACKAGES += " recuserconfighandlers"

ALLOW_EMPTY_recuserconfighandlers = "1"

RDEPENDS_recuserconfighandlers += " bash python"

do_configure_prepend () {
} 

do_compile_prepend () {
}

do_install_prepend () {
	install -d -m 0755 ${D}/opt/cmframework/userconfighandlers
    find recuserconfighandlers -name '*.py' -exec cp {} ${D}/opt/cmframework/userconfighandlers \;
}

FILES_recuserconfighandlers = " \
	/opt/cmframework/userconfighandlers \
	"
