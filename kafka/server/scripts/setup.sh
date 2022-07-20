sed -i "s/broker.id=0/broker.id=$KAFKA_BROKER_ID/g" /kafka/config/server.properties
sed -i "s/zookeeper.connect=localhost:2181/zookeeper.connect=$KAFKA_ZOOKEEPER/g" /kafka/config/server.properties
echo "PLAINTEXT://$HOSTNAME:9092" >> /kafka/config/server.properties