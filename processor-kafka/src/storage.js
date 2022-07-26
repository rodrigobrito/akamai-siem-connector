const { Kafka } = require("kafkajs");
const os = require("os");

const storeEvent = async function (eventsRaw, settingsObject){
    const kafka = new Kafka({ brokers: settingsObject.kafka.brokers });
    const producer = kafka.producer();
    const now = new Date();

    await producer.connect();

    const eventsObject = JSON.parse(eventsRaw);

    await producer.send({
        topic: settingsObject.kafka.topic,
        messages: eventsObject.events
    });

    console.log("[" + now + "][" + os.hostname() + " stored " + eventsObject.events.length + " events of job " + eventsObject.job + " in storage topic " + settingsObject.kafka.topic + "]");

    await producer.disconnect();
};

module.exports = { storeEvent };