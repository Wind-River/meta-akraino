PACKAGES += " activators"

RDEPENDS_activators += " bash python"

do_configure_prepend () {
} 

do_compile_prepend () {
}

do_install_prepend () {
	install -d -m 0755 ${D}/opt/cmframework/activators
    find activators -name '*.py' -exec cp {} ${D}/opt/cmframework/activators/ \;
}

FILES_activators = " \
	/opt/cmframework/activators/ \
	"
