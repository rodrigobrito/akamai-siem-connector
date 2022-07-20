const fs = require("fs");

const loadFile = function (filename){
    let data = fs.readFileSync(filename);

    return data;
};

const loadSettings = function (){
    let settingFilename = process.env.ETC_DIR + "/consumer.conf";

    return JSON.parse(loadFile(settingFilename));
};

const sleep = function (ms) {
    return new Promise((resolve) => {
        setTimeout(resolve, ms);
    });
};

module.exports = {loadFile, loadSettings, sleep };


