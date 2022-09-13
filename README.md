## Akamai SIEM Connector

## 1. Introduction

The project intends to provide a reliable, robust and scalable way for collecting security events (WAF, DDoS, BOT, etc.) 
and easily store then into SIEMs platforms.

## 2. Motivation

Customers want to access, analyze and process their Akamai Security Data to build reports, dashboards or even to get 
insights in real-time to take actions in a fast manner.

Depending on the volume of data, this task can be hard because they'll need build a well-defined and scalable 
infrastructure from scratch to be able to collect the data without any loss.

In many projects at LATAM, we faced many issues such as: availability, coverage limitations and scalability of 
connectors provided by Akamai. It generates a lot of consumption of resources (time and operational costs) from 
customers and Akamai.

What if we could provide an easier, simple and standard way to collect the security data, with just using few 
human-readable parameters, while optimizing the resources needed to do so?

## 3. Scenarios
Customers, with high volume (+5000 events per second) of security data, are facing issues such as unavailability and 
scalability, because the connector responsible for collecting the data was not built to support this amount of data, and
also it is outdated.

Also, if the customer does not have Splunk, they need to provide another mechanism to ingest the data as we only support
Splunk and Syslog. This generates extra efforts and costs for them and for us. Our customers are very heterogeneous in 
terms of SIEM platforms.

## 4. Solution
Now that Akamai has acquired Linode, we can use it as the main platform to provide this integration as a service and 
solve the current limitations, using a reliable, scalable and easier stack.

With microservices architecture we can scale different parts of the data collection and store it directly in their SIEM 
platform without any extra mile in the delivery.

Based on their current volumetry, we can auto-setup the capacity needed for data collection and processing as well the 
storage and provision it on Linode automatically.

## 5. Maintainers

