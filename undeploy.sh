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

# Destroy infrastructure.
$TERRAFORM_CMD destroy -var linode_token=$LINODE_TOKEN --auto-approve

cd ..