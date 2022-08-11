#!/bin/bash

# Calculate the number of consumers to be used based based on TPS (Events triggered per second) and the limits of events that can be fetched.
tps=$(cat $ETC_DIR/scheduler.conf | jq -r .tps)
tpm=$((tps * 60))
eventsPerJob=$(cat $ETC_DIR/scheduler.conf | jq -r .eventsPerJob)
outputQueue=$(cat $ETC_DIR/scheduler.conf | jq -r .outputQueue)
consumers=$((tpm / eventsPerJob))

if [ $consumers -lt 1 ]; then
  consumers=1
fi

echo $consumers

window=$((60 / consumers))
i=0

# Define the offsets per consumers.
now=$(date +%s)

# Publish the jobs in the queue.
while [ $i -lt $((consumers)) ]
do
  offset1=$((i * window))
  offset2=$(((i + 1) * window))
  to=$((now - offset1))
  from=$((now - offset2))
  message="{\"job\": \"$now-$((i + 1))\", \"from\": $from, \"to\": $to, \"eventsPerJob\": $eventsPerJob}"

  echo "[$(date)][job created][$message]" >> $LOGS_DIR/scheduler.log
  mosquitto_pub -I "$HOSTNAME" -t "$outputQueue" -m "$message" -d >> $LOGS_DIR/scheduler.log

  let i++
done

# Log the execution.
chown mosquitto:mosquitto $LOGS_DIR/scheduler.log
chmod og-rwx $LOGS_DIR/scheduler.log