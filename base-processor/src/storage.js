const utils = require("./utils.js");
const os = require("os");

const storeEvent = async function (eventsRaw, settingsObject){
    const now = new Date();
    const filename = "/tmp/" + now.getTime() + ".json";

    utils.writeFile(filename, eventsRaw);

    console.log("[" + now + "][" + os.hostname() + " stored events (" + eventsRaw.length + " bytes) from " + settingsObject.inputQueue + " in " + filename + "]");
};

module.exports = { storeEvent };