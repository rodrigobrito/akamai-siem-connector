## Akamai SIEM Connector

### 1. Introduction
***
The project intends to provide a reliable, robust and scalable way for collecting security events (WAF, DDoS, BOT, etc.) 
and easily store then into SIEMs platforms that are not supported by the connectors provided by Akamai.

### 2. Motivation
***
Customers want to access, analyze and process their security data to build reports, dashboards or even to get insights 
in real-time to take actions in a fast manner. 

Depending on the volume of data, this task can be hard because they'll need build a well-defined and scalable 
infrastructure from scratch to be able to collect the data without any loss, and certainly will consume a lot of 
resources (time and operational costs).

In many projects at LATAM, we faced issues (availability and scalability of connectors) during projects to integrate 
Akamai with customers' SIEM platforms, generating a lot of time consumption for the Akamai Professional Services team.

What if Akamai could provide an easier, simple and standard way to collect the security events, with just using few
human-readable parameters, while optimizing the resources needed to do so?

### 3. Maintainers
***
- [Leandro Cassiano](https://contacts.akamai.com/lcassian) - Solution Architect LATAM
- [Felipe Vilarinho](https://contacts.akamai.com/fvilarin) - Engagement Manager LATAM

If you want to collaborate in this project, reach out us by e-Mail. These is the current backlog:

- Define a retry strategy for `scheduler`, `consumer`, `processor`
- Define the healthcheck attributes the containers images.
- Create K8S manifests.
- Create Terraform recipes for deployment (`AWS`, `GCP`, `Azure`, `Linode`, etc.).
- Remove high severity vulnerabilities in the containers images.
- Add security check in the CI/CD pipeline.
- Reduce the size of base `Apache Kafka` images.
- Reduce the size of `Logstash` images.

### 4. Versions
***
- `1.0.0` - Stable (For production use)
- `latest` - In constant improvement (For development or test use).

## 5. Pre-requisites
***

You'll need:

### To build

- [`NodeJS 16.x or later with NPM`](https://nodejs.org)
- [`Docker 20.x or later`](https://docker.com)
- `Any linux distribution` or 
- `MacOS - Catalina or later` or 
- `MS-Windows with WSL2`
- `Machine with at least 4 CPU cores and 8 GB of RAM`

Just execute the shell script `build.sh` to start the building process.

### To run it locally (In your machine or in a standalone environment)

- [`Docker 20.x or later`](https://docker.com)
- `Machine with at least 4 CPU cores and 8 GB of RAM`

Just execute the shell script `start.sh` to start. It will run in background.
To stop, just execute shell script `stop.sh`.

### To run it in a cluster (Only Swarm mode. We are working on K8S.)

- [`Docker 20.x or later`](https://docker.com) or
- `Provisioned Swarm node with as least 1 node with 4 CPU cores and 8 GB of RAM`

Just execute the commands below in the manager/leader node of your cluster:

- `source ./.env`
- `docker stack deploy -c './docker-swarm.yml' akamai-siem-connector`

## 6. Architeture
***

Follow this [diagram](https://viewer.diagrams.net/?tags=%7B%7D&highlight=FFFFFF&layers=1&nav=1&title=Akamai%20SIEM%20Connector%20Architecture.drawio#R7V1bd5u4Fv41fkwXd9uPubY506zJ1GfWTPsmg2wzwcgFOXHm148Ekg1s2SYxtxS3ayUgLoH97fveEgPzern5HKHV4oF4OBgYmrcZmDcDw9Atw2G%2F%2BMhrOjIa2unAPPI9cdJuYOL%2Fi8WgJkbXvofj3ImUkID6q%2FygS8IQuzQ3hqKIvORPm5Eg%2F1dXaI7BwMRFARz9y%2FfoQryFMdyNf8H%2BfCH%2Fsu6M0yNLJE8WbxIvkEdeMkPm7cC8jgih6dZyc40DTjxJl%2FS6uz1Htw8W4ZCWueD%2Fvvdj%2BlVb3l1HX%2F68%2BPZ9Y3y7udAFGs8oWIs3pu5qwG%2FlBOy2V9OIbc35lqSwcce24vUSi9eir5JWFG%2F4iQu6DNiAzjZR4M9Dtu2yZ8QRG3jGEfUZdS%2FFgaXvefzyqwjH%2Fr9omtxKY%2Fsr4oc0wc6%2BGtg3%2FF5rSuKUP%2FitYxqRJ3xNAsLuexOSkN9l5gdBcYiEVLCVzq%2BDZBOU5I%2BGN5khQcbPmCwxjV7ZKeKoZQiqCZ52HAHxy45DdMnAiwx3WGIMCaacb2%2B9w41tCOjUMH5Bz%2BMfF5M%2FXik2f%2F54efjNeLq%2F0AGKl09oiXw2Nrm%2FfWC%2FLh%2Fv2c%2Fb0EsIC6CLyDr0sCdo%2B7LwKZ6skMuPvjDBzqOaJalVoPrAMD2ERzMXQMSOOO4IT2fVgGAMx3kQIAampsDAqQ0DDYDwPzKN2cgfa7yG0sL0wYpvuq%2BBz2gfmccJP01R%2BjrdDiD3aZ5g9%2FuasttshUOgYysEoIiWjUeepUJrZExNx6kGLScPlgotRwHWsC6wpFnKgAUAwh6zBGKXRHRB5iREwe1u9GonNVxl7c75SshKIPEPpvRVkJ9rsDygMUURveRmijNCgOLYd%2BXwnR%2FI0%2FYCEJN15OJDTCnek91wjumhE4VB4y99ENAIB4j6z3nzWDk%2BltkGHHjj078z29%2F5rT7ZYu9mI%2B6c7LzKnZC979%2FZncxVfHd3WbInr8tBL2xVFnetGdxNuy3c1f4ItGRtMELRwnVHTk2rW3gZZ7w6itfB587gdZ1681G9PmEjXoZZdDOGxicbOBq6oXIL7bo8jVErlkxape1OOauU7D3iyGcvzwM2aKpqkaZhWS9F65Y0DfdJE3%2BZBf%2F5TxoCxO4Ce%2BuAQcpzEFGSsAiCJJzeExasIuLiOC4RFOyJAY74%2FTOb%2FxfnZcbTf8roLflXjaTqo6KklpXT2sK3McDSjUhYSIVkkHJ%2BrnnqJqHfRRpwXbITdH21SWgkj8v8SYyjZ9%2Ftb9ZEN50c5JbmAMhVMWBtSRMD5r6WPymtDvG%2B58l0PS%2FkpgaFfNgk4iZM0VSOeKhS6n1B3NSOI96ojJsjgHgb7ljtHpRplPWg2nKg1OFjq2meXWrne%2BaIOs3TeIRZHlGnUz6xfO5sLWLNfOGQazeKE13AVIGhcUg%2F8yKgoQX%2BNELib%2FfPFy5WMvShopzUqC9sDbsjltpBsSwphrsk7S4C%2Fp49ticcPkF8rbYUsrj0Ma3zSSazC77YlqHkLdIHfdxWB7P8s32ME9SCBdTCHaYuVwT4GXOHxtCmKE4C5CTqon4SRZPZLMY02cjG1D1VFZbdOVVht6kqmijUHE%2BJVV69kUWZ49nljtl%2BGFFfRzi1%2BiIRxpBkNBFCKgVa5MIS4ZfaoJfybVtdS4uZsGBwKxCqJGI%2B90YYQ6NjzREWtNXnNEmVaRJr1LE0iQWlvFrEV%2Btp4MeL3iLuOF1DHCbGFpSuoOHtC0JGQSZ1C2phW4GQWRtCsCDVb4RACNs2QkMdYNHR7OW%2B%2FMcu2lHHPkUU6w17JHhHwx4ZBXck7LFhWekxDVN4pb%2FAIR%2B%2Fq8Z0RkDumu2psdspG7wziahMIByUrDrqCXbZekLHcgp2Nzq1u4uX1D5dwQvWf0r3RInkjh%2FOAcT9zP%2BYred37QZyAb1ukgGR4XgMIG%2B0ScaGTY3n7E%2Bt2R8F4nXlApRdGGYnLOwJpnJc0lQ6HTOVMMJ%2F3FZGUOglEJBoZzh7XRsphiFW67bRgXHfJaN5AtVvaPaE3qgwrT0K84pTFUaSbRRJ2gtCC00ytgp91XzfcW3oj9vQktvsz%2BAdle83TgZpPOZxys4DkRmIjihyB7pMExHc%2FAoJoOJkjUYTQEqPxWinQa2uBBBoKK08tSoR%2B2gzrBxYplJ2lCQ%2FtlOq%2Bu0o2c6n%2FPokqizCyG7QURrCnBCKX0OXOThoyUkeTuNV6vBUEWBG%2BOcax%2F0NMIsNxYapmgbbaLl5CGNMyQFnxCsQeb2IeLkWovrwhmnDs8TXuSJVITY29cbwV0%2FsgaFxGx5bnbN2lO%2Bt6PpXntda%2FKJ2p3vVyFCpt30SF0it3RE2gDr7K5kzqij69j5eEFtMH460sunDuoJYmP1tW0VWneQ5pPo6nKw%2F9NgHytrbeUwivEwK25k0vgfgrS0erUFehqXlpTafAuqn42uV9r3xuOgZ2orIQJk3r881hA0959CgOQYYlpxdUl%2B12To7m1U6m9KJPOptmt0yqIoFlybKandRufvh9hSxuHHSPHblz28QRZyK7PFnJFo2Z287lf8tWu5x65ZberZnld%2BKyh85Rrsq34DO83l6WaWIaznATQMWaJu18VDgATZcr67Kv%2Fv2Ew8CkkH2KwrKqnWhCjIeQ6LYKs8XLI1RHVlKZEVPyqxAch6G5ziDtUcsuJbAg%2B9GJCYzrjgmfEGhkH9sZI%2BNb3nq%2BGw2M1zlRxA8Z%2BrYalN%2FKljbdfesHOfrkPFHCihHRk1Iwu4YqfY5EU9S%2FLcBipn6jjGK%2BFoyRXci9Qx%2FX%2BFwe0aQXn6X%2Fun0Ll1londlRitiIkPrFA8pFuu8%2FGvCFYEpYXbJcsXiFm4eOopnm0rB0EbdAlSxsMgGTTHaH7b1GD1TM7uFHgzev%2BElSfqyJq9xQPbP4OoziuNuyaBivaYko37X74UGLCPvP40d2E2hmpJVxUIDapy6sYpt1d0ULZQAxaWFxRGNcT5UdPRCzTdNwlaxOKK68A7D5eJEEk1OAtH%2BvAfYf7xqfDE4t0dlc5R1VeOtdsoS72wpb76xyYQye5CXO1JlaGd9yu7C09pn3g4%2Bdkbx3aB4MSUo8qD%2F8fHVXPtNR%2B0s7dy8PCi%2Bz3WQA7siECVWO5GBFHIp59nTc8RvX7OgyNUm4GqryfrieTG7YrLHLDR96HD%2BR11LpakROi9mdxihcdsIKRYx6zdCxa8XqbRcowgplhsA4NRdVHX0fFnAsRUOjUyX5OqEVaxWrGZcGDwDsrzbc1Tz0mF43lRUbZpY%2BluIpb2dWFV8C%2F5U8koVq%2BdY1YAKVuV511fPVnwBYx0E%2FIGXKERz3h9ebHWb8qOXT2iJ%2FF9FD1cErpUHV2E9m%2B1qLrNMYaWCVVlp%2BGTBUqTumhUs2F9wz5O7LuKNolOcFTDFXIGcjKWVZHjS9TqmRPWR5LMU5rwBKIW6ghXs2lihRKGkSimsrph4shTqiokdzYohDPHK2TcpW79MqFGTiTMUrnZFNo7tRoQ3Vm2PfWaisHggHuZn%2FAc%3D) to check out the architecture and the components:

## 7. Settings
***

To define the settings of the events collect, just edit the file `scheduler/etc/scheduler.conf` and set the 
following attributes:

- `tps`: It's the number of events that should be processed per second. You can find this information analyzing the data in
WSA.
- `eventsPerJob`: Number of events that should be fetched per request. This information is essential to determine the
number of requests/workers to collect the events per minute. Depending on the volumetry, you will need to scale the 
`consumer`.

By default, the `consumer` will generate mock events. To define the credentials to connect in Akamai SIEM, just map the 
`.edgerc` file in the home directory of `consumer`.

This architecture uses [`Apache Kafka`](https://kafka.apache.org) to process/store the events collected. If you want to 
use an external instance of `Apache Kafka`, just edit the file `processor-kafka/etc/processor.conf` and specify the connection
attributes of the instance. Depending on the volumetry, you will need to scale the `processor`.

To define the customer SIEM attributes, just edit the file `logstash-<siem-identifier>/etc/logstash.conf`.

And that's it!!
