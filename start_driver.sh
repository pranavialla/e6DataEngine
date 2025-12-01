#!/bin/bash
if [ "$#" -lt 3 ]; then
    echo "Usage: ./start_driver.sh <port1> <port2> <port3>"
    exit 1
fi
sleep 2
java -jar driver.jar "$@"