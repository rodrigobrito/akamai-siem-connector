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

If you want to collaborate in this project, reach out us by e-Mail. These are the current backlog:

- Define a retry strategy for `scheduler`, `consumer`, `processor`
- Define the healthcheck attributes the containers images.
- Create K8S manifests.
- Create Terraform recipes for deployment (AWS, GCP, Azure, Linode, etc.)

### 4. Versions
***
- [`1.0.0`]() - Stable (For production use)
- [`latest`]() - In constant improvement (For development or test use).

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

Follow this [diagram](https://viewer.diagrams.net/?tags=%7B%7D&target=blank&highlight=FFFFFF&edit=_blank&layers=1&nav=1&title=Akamai%20SIEM%20Connector%20Architecture.drawio#R7V1bc%2BK4Ev41PGbLd8xjrjM5O6nNDmdrd%2BZN2AK8MRZji1zOrz%2BSLQF2C3AS23ICSVWCZWHs%2FtSt%2FrpbYmBfLp6%2FpGg5vyMhjgeWET4P7KuBZZmO5bF%2FvOWlaPGHbtEwS6NQdNo0jKP%2FYdFoiNZVFOKs1JESEtNoWW4MSJLggJbaUJqSp3K3KYnLn7pEMwwaxgGKYevfUUjn4ims4ab9K45mc%2FnJpjcqziyQ7CyeJJujkDxtNdnXA%2FsyJYQWrxbPlzjmwpNyKd53s%2BPs%2BsZSnNA6b%2FiKHkc%2Fz8Z%2FvlBs%2F%2Fr5dPe79XB7Jq7yiOKVeODzB7RAEWsb317fsX%2Fn97fs73USLknEPqh4FPoi5ZOSVRJi%2FhHmwL54mkcUj5co4Gef2IhgbXO6iMXpKUmogNh0%2BHEUx5ckJml%2BLTtE2J8GrD2jKXnAW2e8wMeTKTsDH1o%2BAU4pft5qEkL4gskC0%2FSFdRFnraFASIxIT%2BDztIHXNkTbfAta2Q%2BJETVbX3kjdPZCyP01GBgAhP%2BQScZa%2Flxh1lIVORtIS%2F4yeIkjJvvUPiz4SYHSt8m6AQUPsxy7P1aUXQaL9kyg41bQMhVoudgPHRVavjWxPa8ZtLwyWCq0PAVYw7bAkvZsCywAEA6ZCRGHJKVzMiMJiq83rRcbrTHY0abPN0KWAol%2FMaUvQvxoRUkZ0IyilJ5z%2B8YHQoyyLApk800Uy247AcjIKg3wvkEpnpNdcIbpvo7CEvKH3gtoimNEo8eyXW0cH8fWAQd%2Bjug%2FW69%2F8Ev95oqjq2dx5fzgRR4k7Hn%2F2T7Yehc%2F3LwtP5LvK0GfEK66JdyNbnC3XV24%2FzcKf06%2BGYuby%2FTrX2fffzxb36%2FOTDiT6RgI1RnuVXpaEWXbADpa8bJOePUUr723uYXXJUmy1QKn7fqEnXgZdtXNGFq%2FucDRMC2VW%2Bi25Wn4WmYyOSutD%2BrNSvnRPU4j9vA4VU1VrXgpw7peitErL0XeN9Qm%2FjBz%2FvffggJkwRyHq5hByslrmjPdOOZMdxctWKYkwFlWgxTs4AAH%2FP6py39Fv6324kfJ3vKfZjTV9KuaWldPW6NvI4BlkJJkwG%2FCiykXdFpCyvu14pw%2Fl99ZQbjOWQfTXD7nMpLn2asZ%2F5%2Fh9DEKIA9kEqNlRFEczRKuaky%2BXAkvuFyjAMXn4sQiCsPCTmD2wWiSX4prak7uc8m4FwP3il%2BLmQbJBgGowu3cHhmyqTJ4moDc9kqQO4YHIFdxQKc1DugCxBe%2FKG0O8UDagmNF3CwruW1AJR92ibgNQzSNI56ojPqxIG4bhxHvVMdtHyCuwx1r3YOyrboelC4HSk0ftYZ5NqGdH1tn1GEe%2FQyzPsSeTp9Y3uZ2LmLFfOGEWzeKc1vATIFlcEi%2F8OyRZcTRJEXis4%2FPF65mMswhDI536ws7w%2F6opbFXLWuq4SZIu2HAP7bP7aDD79BWZ4fq17PQ79Be8db7Is8nB5lb8cXWA0peoriv%2B3V2cHv8rG%2FjHWbBAWbhBtOAGwL8iLlDYxkTlOUEOWddNMpZNJlOM0zzF9uc%2BkhNheP2zlS4Ok1FF4mawyGxxrM3MilzOJjs6XLn9t73djwsxcWsLwJhDEkmE6GkUqFFLCxXfmkNjlK%2FXadvYTEbJgyuBUKNMOZTbYQ1tHpWHOHAufoUJmkyTOL4PQuTOFDLm0V8uZrEUTY%2FWsQ9r2%2BIw8DYnNIlnHiPBSGropOmA62wq0DIbg0hmJA6boQAhdWN0NAEWPQ0erkr%2FrFhO2ruU0WxXdojwTtIeyQL7gntcWFa6b6gKTzTXxkhH7%2BqxvZ8oHfd1tS4etIGbwwiKgMIezWrjQyRWzd90LOYgtuPSu3%2B4iWtT1%2Fwgvmf2jVRIrgTJTMA8XHGf2zt8V23g1jAURfJAGY4GgHIOy2ScWFR4yn602r0R4F4W7EAZRWG3YsZVjmLvXHuHNWcOz1DjVRHUyVk%2BPfrzAhKwhwCkm4mzqPOjVRpiKN9bvQg7ztnMs%2Bh%2Bh1NH9ArDaazw2BecKlCJqkjSaKPhFaKZFwV%2Bqr1vqPW0B%2FpsJLr6M%2FgDZnvVy4G6ZzzeHXXgcgIRE84jwddprEgN58hAFRdrNFpAEjpsVh6CtTaCgCBgtK9odVGXCQJYc0lV9o0C6aplBUl%2BZ%2F1kqrjdpRcTy563BNF8N0OHaUhjAmh7CUJmIODFlzkySRbFg5PEwQzxb9WODteglktKLZs1TLYTtPNQ8gx5Qg4Id6AyptVxOuVELWHNwwbnjS%2BxZBSpYTMsc3O8Fcv7IHUWIfH1umqHaUgHOhmKfv5HXlZanf6qAoZ2vW23zUKCjOuaxhAm%2F2NzJhUFHV7WkgssIcKMGqHD32jbviwLRILo7%2B6TWTDtq8uxewsCr%2FvLvfkq9cLlARvzDPWW%2FH5EODWGtFsQRGGtRWhNWcBGh4aLAflhGMld3jzCSqK3wVj1eVzFS6%2FMiDens8HV%2F8chvETpP%2FfCaNTgtFTLP4wzU5hhAVXJ%2BrWnR4Pa67%2Baa8awNHhhhwRGZCxmoNswNbqFyk2xBorqxGqxj1K1l3E5tN5cd9FNLtCFHGhsdufknTRndvUdHy%2BUQdspN0Bk8zjZPK1mHzfs%2FSafAtyoNPyv0YRN0qA2xZMoHc7x0OFB9hwu7rc%2BezCgxfyH6y%2FHqG%2BTMxKlmo0gkJxVQQGbF3SnFhqRK3fFfmqNZT2IAaFqU9YkO3dRUFKMjLlhmPMN3xK%2BLeI7JjjNS%2Ftn06nVqD8korQm3hu7an%2BVWCt90Uscz4TDnxfAaVvtYQkrF6SZp8L8V2G%2FzpGGTPfGUYp3%2Bun6k4UnuEfS5yse8TF22%2BKjy6u0tdB1FTk%2Bi2DyDJ6NYYUm6me%2Fz3mhsCWMAdksWS8hc8YPcVTp1GwDL9fgCo2fnlGE4x207YjRs827H6hB8n7d7wged3c%2BCWLye4VdseM4qhfOqjYTyuPqN98%2BI0gGo2ZjzxY7aJaMtfERhBqnPqxy3Dr1S7tZ3LFWyubV1qjMlX0zEpOvoi5NrF5pbowAtLl6kIfQy7SMf66Bdh%2FvGqJKjl3%2FboxyraqJRw9aYk3lvz3oPDMhjq7Z3BrqznSsn%2FoB4JnqBUdWLZ9hbL5hKA0hP7Hxzdz%2BovC9Gy93QN9UHx%2F2p4hqU0hauxGI4kUCigfs63EiA%2FsKVEd1TYY1U6X%2BcXPuNnguwBiTLOE0NCE63Pa2spOjdAn3GywUYRGuhFSbDJ33AhVv11KZeU6RUixHQQAp%2B2kqmeW0wKeq3BoZLiklCdsYjdp9cCF5BmI5c2eY%2B2xtAexvUnVroVlvkZYxuuFVXWzEfanymirF%2Fh4Mm1FvNLEmqWhakEDq%2FK828tnK76hZBXH%2FIYXKEEzXuZfLXWb8LPnD2iBok9sh98CrlMGVzF7dlucXmcbyUYVq83U8OsUSxG661axYH3BLQ%2FuBogXik7wtoIp1gqUdKzIJMNOl6uMEtWXWJ%2B0sOQNQC1UrS1wWxsKNRIlTWphq8nE12mhqVif060aQopXb36TuvWZqUYTU5ylcLUbmuPYYUp4YdX63BemCvM7EmLe4%2F8%3D) to check out the architecture and the components:

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