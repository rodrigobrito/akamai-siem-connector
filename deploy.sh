#!/bin/bash

# Show the banner.
if [ -f "banner.txt" ]; then
  cat banner.txt
fi

# Find terraform binary in the os path.
TERRAFORM_CMD=$(which terraform)

# Check if terraform is installed.
if [ ! -f "$TERRAFORM_CMD" ]; then
  echo "Terraform is not installed. Please install it to continue!"

  exit 1
fi

cd iac

source .env

# Initialize terraform (download the last version of providers & modules).
$TERRAFORM_CMD init --upgrade || exit 1

# Check if the recipe is valid.
$TERRAFORM_CMD plan -var linode_token=$LINODE_TOKEN || exit 1

# Apply the recipe to provision the infrastructure.
$TERRAFORM_CMD apply -var linode_token=$LINODE_TOKEN --auto-approve

cd ..