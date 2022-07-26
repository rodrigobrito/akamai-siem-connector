const settings = require("./settings.js");
const storage = require("./storage.js");
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
    storage.storeEvent(messageRaw, settingsObject);
});

client.on("error", function(error){
    console.log(error);
});