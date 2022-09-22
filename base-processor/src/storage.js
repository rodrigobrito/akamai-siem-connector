const utils = require("./utils.js");
const os = require("os");

const storeEvents = async function (eventsRaw, settingsObject){
    const now = new Date();
    const filename = process.env.DATA_DIR + "/" + now.getTime() + ".json";

    utils.writeFile(filename, eventsRaw);

    console.log("[" + now + "][" + os.hostname() + " stored events (" + eventsRaw.length + " bytes) from " + settingsObject.inputQueue + " in " + filename + "]");
};

module.exports = { storeEvents };