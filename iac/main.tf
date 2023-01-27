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
  label       = var.linode_cluster_label
  k8s_version = "1.25"
  region      = var.linode_cluster_node_region
  tags        = [ var.linode_cluster_label ]

  pool {
    type  = var.linode_cluster_node_type
    count = var.linode_cluster_nodes_count
  }

  control_plane {
    high_availability = var.linode_cluster_in_ha
  }
}

# Download the Kubernetes configuration file to be able to connect in the cluster after the provisioning.
resource "local_file" "download-kubeconfig" {
  filename       = "kubeconfig"
  content_base64 = linode_lke_cluster.akamai-siem-connector.kubeconfig
  depends_on     = [ linode_lke_cluster.akamai-siem-connector ]
}

# Apply the stack settings.
resource "null_resource" "apply-settings" {
  triggers = {
    always_run = timestamp()
  }

  provisioner "local-exec" {
    command = "./apply-settings.sh"
  }

  depends_on = [ local_file.download-kubeconfig ]
}

# Apply the stack services and containers.
resource "null_resource" "apply-stack" {
  triggers = {
    always_run = timestamp()
  }

  provisioner "local-exec" {
    command = "./apply-stack.sh"
  }

  depends_on = [ null_resource.apply-settings ]
}