const settings = require("./settings.js");
const akamaiSiem = require("./akamai-siem.js");
const mqtt = require("mqtt");
const os = require("os");

const settingsObject = settings.loadSettings();
const client = mqtt.connect("mqtt://" + settingsObject.scheduler, {clientId: os.hostname()});

client.subscribe(settingsObject.inputQueue);

client.on("connect",function(packet){
    let now = new Date();

    console.log("[" + now + "][" + os.hostname() + " connected to queue " + settingsObject.inputQueue + "]");
});

client.on("message", async function(queue, messageRaw, packet){
    let now = new Date();

    const messageObject = JSON.parse(messageRaw.toString());

    console.log("[" + now + "][" + os.hostname() + " received the job " + messageObject.job + " from queue " + settingsObject.inputQueue + "]");

    const promise = akamaiSiem.fetchEvents(messageObject, settingsObject);

    promise.then((eventsRaw) => {
        now = new Date();

        client.publish(settingsObject.outputQueue, eventsRaw);

        console.log("[" + now + "][" + os.hostname() + " published events of job " + messageObject.job + " to queue " + settingsObject.outputQueue + "]");
    });

    promise.catch((error, response) => {
        console.log(error);
    });
});

client.on("error", function(error){
    console.log(error);
});