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

if [ -z "$1" ]; then
  echo "You need to specify the node count!"

  exit 1
fi

export KUBECONFIG=./kubeconfig

# Wait until all nodes are ready.
nodes=
nodes_count=$1
nodes_available=
nodes_available_count=0

while [ $nodes_available_count -ne $nodes_count ] ; do
  echo "Waiting for $nodes_count nodes be available..."

  nodes=$($KUBECTL_CMD get nodes)
  nodes_available=$(echo "$nodes" | grep Ready | awk '{print $1}')
  nodes_available_count=$(echo "$nodes_available" | wc -l)

  sleep 1
done

# Apply the labels in the nodes.
i=0

for node in $nodes_available
do
  if [ $i == 0 ]; then
    $KUBECTL_CMD label node "$node" kubernetes.io/role=manager --overwrite
  else
    $KUBECTL_CMD label node "$node" kubernetes.io/role=worker --overwrite
  fi

  i=$((i + 1))
done