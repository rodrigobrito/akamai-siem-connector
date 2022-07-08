# Akamai SIEM Connectors

Description about this project

[![Build Status](https://travis-ci.org/akamai/AkamaiOPEN-edgegrid-golang.svg?branch=master)](https://travis-ci.org/akamai/AkamaiOPEN-edgegrid-golang)
[![GoDoc](https://godoc.org/github.com/akamai/AkamaiOPEN-edgegrid-golang?status.svg)](https://godoc.org/github.com/akamai/AkamaiOPEN-edgegrid-golang)
[![Go Report Card](https://goreportcard.com/badge/github.com/akamai/AkamaiOPEN-edgegrid-golang)](https://goreportcard.com/report/github.com/akamai/AkamaiOPEN-edgegrid-golang)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://github.com/akamai/AkamaiOPEN-edgegrid-golang/blob/master/LICENSE)

- This project is to Integrate Akamai SIEM systems and for security data analysis.

Prerequisites to run this project

 - Visual Studio Code (https://code.visualstudio.com/)
 - Docker 
   - For Ubuntu - (https://docs.docker.com/engine/install/ubuntu/)
   - For Debian - (https://docs.docker.com/engine/install/debian/)

Prepare vscode IDE

 - install the following plugins (https://www.youtube.com/watch?v=r5dtl9Uq9V0)
    - gitlens
    - vscode-icons
    - React (https://www.youtube.com/watch?v=9EMUJm7qdxM)


After you installing prerequisites you can execute the following steps to run

 - git clone git@github.com:lcassiano/akamai-siem-connector.git
 - cd akamai-siem-connector
 - docker-compose up -d