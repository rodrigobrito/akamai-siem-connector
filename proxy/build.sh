#!/bin/bash

mkdir ssl
openssl req -x509 -nodes -days 365 -subj "/C=BR/ST=SP/O=lcassiano.me, Inc./CN=lcassiano.me" -addext "subjectAltName=DNS:siem.lcassiano.me" -newkey rsa:2048 -keyout ssl/nginx-selfsigned.key -out ssl/nginx-selfsigned.crt
