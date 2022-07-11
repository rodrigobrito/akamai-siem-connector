const { Kafka } = require('kafkajs')

const kafka = new Kafka({
  clientId: 'akamai-siem',
  brokers: ['192.168.1.137:9092']
})

const producer = kafka.producer() 

var sendMessage = async () => {
  await producer.connect()
  await producer.send({
    topic: 'akamai-siem',
    messages: [
      { key: 'key1', value: 'hello world' },
      { key: 'key2', value: 'hey hey!' }
    ],
  })
  await producer.disconnect()
}

sendMessage();

