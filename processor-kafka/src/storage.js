const { Kafka } = require("kafkajs");
const os = require("os");

const storeEvents = async function (eventsRaw, settingsObject){
    try {
        const kafka = new Kafka({brokers: settingsObject.kafka.brokers});
        const producer = kafka.producer({maxBytesPerPartition: settingsObject.maxMessageSize});
        const now = new Date();

        await producer.connect();

        const eventsObject = JSON.parse(eventsRaw);

        await producer.send({
            topic: settingsObject.kafka.topic,
            messages: eventsObject.events
        });

        console.log("[" + now + "][" + os.hostname() + " stored " + eventsObject.events.length + " events of job " + eventsObject.job + " in topic " + settingsObject.kafka.topic + "]");

        await producer.disconnect();
    }
    catch(error){
        console.log(error);
    }
};

module.exports = { storeEvents };