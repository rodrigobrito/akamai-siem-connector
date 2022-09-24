#!/bin/bash

# Show the banner.
if [ -f "./banner.txt" ]; then
  cat ./banner.txt
fi

# Find terraform binary in the os path.
TERRAFORM_CMD=`which terraform`

# Check if terraform is installed.
if [ ! -f "$TERRAFORM_CMD" ]; then
  echo "Terraform is not installed. Please install it to continue!"

  exit 1
fi

# Define variables based on the environment.
VARIABLES="-var linode_token=$LINODE_TOKEN"

# Initialize terraform (download the last version of providers & modules).
$TERRAFORM_CMD init --upgrade

# Check if the recipe is valid.
$TERRAFORM_CMD plan $VARIABLES

# Apply the recipe to provision the infrastructure.
$TERRAFORM_CMD apply $VARIABLES --auto-approve