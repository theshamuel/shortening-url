#!/bin/sh
echo "Start api......."
until nc -vz localhost 27017
do
    echo "Waiting api database..."
    sleep 5
done
#set TZ
cp /usr/share/zoneinfo/$TZ /etc/localtime && \
echo $TZ > /etc/timezone

java -Xmx1024m -jar -XX:+UseG1GC -jar /opt/shrturl/shrturl-${VERSION}.jar
