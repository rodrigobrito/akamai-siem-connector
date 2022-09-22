const fs = require("fs");

const loadFile = function (filename){
    const raw = fs.readFileSync(filename);

    return raw;
};

const writeFile = function (filename, content){
    fs.writeFileSync(filename, content);
};

module.exports = { loadFile, writeFile };