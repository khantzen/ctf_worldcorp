resource "azurerm_virtual_network" "vm_vit_network" {
  name                = "ctf-worldcorp-network"
  address_space       = ["10.0.0.0/16"]
  location            = data.azurerm_resource_group.vm_resource_group.location
  resource_group_name = data.azurerm_resource_group.vm_resource_group.name

  tags = {
    project = "ctf-worldcorp"
  }
}

resource "azurerm_subnet" "vm_subnetwork" {
  name                 = "ctf-worldcorp-subnet"
  resource_group_name  = data.azurerm_resource_group.vm_resource_group.name
  virtual_network_name = azurerm_virtual_network.vm_vit_network.name
  address_prefixes     = ["10.0.2.0/24"]
}

resource "azurerm_network_security_group" "vm_security_group" {
  name                = "ctf-worldcorp-security-group"
  location            = data.azurerm_resource_group.vm_resource_group.location
  resource_group_name = data.azurerm_resource_group.vm_resource_group.name

  tags = {
    project = "ctf-worldcorp"
  }
}

resource "azurerm_network_security_rule" "vm_security_port80" {
  name                        = "vm_sec_80"
  priority                    = 2001
  resource_group_name         = data.azurerm_resource_group.vm_resource_group.name
  network_security_group_name = azurerm_network_security_group.vm_security_group.name

  direction = "Inbound"
  access    = "Allow"

  protocol               = "Tcp"
  source_port_range      = "*"
  destination_port_range = "80"

  source_address_prefix      = "0.0.0.0/0"
  destination_address_prefix = "*"
}

resource "azurerm_network_security_rule" "vm_security_port22" {
  name                        = "vm_sec_22"
  priority                    = 1001
  resource_group_name         = data.azurerm_resource_group.vm_resource_group.name
  network_security_group_name = azurerm_network_security_group.vm_security_group.name

  direction = "Inbound"
  access    = "Allow"

  protocol               = "Tcp"
  source_port_range      = "*"
  destination_port_range = "22"

  source_address_prefix      = "0.0.0.0/0"
  destination_address_prefix = "*"
}

resource "azurerm_network_interface" "vm_net_interface" {
  name                = "ctf-worldcorp-net-interface"
  location            = data.azurerm_resource_group.vm_resource_group.location
  resource_group_name = data.azurerm_resource_group.vm_resource_group.name

  ip_configuration {
    name                          = "internal"
    subnet_id                     = azurerm_subnet.vm_subnetwork.id
    private_ip_address_allocation = "Dynamic"
    public_ip_address_id          = azurerm_public_ip.vm_public_ip.id
  }

  tags = {
    project = "ctf-worldcorp"
  }
}

resource "azurerm_network_interface_security_group_association" "sec_group_to_net_interface" {
  network_interface_id      = azurerm_network_interface.vm_net_interface.id
  network_security_group_id = azurerm_network_security_group.vm_security_group.id
}

resource "azurerm_public_ip" "vm_public_ip" {
  name                = "vm-public-ip"
  resource_group_name = data.azurerm_resource_group.vm_resource_group.name
  location            = data.azurerm_resource_group.vm_resource_group.location
  allocation_method   = "Dynamic"

  tags = {
    environment = "formation"
    project     = "ctf-worldcorp"
  }
}

output "vm_public_ip" {
  value = azurerm_public_ip.vm_public_ip.ip_address
}

