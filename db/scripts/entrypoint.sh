#!/bin/bash

set -m

#set TZ
cp /usr/share/zoneinfo/$TZ /etc/localtime && \
echo $TZ > /etc/timezone
echo "MONGO_SERVER=${MONGO_SERVER}, MONGO_PORT=${MONGO_PORT}"
echo "MONGO_ADMIN=${MONGO_ADMIN}, MONGO_ADMIN_PASSWORD=${MONGO_ADMIN_PASSWORD}, MONGO_DB=${MONGO_DB}"
echo "MONGO_AUTH=${MONGO_AUTH}"

if [ "${MONGO_AUTH}" == true ]; then
(mongod --auth --replSet 'shrturl-rs' --keyFile '/shrturl-rs.key' --port ${MONGO_PORT) &(
sleep 3;
echo "=> Creating ADMIN pwd"

mongo --eval "rs.initiate();"
sleep 3;
mongo admin --eval "db.createUser({ user: '$MONGO_ADMIN', pwd: '$MONGO_ADMIN_PASSWORD', roles: [ { role: 'root', db: 'admin' } ] });"

mongo admin -u ${MONGO_ADMIN} -p ${MONGO_ADMIN_PASSWORD} <<EOF
rs.add('shrturl-db-replica-1:27017');
EOF

/restore.sh 
/create_user.sh
echo "=> ADMIN pwd has created"
)
else
  (mongod --replSet 'shrturl-rs') &(
  sleep 5;
  mongo --eval "rs.initiate(); rs.add('shrturl-db:27017'); rs.add('shrturl-db-replica-1:27017');"
  echo "=> Mongo start in test mode without credentials"
  /restore.sh
  )
fi
fg
