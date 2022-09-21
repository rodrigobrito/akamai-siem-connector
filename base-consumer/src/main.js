const settings = require("./settings.js");
const akamaiSiem = require("./akamai-siem.js");
const mqtt = require("mqtt");
const os = require("os");

const settingsObject = settings.loadSettings();
const mqttClient = mqtt.connect("mqtt://" + settingsObject.scheduler, {clientId: os.hostname()});

mqttClient.subscribe(settingsObject.inputQueue);

mqttClient.on("connect",function(packet){
    let now = new Date();

    console.log("[" + now + "][" + os.hostname() + " connected to queue " + settingsObject.inputQueue + "]");
});

mqttClient.on("message", async function(queue, messageRaw, packet){
    try{
        let now = new Date();
        const messageObject = JSON.parse(messageRaw.toString());

        console.log("[" + now + "][" + os.hostname() + " received the job " + messageObject.job + " from queue " + settingsObject.inputQueue + "]");

        const result = akamaiSiem.fetchEvents(messageObject, settingsObject);

        result.then((eventsRaw) => {
            mqttClient.publish(settingsObject.outputQueue, eventsRaw);

            now = new Date();

            console.log("[" + now + "][" + os.hostname() + " published events of job " + messageObject.job + " to queue " + settingsObject.outputQueue + "]");
        });

        result.catch((error) => console.log(error));
    }
    catch(error){
        console.log(error);
    }
});

mqttClient.on("error", function(error){
    console.log(error);
});