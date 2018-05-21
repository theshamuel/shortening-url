#!/bin/bash

set -m

#set TZ
cp /usr/share/zoneinfo/$TZ /etc/localtime && \
echo $TZ > /etc/timezone
echo "MONGO_SERVER=${MONGO_SERVER}, MONGO_PORT=${MONGO_PORT}"
echo "MONGO_ADMIN=${MONGO_ADMIN}, MONGO_ADMIN_PASSWORD=${MONGO_ADMIN_PASSWORD}, MONGO_DB=${MONGO_DB}"
echo "MONGO_AUTH=${MONGO_AUTH}"

if [ "${MONGO_AUTH}" == true ]; then
(mongod --auth --replSet 'shrturl-rs' --keyFile '/shrturl-rs.key' ) &(
sleep 3;
echo "=> Creating ADMIN pwd"
sleep 3;
mongo admin --eval "db.createUser({ user: '$MONGO_ADMIN', pwd: '$MONGO_ADMIN_PASSWORD', roles: [ { role: 'root', db: 'admin' } ] });"
/create_user.sh
echo "=> ADMIN pwd has created"
)
else
  (mongod --replSet 'shrturl-rs') &(
  sleep 5;
  echo "=> Mongo start in test mode without credentials"
  /restore.sh
  )
fi
fg
