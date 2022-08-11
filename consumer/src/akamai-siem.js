const EdgeGrid = require('akamai-edgegrid');
const linebreak = "\\n";

const fetchEvents = function (messageObject, settingsObject) {
    const authParams = {
        path: settingsObject.edgercFilename,
        section: settingsObject.edgercSection
    }

    const eg = new EdgeGrid(authParams);
    const fetchEventsParams = {
        path: "/siem/v1/configs/" + settingsObject.configsIds,
        method: "GET",
        headers: {
            Accept: "application/json"
        },
        qs: {
            limit: messageObject.eventsPerJob,
            from: messageObject.from,
            to: messageObject.to
        }
    };

    eg.auth(fetchEventsParams);

    return new Promise(function (resolve, reject) {
        eg.send(function (error, response, body) {
            if (error) {
                console.log(error, response, body);

                return reject(error, response);
            }

            let eventsBuffer = body.split(linebreak);
            let eventsList = [];

            eventsBuffer.forEach((item, index) => {
                if (item.length > 0 && index < (eventsBuffer.length - 1)) {
                    let eventObject = {
                        key: messageObject.job + "-" + index,
                        value: item.replace("'", "").replace(/\\/g, "")
                    };

                    eventsList.push(eventObject);
                }
            });

            let eventsObject = {
                "job": messageObject.job,
                "events": eventsList
            };

            return resolve(JSON.stringify(eventsObject));
        });
    });
};

module.exports = { fetchEvents };