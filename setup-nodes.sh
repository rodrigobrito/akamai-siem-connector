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

# List all available nodes in kubernetes cluster.
nodes=$($KUBECTL_CMD get nodes -o name)
i=0

# Apply the labels.
for item in $nodes
do
  node=$(echo "$item" | sed "s|node/||g")

  if [ $i == 0 ]; then
    $KUBECTL_CMD label node $node kubernetes.io/role=manager --overwrite
  else
    $KUBECTL_CMD label node $node kubernetes.io/role=worker --overwrite
  fi

  let i++
done