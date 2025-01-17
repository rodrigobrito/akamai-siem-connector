const eventTemplate = {
    type: "akamai_siem",
    format: "json",
    version: "1.0",
    attackData: {
        configId: "1523",
        policyId: "PFk3_19798",
        clientIP: "2804:d59:922a:4a00:e44f:b1be:dc43:7f5c",
        rules: "NjAwMjQ4MjM%3d%3b",
        ruleVersions: "",
        ruleMessages: "QVBJIFJlcXVlc3RzICggUE9TVCwgUFVUIGFuZCBERUxFVEUgKQ%3d%3d%3b",
        ruleTags: "aG9zdHM%3d%3b",
        ruleData: "",
        ruleSelectors: "",
        ruleActions: "YWxlcnQ%3d%3b",
        apiId: "API_725806",
        apiKey: ""
    },
    httpMessage: {
        requestId: "9cfbbd76",
        start: "1658532762",
        protocol: "h2",
        tls: "tls1.3",
        method: "POST",
        host: "www.example.com",
        port: "443",
        path: "/",
        requestHeaders: "Host%3a%20www.example.com%0d%0aAccept%3a%20*%2f*%0d%0aContent-Type%3a%20application%2fjson%0d%0aAccept-Encoding%3a%20gzip,%20deflate,%20br%0d%0ax-acf-sensor-data%3a%202,i,DZ9fq4qVniC6Yrip3Hx+yyupVMGH9MgScUUSLRVgM4Luk88g6tDtkGYC+x1McjbqD9j%2fxrFn8bYl78S300KvYXA2s0QBiz8ZOzW6P4my+x4z9GoaVzX96rNWGen6Ck9%2fVuaL6HilTVjsWOT6iOj64CEeCLywaEyAlNhzLYU39LA%3d,Qxs7F1fcesw6iu2YQoU8Kq6hj4fXItgsx73aDW3zx6Eq5WAXfz2V4UTiy%2f1NmNbqLwrPRffXXw1AryGkcd0a60BK94SEybqYjpYKq15KqezxWvoYHwNN8XWK%2fL6SwPT6wgIOXPnLEPIat2qBxJxoe0U%2ffsqqaO+gnsHwoSMtgTg%3d$nXGg5SLZX4M75ugwn9Y6GkzCivdvnheSApmCfAg3NlAE1YwRALBm3vBz1Fhz96bIveavoFNRkbD1DqeuC6hMX5Rmq04pUWkDq6mRWJXWIi7Y5TyyHi9OhA9FvPv7xBdFEXf6Sz8Y7uUtVh1mOkNwBIF85K3Lm1TtKlCy7UrMCBeBY+xHFbqW72y0CERa5cFzwYpgJ7+hljnijoGTyuxSHEKTbVLNYeQ%2fgGHRJWoc4sPQR+F6sTK9mgDFv18ad72ZG8oBHwKIRsxt39J3ukufw58xzRRYq%2fxxZYxvls%2fmWSYMKwar6jecE9H1k03EMHqlGfQnC9K4lP6fgQZHlPJHTYxq438PiinGljcT2r37MwdDVh8YPbkc3saRO6%2fzriqJb2ZZufnKsXeJT7ZyxrRblpu3sxHBtBs5Vm6ucI%2fd%2fDiMXIju2IgDSSTNnSUQlkJoHaQcGiC5IqLjHncoiKuEsaAoVtxAVccR1EOtMPrOubSAq5yroEkfU6ppT+c5fPBObkfRGuN3aK+x+jAYWwELOQ51OQDIVNSigeZRzhqsGarhDJVOkBu+lNQWcZ9Ot24AjKtZuhmfdquKL2Ly22pON8sgMb4pAU7rQQgFpdfciRrbaGOwkxvKFEHEoyIDfaTp8RyzJcV5pkjpJ8sZT1FujnoXZMR1L84I0WaWXcUnPGKrzn16tIKfFZfRM3lT9AGvJI%2fvQBaXCzl2P0TzT3xPtq6CfVDXJPSPkkwmEL3PVGrO93YPmVECysXOyYOPTBVhCM1cAT472K%2f5xBwhn15dPfFUyVKVPwSCMZmJ+Ev01QX5EholjMAaZPEnbHCFbYyGki0iC7OlsEhLQ5D+z7LaDJKd3+cNyeq+2IxkCa8yZomalei9A7PobbV%2fO6Gok7v0ZOqsAPZNIofMAoMabeRWQKXXkZZVXA5zpYWubFbkmXR9un82rDKoHbZg4cA2fWzFmj5ktL4+5+6Px5IHIABtTUVrKPAQpEj8Hf%2fkGAcOb7pX0COE9+Fu0n46s98besLzcTewq7YfjPFT3vsUgP3T0bZ9Ljkfv3CCWu6KPwkZrkL6PA5n2d22ygRE4UEsEZPjjTHsRpY6jbEKI0Hg4ASzoIFgFVTYQowORu1n9o4%2f8lxz6kg3U3NBqa21MWRRz5eMV0AEfFKR10Se2I5Q9k5hUMpl4zjzgle7LfzKf+HCplTelzyv1mFcgjd1iVo95RDVzIOGubYjhClJ3MjFyiyFd6ZHMjaKl81%2f3Q5I%2fWhHDA5y0BDEukPoC8PmzkIoKQ21HGWOhpkmK2ph9y%2f6zVTuLRorio259LKZM+8cxUShSsfB1o%2fbvM1Y3LPCIZ46TxpoTM9Xgq9b4txl6Kka35sSPaTYfniiIy0vNjqfYOfDiKkYkPNPUgjXPurrVrcYCd4fuRBlwnfwFcbO4PhAhDBA%2fbD8uepTSOamqs2AWjpdwlI9A0XzceqKOSJiFR1hpVKOJp9qDI5%2fXZt0gwc8hel%2f4oV8YXw9kG9Mc8pDU0po49sIQHXmwtDs1KR8X2ItgsDusByi972UjlbZWpG9CYgFXuhpAm%2fdlH5HGsyKi6n7VIrWpMwHPtDNXMwhTuawtqCpGucg7gxdUYog90BAvyUaa2LEcbtMMOM1w2%2firRtLK95Jf3VOShE9pEKTYHroNOxwLVk6ARjhPy0VA02OdA%2fLwOVQOzDtvoFYeal1sHyBjXU8HCAyOD1%2fHmqzVubfuUZzSr853x8svp9v4lCHUda5FWd8GwVnCz6vY4%2fWJYqqSffp1kANt8DuVFxbTqcxEJ7nIC7%2fLWe3SfjxD1zssS%2fJwscu3ZNGBm2Dkc4GrARQRMn6aErYTOkW%2fKOI+S0x4O6GOKWuMxXaXXrET5h1cEeWOPc5AyRCNlLCZhBjq3OulvUm7POfurR%2fbFefv4+MPPbCYhnUi+Evd8cHDxCHq66WVsmO6ikaqBFtOr+3UnrrfURL%2fQ6d0x%2fkrsQwLrtteg1MmIxDRAaiCv3ka4TeiP9k8yZ1D5Ue48bIDTdGVH4h4wHN97RxITHGs6mNQNWt6QsH%2fYdKhk2TVhbj3x5QkvukcpbGUNc1rPWf2kYU5KtLF5K1umOC6+9XflC0nhDsCNRi+vLwd83vL4q6Xy7zbK+iNa2z1lFLiMxamWcClkLNg+e962egEMT22%2fqF64dqzXm4YNV9lMjSMHe3OwIGU+oSD8tm4%2feCDqrkXLX9RpOwsWi9Bm5Ezm2cPnjDvHKKfJ9+Y6km3tj3Vka8suM4WBHYQXPQa4GVnC3%2fA7D%2fwzOat0HGUHtBmZKUvqytXqpzKPwl%2fl62q5FhJtCjJypTn5ThqgklFTJ0BqPGUu0xd0tuVxmg4iulh3WxdV3hgjeSr2NJ5gNok7O5hFIQ6fRDqFyqZVup7UIHj9euWuc%2fSNnwdrWnVo5jArsWcoHnECpDSSzy6Irvb5AcxRjD76vUdwiVLKPjm6gbk%2flVONPt4DCYVYqnlASrpe4nfzNiDRuACSuqrzQP65TodssIg3yodzEYmG6fQGFJcQF8pLUuR%2f5aUCrBzF8U22dAfUa7K2zTLwavRv2vueXcxnS3WBE9%2ftcUHplUCvlg5jOWGLvkv3i1KAWLnqe70LjH%2fOQ5%2f31XxGheacrdKrVOBCLyYSZmk4EAjAl7WHUjgtIgPXeYXwvtfodxCGiR5XoDwgniU+jRjSR7y4sCRD28fQIa0E9LMNa8IJERkFK8KFIHlnmZC8fwqcJQi2+M9gktS2sqJ0zHvvYgx1sCRm5uFq0jSk8LcMThbO%2fDzSkMsjlZMqnHR0w80dnYULF4eoJo8BVb8xd2BdvjO5KjVFf6zTkPTZ6CJ86KM0oixH5Gm7YZA4332wiDEmWTmQWx%2fD4U%2fJrfKExjJRbWt4of55DBrvZ29y2NLx7uZsA3hrRp0MQhe%2f20xAA9Oun4BrWm5phngN1Y1uLa6axayX8K4nF8ajGXp9xgZdi2a1uyW7r5HL7SRsOrT1tu7qUkTf24YugtaNnsOnsxUddYiYeDr5mWtU7BB2DW+O2YVFVmojdLDft0%2fwyxxo7uvwivwDt2P9pXZVCZ2STZ%2fxVU$34,14,80$%0d%0ax-token%3a%20exp%3d1658532882%7eacl%3d%2f*%7ehmac%3d9c1ed1c619e21bc355493a0aa575c35cc4222ac9e129ddfe04b75ab9978d71d9%0d%0aUser-Agent%3a%20Example%2f7.8.98%20(iPhone%3b%20iOS%2015.5)%0d%0aContent-Length%3a%2021%0d%0aAccept-Language%3a%20pt-BR,pt%3bq%3d0.9%0d%0a",
        status: "200",
        bytes: "848",
        responseHeaders: "Content-Type%3a%20application%2fjson%3b%20charset%3dutf-8%0d%0aContent-Encoding%3a%20gzip%0d%0aStrict-Transport-Security%3a%20max-age%3d15724800%3b%20includeSubDomains%0d%0aContent-Length%3a%20848%0d%0aX-EdgeConnect-MidMile-RTT%3a%200%0d%0aX-EdgeConnect-Origin-MEX-Latency%3a%2027%0d%0aWarning%3a%20%7bTrue-Client-Port%3a%2054725%20%7d,%20X-Context%3a%20%20%7d%0d%0aDate%3a%20Fri,%2022%20Jul%202022%2023%3a32%3a42%20GMT%0d%0aConnection%3a%20keep-alive%0d%0aVary%3a%20Accept-Encoding%0d%0aSet-Cookie%3a%20ak_bmsc%3dADBAABC6377736F350322F9112D6A207%7e000000000000000000000000000000%7eYAAQFM1YaM2ZLL2BAQAADZRBKBDbGbtDv45viToDtRmPdagQ6AOcdXDtSnshsLXy8m7yMLa7ZlNyDszM77OxNzUK3vdOnGwsIS0K6LWdtJmb2KR+ur3JA42ZjDkVI8zdRAN+XVMYfkgKLhwFuy674c+UAPT2k1l+A6RdTKSOr9+YtBj3B+lQsoqc0QtupB7Nl4e3Ohu5s606maqI5mwoclCXH9LFFRVEepxqmiz7ln3BoZRfkJ+rnNR%2fJfa5aL5aa9ST42xc05KcjvIOyvWCdYfkTsTa7wGSyIBmShlsavNoHZpHY1L0TL4Unwb+5xauj6bDkbAZ6mx%2fcNxzjh9Fv8WcyA2uDnyaeiJYChGCbUaJXToR9QLDFGIMB8YWYe0TQxUKqnrt38%2f1bYnJdBvi8b19pWZpoBXcPi7RvMSFjf6L9ts1RwMk17Pz%3b%20Domain%3d.example.com%3b%20Path%3d%2f%3b%20Expires%3dSat,%2023%20Jul%202022%2001%3a32%3a42%20GMT%3b%20Max-Age%3d7200%3b%20HttpOnly%0d%0a"
    },
    geo: {
        continent: "SA",
        country: "BR",
        city: "BRASILIA",
        regionCode: "DF",
        asn: "8167"
    }
};

const fetchEvents = async function (messageObject, settingsObject){
    return new Promise(function (resolve, reject){
        let eventsList = [];

        for(let index = 0; index < messageObject.eventsPerJob; index++){
            let eventObject = {
                key: messageObject.job + "-" + index,
                value: JSON.stringify(eventTemplate)
            };

            eventsList.push(eventObject);
        }

        let eventsObject = {
            job: messageObject.job,
            events: eventsList
        };

        return resolve(eventsObject);
    });
};

module.exports = { fetchEvents };