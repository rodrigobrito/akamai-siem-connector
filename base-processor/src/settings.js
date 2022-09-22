const utils = require("./utils.js");

const loadSettings = function (){
    const settingFilename = process.env.ETC_DIR + "/settings.json";

    return JSON.parse(utils.loadFile(settingFilename));
};

module.exports = { loadSettings };