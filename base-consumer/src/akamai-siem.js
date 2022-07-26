const eventTemplate = {
    type: "akamai_siem",
    format: "json",
    version: "1.0",
    attackData: {
        configId: "1234",
        policyId: "5678",
        clientIP: "1.2.3.4",
        rules: "abcd",
        ruleVersions: "1.0",
        ruleMessages: "efgh",
        ruleTags: "ijkl",
        ruleData: "mnop",
        ruleSelectors: "qrst",
        ruleActions: "deny"
    },
    httpMessage: {
        requestId: "1",
        start: "1658843026",
        protocol: "h2",
        tls: "tls1.3",
        method: "GET",
        host: "www.example.com",
        port: "443",
        path: "/",
        requestHeaders: "Host%3a%20www.example.com%0d%0aUser-Agent%3a%20Mozilla%2f5.0%20(Windows%20NT%206.1%3b%20WOW64)%20AppleWebKit%2f534+%20(KHTML,%20like%20Gecko)%20BingPreview%2f1.0b%0d%0aAccept%3a%20*%2f*%0d%0asec-fetch-site%3a%20same-origin%0d%0asec-fetch-mode%3a%20no-cors%0d%0asec-fetch-dest%3a%20script%0d%0aReferer%3a%20https%3a%2f%2fwww.example.com.br%2f%0d%0aAccept-Encoding%3a%20gzip,%20deflate,%20br%0d%0aAccept-Language%3a%20en-US",
        status: "403",
        bytes: "5678",
        responseHeaders: "Content-Type%3A%20text%2Fhtml%3B%20charset%3DUTF-8%0AAccept-Ranges%3A%20bytes%0ALast-Modified%3A%20Wed%2C%2013%20Jul%202022%2016%3A06%3A44%20GMT%0AContent-Encoding%3A%20gzip%0AContent-Length%3A%205678%0ACache-Control%3A%20public%2C%20max-age%3D0%0ADate%3A%20Mon%2C%2025%20Jul%202022%2019%3A42%3A47%20GMT%0AConnection%3A%20keep-alive%0AVary%3A%20Accept-Encoding%0AServer-Timing%3A%20cdn-cache%3B%20desc%3DHIT%0AServer-Timing%3A%20edge%3B%20dur%3D1%0AStrict-Transport-Security%3A%20max-age%3D600%20%3B%20includeSubDomains"
    },
    geo: {
        continent: "NA",
        country: "US",
        city: "CHICAGO",
        regionCode: "IL",
        asn: "8075"
    }
};

const fetchEvents = async function (messageObject, settingsObject){
    return new Promise(function (resolve, reject){
        let eventsList = [];

        for(let index = 0; index < messageObject.eventsLimit; index++){
            let eventObject = {
                key: messageObject.job + "-" + index,
                value: JSON.stringify(eventTemplate)
            };

            eventsList.push(eventObject);
        }

        let eventsObject = {
            "job": messageObject.job,
            "events": eventsList
        };

        return resolve(JSON.stringify(eventsObject));
    });
};

module.exports = { fetchEvents };