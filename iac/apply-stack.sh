#!/bin/bash

# Find kubectl binary in the os path.
KUBECTL_CMD=`which kubectl`

# Check if kubectl is installed.
if [ ! -f "$KUBECTL_CMD" ]; then
  echo "Kubernetes client (kubectl) is not installed. Please install it to continue!"

  exit 1
fi

# Check if the kubeconfig file is available.
if [ ! -f "./kubeconfig" ]; then
  echo "kubeconfig file not found! You can proceed without this file!"

  exit 1
fi

export KUBECONFIG=./kubeconfig

# Install the kubernetes orquestrator.
$KUBECTL_CMD apply -n portainer -f https://downloads.portainer.io/ce2-15/portainer.yaml

# Install the stack (uncomment the desired deployments and services to be applied).
$KUBECTL_CMD apply -f stack-deployments.yml
$KUBECTL_CMD apply -f stack-services.yml