const mqtt = require("mqtt");
const os = require("os");
const fs = require("fs");

const settingFilename = process.env.ETC_DIR + "/consumer.conf";
const settingsFile = fs.readFileSync(settingFilename);
const settingsData = JSON.parse(settingsFile);

let client = mqtt.connect(settingsData.schedulerUrl, {clientId: os.hostname()});

client.subscribe(settingsData.schedulerTopic);

client.on("connect",function(packet){
    let now = new Date();

    console.log("[" + now + "][" + os.hostname() + " connected to " + settingsData.schedulerUrl + " on topic " + settingsData.schedulerTopic);
});

client.on("message", async function(topic, message, packet){
    let now = new Date();

    console.log("[" + now + "][" + os.hostname() + " received a new message from topic " + settingsData.schedulerTopic + "]");

    let edgercFilename = settingsData.edgercFilename;

    /**
     * Add requests to SIEM API.
     */

    console.log(message.toString());
});

client.on("error", function(error){
    let now = new Date();

    console.log("[" + now + "][" + os.hostname() + " got an error on fetching topic " + settingsData.schedulerTopic + "]");
    console.log(error);
});

function sleep(ms) {
    return new Promise((resolve) => {
        setTimeout(resolve, ms);
    });
}