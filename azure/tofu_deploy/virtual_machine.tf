data "azurerm_image" "ctf_worldcorp_image" {
  name                = "ctf_worldcorp"
  resource_group_name = data.azurerm_resource_group.vm_resource_group.name
}

resource "azurerm_linux_virtual_machine" "ctf_worldcorp_machine" {
  name                = "ctf-worldcorp-machine"
  resource_group_name = data.azurerm_resource_group.vm_resource_group.name
  location            = data.azurerm_resource_group.vm_resource_group.location
  size                = "Standard_B1s"
  admin_username      = "adminuser"
  network_interface_ids = [
    azurerm_network_interface.vm_net_interface.id
  ]

  source_image_id = data.azurerm_image.ctf_worldcorp_image.id

  admin_ssh_key {
    username   = "adminuser"
    public_key = file("${var.ssh_public_key_path}")
  }

  os_disk {
    caching              = "ReadWrite"
    storage_account_type = "Standard_LRS"
  }

  tags = {
    project = "ctf-worldcorp"
  }
}
