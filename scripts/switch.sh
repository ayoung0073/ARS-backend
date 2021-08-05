#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT" >> /home/ec2-user/log/deploy.log
    echo "> Port 전환" >> /home/ec2-user/log/deploy.log
    echo "set \$service_url http://172.31.33.165:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

    echo "> 엔진엑스 Reload : docker exec -d nginx nginx -s reload" >> /home/ec2-user/log/deploy.log
	  sudo docker exec -d nginx nginx -s reload
}