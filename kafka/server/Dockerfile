FROM debian:latest

# File Author / Maintainer
MAINTAINER Leandro Cassiano Alves

### solve some problems with shell commands ###
RUN rm /bin/sh && ln -s /bin/bash /bin/sh

RUN apt-get update -y && \
      apt-get upgrade -y && \
      apt-get install -y curl \
						unzip \
						cron \
			   ca-certificates \
			   openjdk-11-jre

RUN mkdir -p /kafka/

RUN curl "https://dlcdn.apache.org/kafka/3.2.0/kafka_2.13-3.2.0.tgz" -o "kafka_2.13-3.2.0.tgz"

RUN tar -xzf kafka_2.13-3.2.0.tgz -C /kafka/

RUN rm -rf kafka_2.13-3.2.0.tgz

RUN mkdir /etc/kafka/

RUN ln -s /kafka/kafka_2.13-3.2.0/config/ /etc/kafka/

RUN echo "#!/bin/sh" > /bin/start.sh

RUN echo "" >> /bin/start.sh

RUN echo "/kafka/kafka_2.13-3.2.0/bin/kafka-server-start.sh /etc/kafka/server.properties" >> /bin/start.sh

RUN ["chmod", "+x", "/bin/start.sh"]

EXPOSE 9092/tcp

CMD ["/bin/start.sh"]
