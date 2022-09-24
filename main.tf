# Define the required providers for Linode provisioning.
terraform {
  required_providers {
    linode = {
      source = "linode/linode"
    }
  }
}

# Define Linode credentials.
provider "linode" {
  token = var.linode_token
}

# Define the Linode Kubernetes Engine provisioning.
resource "linode_lke_cluster" "akamai-siem-connector" {
  label       = "akamai-siem-connector"
  k8s_version = "1.23"
  region      = var.linodes_region
  tags        = [ "akamai-siem-connector" ]

  pool {
    type  = var.linodes_type
    count = var.linodes_count
  }
}

# Create the Kubernetes configuration file to be able to connect in the cluster after the provisioning.
resource "local_file" "kubeconfig-creation" {
  filename = "kubeconfig"
  content_base64 = linode_lke_cluster.akamai-siem-connector.kubeconfig
  depends_on = [ linode_lke_cluster.akamai-siem-connector ]
}

# Setup the cluster nodes.
resource "null_resource" "setup-nodes" {
  triggers = {
    always_run = "${timestamp()}"
  }

  provisioner "local-exec" {
    command = <<EOT
      "./setup-nodes.sh"
    EOT
  }

  depends_on = [ local_file.kubeconfig-creation ]
}

# Apply the settings.
resource "null_resource" "apply-settings" {
  triggers = {
    always_run = "${timestamp()}"
  }

  provisioner "local-exec" {
    command = <<EOT
      "./apply-settings.sh"
    EOT
  }

  depends_on = [ null_resource.setup-nodes ]
}

# Apply the stack.
resource "null_resource" "apply-stack" {
  triggers = {
    always_run = "${timestamp()}"
  }

  provisioner "local-exec" {
    command = <<EOT
      "./apply-stack.sh"
    EOT
  }

  depends_on = [ null_resource.apply-settings ]
}