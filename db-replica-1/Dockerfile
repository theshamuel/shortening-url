FROM mongo:3.4

ADD scripts/entrypoint.sh /entrypoint.sh
ADD scripts/backup_script.sh /backup_script.sh
ADD scripts/shrturl-rs.key /shrturl-rs.key

EXPOSE 27017

RUN \
 chmod +x /entrypoint.sh && \
 chmod +x /backup_script.sh && \
 chmod 400 /shrturl-rs.key && \
 mkdir /backup && \
 apt-get update && \
 export DEBIAN_FRONTEND=noninteractive && \
 apt-get install -y tzdata && \
 apt-get install -y netcat && \
 apt-get clean

CMD ["/entrypoint.sh"]
