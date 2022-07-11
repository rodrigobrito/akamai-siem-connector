const { Kafka } = require('kafkajs')

const kafka = new Kafka({
  clientId: 'akamai-dsadsdsa',
  brokers: ['localhost:9092']
})

const producer = kafka.producer()

producer.connect()
producer.send({
topic: 'aka-siem-sjakdjks',
messages: [
    { value: 'Hello KafkaJS user!' },
],
})

