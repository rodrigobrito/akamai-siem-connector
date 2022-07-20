FROM alpine:latest

# File Author / Maintainer
MAINTAINER Leandro Cassiano Alves

RUN apk add --update \
    curl \
    openjdk11-jre \
	bash \
 && rm /var/cache/apk/*

RUN mkdir -p /kafka/

RUN curl "https://dlcdn.apache.org/kafka/3.2.0/kafka_2.13-3.2.0.tgz" -o "kafka_2.13-3.2.0.tgz"

RUN tar -xzf kafka_2.13-3.2.0.tgz -C /tmp/

RUN rm -rf kafka_2.13-3.2.0.tgz

RUN cp -rf /tmp/kafka_2.13-3.2.0/* /kafka/

RUN rm -rf /tmp/kafka_2.13-3.2.0

COPY scripts/start.sh bin/start.sh

COPY scripts/setup.sh bin/setup.sh

RUN ["chmod", "+x", "bin/setup.sh"]

RUN ["chmod", "+x", "/bin/start.sh"]

EXPOSE 2181/tcp
EXPOSE 8080/tcp

CMD ["bin/start.sh"]
