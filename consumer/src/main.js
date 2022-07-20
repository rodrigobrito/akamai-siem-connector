const lib = require("./lib.js");
const akamaiSiem = require("./akamai-siem.js");
const mqtt = require("mqtt");
const os = require("os");
const settingsData = lib.loadSettings();

let client = mqtt.connect(settingsData.schedulerUrl, {clientId: os.hostname()});

client.subscribe(settingsData.schedulerTopic);

client.on("connect",function(packet){
    let now = new Date();

    console.log("[" + now + "][" + os.hostname() + " connected to " + settingsData.schedulerUrl + " on topic " + settingsData.schedulerTopic + "]");
});

client.on("message", async function(topic, message, packet){
    let now = new Date();

    console.log("[" + now + "][" + os.hostname() + " received a new message from topic " + settingsData.schedulerTopic + "]");

    let messageData = JSON.parse(message);

    console.log(messageData);

    let eventsData = akamaiSiem.fetchEvents(settingsData.edgercFilename, settingsData.edgercSectionName, settingsData.configurations, messageData.from, messageData.to);

    console.log(eventsData);
});

client.on("error", function(error){
    let now = new Date();

    console.log("[" + now + "][" + os.hostname() + " got an error on fetching topic " + settingsData.schedulerTopic + "]");
    console.log(error);
});