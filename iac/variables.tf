# General attributes.
variable "linode_cluster_label" {
  default = "akamai-siem-connector"
}
# Linode credentials
variable "linode_token" {}
# Linode Kubernetes cluster definition.
variable "linode_cluster_in_ha" {
  default = true
}
variable "linode_cluster_node_region" {
  default = "us-east"
}
variable "linode_cluster_node_type" {
  default = "g6-standard-4"
}
variable "linode_cluster_nodes_count" {
  default = 2
}