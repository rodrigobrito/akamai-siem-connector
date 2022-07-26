const utils = require("./utils.js");

const loadSettings = function (){
    let settingFilename = process.env.ETC_DIR + "/processor.conf";

    return JSON.parse(utils.loadFile(settingFilename));
};

module.exports = { loadSettings };