#!/bin/bash

# Calculate the number of consumers to be used based based on TPS (Events triggered per second) and the limits of events that can be fetched.
eventsLimit=$(cat $ETC_DIR/scheduler.conf | jq -r .eventsLimit)
topic=$(cat $ETC_DIR/scheduler.conf | jq -r .topic)
tps=$(cat $ETC_DIR/scheduler.conf | jq -r .tps)
tpm=$((tps * 60))
consumers=$((tpm / eventsLimit))

if [ $consumers -lt 1 ]; then
  consumers=1
fi

window=$((60 / consumers))
i=0

# Define the offsets per consumers.
while [ $i -lt $((consumers)) ]
do
  offset1=$((i * window))
  offset2=$(((i + 1) * window))
  from=$(date +%s)
  from=$((from - offset1))
  to=$((from - offset2))
  message="{\"from\": $from, \"to\": $to}"

  echo "[$(date)][$message]" >> $LOGS_DIR/scheduler.log
  mosquitto_pub -I "$HOSTNAME" -t "$topic" -m "$message" -d >> $LOGS_DIR/scheduler.log

  let i++
done

# Log the execution.
chown mosquitto:mosquitto $LOGS_DIR/scheduler.log
chmod og-rwx $LOGS_DIR/scheduler.log