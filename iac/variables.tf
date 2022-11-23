# Linode credentials
variable "linode_token" {}

# Linode Kubernetes cluster definition.
variable "linodes_region" {
  default = "us-east"
}
variable "linodes_type" {
  default = "g6-standard-4"
}
variable "linodes_count" {
  default = 2
}