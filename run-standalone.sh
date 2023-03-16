#!/bin/bash
docker run -d -p 8080:8080 -m 144m --cpus=0.25 csonyu/sample-rate-limiting-app:1.0.0