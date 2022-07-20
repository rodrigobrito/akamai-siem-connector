#!/bin/bash
sed -i "s/admin.enableServer=false/admin.enableServer=true/g" /kafka/config/zookeeper.properties
echo "admin.serverPort=8080" >> /kafka/config/zookeeper.properties