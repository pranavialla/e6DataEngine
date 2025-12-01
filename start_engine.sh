#!/bin/bash
if [ -z "$1" ]; then
    echo "Usage: ./start_engine.sh <port_number>"
    exit 1
fi
java -jar engine.jar $1 > engine_$1.log 2>&1 &
echo "Engine started on port $1 (PID: $!)"