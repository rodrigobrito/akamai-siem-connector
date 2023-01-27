#!/bin/bash

# Find kubectl binary in the os path.
KUBECTL_CMD=$(which kubectl)

# Check if kubectl is installed.
if [ ! -f "$KUBECTL_CMD" ]; then
  echo "Kubernetes client (kubectl) is not installed. Please install it to continue!"

  exit 1
fi

# Check if the kubeconfig file is available.
if [ ! -f "kubeconfig" ]; then
  echo "kubeconfig file not found! You can proceed without this file!"

  exit 1
fi

source .env

export KUBECONFIG=kubeconfig

# Check if there are Not Ready nodes before start deploying the stack.
while [ true ]; do
  NOT_READY_NODES=$($KUBECTL_CMD get nodes 2>/dev/null | grep NAME)

  if [ ! -z "$NOT_READY_NODES" ]; then
    NOT_READY_NODES=$($KUBECTL_CMD get nodes | grep NotReady)

    if [ -z "$NOT_READY_NODES" ]; then
      break
    fi
  fi

  echo "Waiting to all nodes in the cluster be ready..."

  sleep 1
done

NODES=$($KUBECTL_CMD get nodes | grep Ready | awk '{print $1}' )
ROLE=collector

for NODE in $NODES
do
  $KUBECTL_CMD label node "$NODE" kubernetes.io/role=$ROLE --overwrite

  if [ "$ROLE" == "collector" ]; then
    ROLE=processor
  elif [ "$ROLE" == "processor" ]; then
    ROLE=console
  else
    ROLE=collector
  fi
done

# Create the settings files (uncomment the settings definition based on your stack).
$KUBECTL_CMD apply -f stack-namespaces.yaml
$KUBECTL_CMD create configmap consumer-edgerc --from-file=edgerc=$HOME/.edgerc -n akamai-siem-connector -o yaml --dry-run=client | $KUBECTL_CMD apply -f -
$KUBECTL_CMD create configmap consumer-settings --from-file=$HOME_DIR/consumer/etc/settings.json -n akamai-siem-connector -o yaml --dry-run=client | $KUBECTL_CMD apply -f -
#$KUBECTL_CMD create configmap ingress-default-settings --from-file=$HOME_DIR/ingress/etc/nginx/http.d/default.conf -n akamai-siem-connector -o yaml --dry-run=client | $KUBECTL_CMD apply -f -
#$KUBECTL_CMD create configmap ingress-opensearch-dashboards-settings --from-file=$HOME_DIR/ingress/etc/nginx/http.d/opensearch-dashboards.conf -n akamai-siem-connector -o yaml --dry-run=client | $KUBECTL_CMD apply -f -
#$KUBECTL_CMD create configmap ingress-cert --from-file=cert=$HOME_DIR/ingress/etc/ssl/certs/cert.crt -n akamai-siem-connector -o yaml --dry-run=client | $KUBECTL_CMD apply -f -
#$KUBECTL_CMD create configmap ingress-cert-key --from-file=cert-key=$HOME_DIR/ingress/etc/ssl/private/cert.key -n akamai-siem-connector -o yaml --dry-run=client | $KUBECTL_CMD apply -f -
$KUBECTL_CMD create configmap json-converter-settings --from-file=$HOME_DIR/json-converter/src/main/resources/etc/settings.json -n akamai-siem-connector -o yaml --dry-run=client | $KUBECTL_CMD apply -f -
$KUBECTL_CMD create configmap kafka-broker-settings --from-file=$HOME_DIR/kafka/broker/etc/server.properties -n akamai-siem-connector -o yaml --dry-run=client | $KUBECTL_CMD apply -f -
$KUBECTL_CMD create configmap zookeeper-settings --from-file=$HOME_DIR/kafka/zookeeper/etc/zookeeper.properties -n akamai-siem-connector -o yaml --dry-run=client | $KUBECTL_CMD apply -f -
$KUBECTL_CMD create configmap logstash-kafka-mssentinel-settings --from-file=$HOME_DIR/logstash-kafka-mssentinel/etc/logstash.conf -n akamai-siem-connector -o yaml --dry-run=client | $KUBECTL_CMD apply -f -
#$KUBECTL_CMD create configmap logstash-kafka-opensearch-settings --from-file=$HOME_DIR/logstash-kafka-opensearch/etc/logstash.conf -n akamai-siem-connector -o yaml --dry-run=client | $KUBECTL_CMD apply -f -
$KUBECTL_CMD create configmap processor-kafka-settings --from-file=$HOME_DIR/processor-kafka/etc/settings.json -n akamai-siem-connector -o yaml --dry-run=client | $KUBECTL_CMD apply -f -
$KUBECTL_CMD create configmap scheduler-settings --from-file=$HOME_DIR/scheduler/etc/settings.json -n akamai-siem-connector -o yaml --dry-run=client | $KUBECTL_CMD apply -f -