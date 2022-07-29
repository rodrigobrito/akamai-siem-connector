const { Edgegrid } = require('akamai-edgegrid');
const linebreak = "\\n";

const fetchEvents = function (messageObject, settingsObject) {

    var clientToken = process.env.CLIENT_TOKEN,
        clientSecret = process.env.CLIENT_SECRET,
        accessToken = process.env.ACCESS_TOKEN,
        baseUri = `https://${process.env.BASE_URI}/`;

    var eg = new EdgeGrid(clientToken, clientSecret, accessToken, baseUri);

    let fetchEventsParams = {
        path: `/siem/v1/configs/${process.env.CONFIG_ID}`,
        method: "GET",
        headers: {
            Accept: "application/json"
        },
        qs: {
            limit: messageObject.eventsLimit,
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
                        value: data.replace("'", "").replace(/\\/g, "")
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