- [Leandro Cassiano](https://contacts.akamai.com/lcassian) - Solution Architect LATAM
- [Felipe Vilarinho](https://contacts.akamai.com/fvilarin) - Engagement Manager LATAM

If you want to collaborate in this project, reach out us by e-Mail. This is the current backlog:

- Define the healthcheck attributes the containers images.
- Create K8S manifests.
- Create Terraform recipes for deployment (`AWS`, `GCP`, `Azure`, `Linode`, etc.).
- Add security check in the CI/CD pipeline.

## 6. Versions

- `1.0.0` - [![pipeline](https://github.com/lcassiano/akamai-siem-connector/actions/workflows/pipeline.yml/badge.svg?branch=1.0.0)](https://github.com/lcassiano/akamai-siem-connector/actions/workflows/pipeline.yml) - Last stable version.
- `latest` - [![pipeline](https://github.com/lcassiano/akamai-siem-connector/actions/workflows/pipeline.yml/badge.svg?branch=main)](https://github.com/lcassiano/akamai-siem-connector/actions/workflows/pipeline.yml) - In constant improvement (For development or test use).

## 7. Pre-requisites

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

Just execute the shell script `start.sh` to start. You can specify a specific component to start also. Just pass the 
name of the component after the start script. It will run in background. After that, edit your host and point the 
hostnames `queue.akamai.siem` and `dashboard.akamai.siem` to `127.0.0.1` and then open these hostnames in your preferred
browser. If you want to change the hostnames, just edit the environment variables in the `ingress` service.
To stop, just execute shell script `stop.sh`.

### To run it in a cluster (Only Swarm mode. We are working on K8S.)

- [`Docker 20.x or later`](https://docker.com) or
- `Provisioned Swarm node with as least 1 node with 4 CPU cores and 8 GB of RAM`

Just execute the commands below in the manager/leader node of your cluster:

- `source ./.env`
- `docker stack deploy -c './docker-swarm.yml' akamai-siem-connector`

Use the same procedure above to browse the hostnames after the boot.

## 8. Architeture

Follow this [diagram](https://viewer.diagrams.net/?tags=%7B%7D&target=blank&highlight=FFFFFF&edit=_blank&layers=1&nav=1&title=Akamai%20SIEM%20Connector%20Architecture.drawio#R7V1Zd6O4Ev41fkwfdtuPWbtzJ30n05k%2BvbzcI4NsM8HIDXJiz6%2B%2FEkg2ULJDYrY07j4nAbEE9FWVqr4qiYF5uVh%2FjNBy%2Fpl4OBgYmrcemFcDw9Atw2G%2FeMsmbRlZZtowi3xPnLRrePD%2FxaJRE60r38Nx7kRKSED9Zb7RJWGIXZprQ1FEnvOnTUmQ%2F6tLNMOg4cFFAWz95nt0Lt7CGO7aP2F%2FNpd%2FWXfG6ZEFkieLN4nnyCPPmSbzemBeRoTQdGuxvsQB7zzZL%2Bl1N3uObh8swiEtc8Hfvvdzcqctbi6jT1%2FPvvxYG1%2BuznQ7vc0TClbijam7HPBbOQG77cUkYlszviV72LhhW%2FFqgcVr0Y3sK4rX%2FMQ5XQSsQWebKPBnIdt22TPiiDU84Yj6rHfPxYGF73n88osIx%2F6%2FaJLcSmP7S%2BKHNMHOvhjYV%2FxeK0riVD74rWMakUd8SQLC7nsVkpDfZeoHQbGJhFSIlc6vg90mepI%2FGl5nmkQ3fsRkgWm0YaeIo5Yhek3ItOMIiJ93EqJLAZ5npMMSbUgI5Wx76x1ubENAp4bxE3oa%2Fzx7%2BGtDsfnr5%2FPnP4zH2zMdoHj%2BiBbIZ20Pt9ef2a%2Fz%2B1v28zr0ko4F0EVkFXrYE337PPcpflgilx99ZoqdRzXbpVah1weG6SE8mroAInbEcUd4Mq0GBGM4zoMAMTA1BQZObRhoAIT%2FkEnMWv5a4RXUFmYPlnzT3QQ%2B6%2FvIfLnjJylKd5NtA3IfZwl2f64ou81WOQQ6tkIBimjZeORZKrRGxsR0nGrQcvJgqdByFGAN6wJLDksZsABA2GMjgdglEZ2TGQlRcL1rvdhpDTdZu3PuCFkKJP7BlG5E93MLlgc0piii53yY4oIQoDj2Xdl84wfytL0AxGQVufiQUIr3ZDecYXroRDGg8Zc%2BCGiEA0T9p%2FzwWDk%2B0ktoFg689un3zPYPfqsPtti7Wos7JzsbuROy9%2F2e3clcxXd3lyV78roc9GKsyuKuNYO7abeFu9ofgSNZG4JQHOG6o6em1S28jBNeHcXr4HNn8LpMvfmoXp%2BwES%2FDLLoZQ%2BODDRwN3VC5hXZdnsaolZFMjkrbnXKjUrJ3jyOfvTwP2OBQVYs2Dct6KVq3tGm4T5v4y8z5z3%2FSECB259hbBQxSzkFECWERBEk4vScsWEbExXFcIijYEwO84PdPbf5fnJdpT%2F8po7fkXzWaqo%2BKmlpWT2sL38YASzciYYEKySDl%2FFpx6ibpv7M04DpnJ%2Bj6cp30kTwu%2BZMYR0%2B%2B21%2FWRDedHOSW5gDIVTFgbaSJAbmvxS9Kq0O87zyZrueV3NSgkg%2BbRNyEFE3liIcqo94XxE3tZcQb1XFzBBBvwx2r3YMyjbIeVFsOlDp8bJXm2VE7PzJH1DRP4xFmeUSdTvnE8rmzuYgV84VDbt0oTmwBMwWGxiH9yJOAhhb4kwiJv90%2FX7iYydCHinRSo76wNeyOWmoH1bKkGu5I2l0E%2FCN7bE84fIT6Wm0ZZHHpfZrnk0JmF3yxrUDJW6QPer%2FNDmblZ%2FsYR5gFC5iFG0xdbgjwE%2BYOjaFNUJwEyEnURf0kiibTaYxpspGNqXtqKiy7c6bCbtNUNJGoeZkSqzx7I5MyL7PLHRv7YUR9GeF01BdEGEOS9YlQUqnQggtLlF9ag17qt211jRYzYcLgWiBUScR8qo0whkbHiiMsOFafaJIqaRJr1DGaxIJaXi3iy9Uk8ON5bxF3nK4hDomxOaVLOPD2BSGjoJO6Ba2wrUDIrA0hmJDqN0IghG0boaEOsOgoe7mP%2F9hFO%2BrYp4hivWGPBO%2FFsEdGwR0Je2yYVrpPwxSe6S9IyPuvqjGdEdC7Zmtq7HbSBm8kEZUEwkHNqiOfYJfNJ3SMU7C7UandXbyk9ekKXjD%2FU7omSpA7fjgDEPeT%2FzFb53ftBriAXhfJgMhwPAaQN1okY8OixhP7Uyv7o0C8Li5AWYVhdmKEPWKoHJccKp2ODZUwwr%2BXmRF2Rz6nmUsV%2F9Ohl%2BBBot0o2utESTEmsVofKB0YBJ6zPk%2Bg%2BgNNH9Errae1x3pe8F6FYWUbGZP2ItJCxYytQl81%2BXdcG%2FrjNkzmlgoavCEN%2FsqZIY0HQE7ZSSGSjuiIVXeg%2F%2FQgIp3fgQ0qztxolA1Sui9GO9VqdbFBoLq0cp5VIvbepls5MGelLC9JfmznV%2FXbUbKdD%2FnFSlSUwshu0FEaQoIIxZvQZQ4OWvAuDyfxMnV4qog2I%2FxrheP%2BRpvF6mLDVM2JbTT3PIQBp5SAE%2BIVqLxeRLxcPVF9eEMO8aTxdS5PVYiNTb0x%2FNWzfGBo3IbHVucUHuV7K6YAKM9rLX5Ru9O9qmqo1Ns%2BSgqk1e6IGECbfUdmrFcURXzvL4gt0ocjrSx9WFcQC6ngtk1k1STPIdPXYeb%2B0GMfyHFvJzWJ8DLJcmdofA%2FA2494tKh5w9KaV5t3Ai3dy0ug9r2euehj2ooYQ8nA1%2BdkwjqhU5DRnAAMS05aqS%2BJbZ3c1irdVumOvui3mt0amhXrOD0o8%2BZF4%2B6H21PEmslJTdqFP7tCFPFeZI8%2FJdHiNHIn%2B%2BPWR27pI59Mfismf%2BQY7Zp8A7rhp1lrlSKu5QA3DZjqbXaMhwoPsOF2dVn%2B3bdfjhCQDLIfZ1Dmvwv5lPEYdoqt8nzBihvVdUsJfvUojgZ252F4Xhaw9joLLlHw2XcjEpMpNxwPfJ2ikH%2FDZM8Y3%2FKM9Ol0arjKbyt4zsSx1UP9sWBtl%2FOzcpKvQ8EfKaAcGTUhCetspNnnnXiU4b8OUMzMd4xRxJeoKboTqWf45xKH2zOC9PKb9E%2Bnd%2BmqEL2JY61IiAytUzKkWAP0%2FNsDNwSmhNkliyWLW%2FjwcMJTgeeoW4Aq1itZowlG%2B8O2Hpt0UzO7hR4M3r%2FgBUkqvB42cUD2TwzrsQ6a427poGIZqIRRv%2Bn3%2BgWWkfefxg6sy1DN9Kpi%2FQI1Tt1YHLfquowWkoni0sKai8Y4Hyo6eiF7nJKwVay5qE7hw3C5OCVFk9NJtK%2B3APv3l9cvBuf2qCxHWVde32onLfHG4vTmS6RMqLMHZbkjWYZ2lr3sLjytfT3u4GNnDN8ViucTgiIP%2Bh%2Fv38y1X77UzorRzeuD4rNfByWwKwpRYhEVGUghl3KZPZ4jfv1SCEWpNoFUW03mF09r5BXJHrNQ9KHDmSR1rcCmRui0Rt5hhMZtI6RYG63fCBU%2FiqSyco0ipFi4AIBTd1LV0fNpAcdWODSSLsnlCatYBFktuDB4Bt3yZs9RLUuH4XlVUrXpztJf01na6zurik%2FMH9u90sTqOVE1oIFVed715bMVH9ZYBQF%2F4AUK0YxXmhdL3Sb86PkjWiD%2Fd7HDFYFr5cFVjJ7NVjWXWf2wUsWqLI1xtGIpqLtmFQvWF9xyctdFvFB0grMKppgrkNOxNJMMT7pcxZSovr180sKcNwC1UFeIgl2bKJRIlFSphdWlhI%2FWQl0xsaNZNYQhXrnxTerWbxNq1DTEGQpXu64x7u5ewzdfv5nfNv%2FzvtO%2Frz%2Fd%2Fndz1m6G5JXLYKXPWqD1XiQTle9dNl2ZakAD1OGhp8xPdUykDY5arTDpiugToLFXGYoL7lrOULE6Sm1curLH91RPvAcjVgEiZoEfcRQ%2BuIoFroIfUcIBPfA%2BwWEVahhsRaqpIjjYbkR4Ge722EdmNOafiYf5Gf8H) to check out the architecture and the components:

## 9. Settings

To define the settings of the events collect, just edit the file `scheduler/etc/settings.json` and set the 
following attributes:

- `tps`: It's the number of events that should be processed per second. You can find this information analyzing the data
in WSA.
- `eventsPerJob`: Number of events that should be fetched per request. This information is essential to determine the
number of requests/workers to collect the events per minute. Depending on the volumetry, you will need to scale the 
`consumer`.

By default, the `consumer` will generate mock events. To define the credentials to connect in Akamai SIEM, just edit the
file `consumer/etc/settings.json` and set the following attributes:

- `edgercFilename`: Path for the Akamai EdgeGrid credentials file. You also need to map a volume pointing to this path.
- `edgercSection`: Identifier of the section in the `edgercFilename`.
- `configsIds`: Identifiers of the security configs separated by comma.

By default, the `processor` will store the events in the disk (The default path of the storage is the `data` directory
in the home directory). This architecture also supports [`Apache Kafka`](https://kafka.apache.org) to process/store the 
events collected. If you want to use an external instance of `Apache Kafka`, just edit the file 
`processor-kafka/etc/settings.json` and specify the connection attributes of the instance. 
Depending on the volumetry, you will need to scale the `processor`.

To define the customer SIEM attributes, just edit the file `logstash-kafka-<siem-identifier>/etc/logstash.conf`.

And that's it!!