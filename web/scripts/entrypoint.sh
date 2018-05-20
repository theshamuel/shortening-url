#!/bin/sh
echo "start nginx"

#set TZ
cp /usr/share/zoneinfo/$TZ /etc/localtime && \
echo $TZ > /etc/timezone

#copy /etc/nginx/medregistry.conf if mounted
if [ -f /etc/nginx/shrturl.conf ]; then
    cp -fv /etc/nginx/shrturl.conf /etc/nginx/conf.d/shrturl.conf
fi

echo "PROXY_SERVER=${PROXY_SERVER}, PROXY_PORT=${PROXY_PORT}"
PROXY_SERVER=${PROXY_SERVER}
PROXY_PORT=${PROXY_PORT}

#replace PROXY_SERVER and PROXY_PORT by actual keys
sed -i "s|PROXY_SERVER|${PROXY_SERVER}|g" /etc/nginx/conf.d/*.conf
sed -i "s|PROXY_PORT|${PROXY_PORT}|g" /etc/nginx/conf.d/*.conf


nginx -g "daemon off;"
