const edgegrid = require('akamai-edgegrid');

const fetchEvents = function (edgegridFilename, edgegridSectionName, configurations, from, to){
    let authParms = {
        path: edgegridFilename,
        section: edgegridSectionName
    };

    let eg = new edgegrid(authParms);

    let fetchEventsParams = {
        path: "/siem/v1/configs/" + configurations,
        method: "GET",
        headers: {
            Accept: "application/json"
        },
        qs: {
            from: from,
            to: to
        }
    };

    let response;

    eg.auth(fetchEventsParams).send(function (error, response, body) {
        if (error)
            console.log(error, response, body);
        else
            response = body;
    });

    return response;
};

module.exports = { fetchEvents };