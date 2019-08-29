PACKAGES += " inventoryhandlers"

RDEPENDS_inventoryhandlers += " bash python"

do_configure_prepend () {
} 

do_compile_prepend () {
}

do_install_prepend () {
	install -d -m 0755 ${D}/opt/cmframework/inventoryhandlers
    find inventoryhandlers -name '*.py' -exec cp {} ${D}/opt/cmframework/inventoryhandlers \;
}

FILES_inventoryhandlers = " \
	/opt/cmframework/inventoryhandlers \
	"
