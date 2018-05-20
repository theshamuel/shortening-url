#!/bin/sh
echo "Start api......."
until nc -vz shrturl-db 27017
do
    echo "Waiting api database..."
    sleep 5
done
#set TZ
cp /usr/share/zoneinfo/$TZ /etc/localtime && \
echo $TZ > /etc/timezone

java -jar /opt/shrturl/shrturl-${VERSION}.jar
