#!/bin/bash

set -m

#set TZ
cp /usr/share/zoneinfo/$TZ /etc/localtime && \
echo $TZ > /etc/timezone
echo "MONGO_SERVER=${MONGO_SERVER}, MONGO_PORT=${MONGO_PORT}"
echo "MONGO_ADMIN=${MONGO_ADMIN}, MONGO_ADMIN_PASSWORD=${MONGO_ADMIN_PASSWORD}, MONGO_DB=${MONGO_DB}"
echo "MONGO_AUTH=${MONGO_AUTH}"

if [ "${MONGO_AUTH}" = true ]; then
(mongod --auth --replSet "shrturl-rs") &(
sleep 5;
echo "=> Creating ADMIN pwd"
mongo admin --eval "db.createUser({ user: '$MONGO_ADMIN', pwd: '$MONGO_ADMIN_PASSWORD', roles: [ { role: 'root', db: 'admin' } ] });" && /
/restore.sh && /
/create_user.sh &&/
/backup_script.sh)
else
  (mongod --replSet "shrturl-rs") &(
  sleep 5;
  # mongo --eval "rs.initiate(); myconf = rs.conf(); myconf.members[0].host = 'shrturl-db:27017'; rs.reconfig(myconf,{force:true}); rs.add('shrturl-db-replica:27017');"
  echo "=> Mongo start in test mode without credentials"
  /restore.sh
  )
fi
fg
