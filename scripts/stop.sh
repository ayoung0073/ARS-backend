#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

IDLE_PROFILE=$(find_idle_profile)
echo "> 현재 프로필 : ${IDLE_PROFILE}"  >> /home/ec2-user/log/deploy.log

CONTAINER_ID=$(docker container ls -f name=${IDLE_PROFILE} -q)
echo "> 컨테이너 ID : ${CONTAINER_ID}"  >> /home/ec2-user/log/deploy.log

if [ -z ${CONTAINER_ID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."  >> /home/ec2-user/log/deploy.log
else
  echo "> docker stop $IDLE_PROFILE && sudo docker rm $IDLE_PROFILE"  >> /home/ec2-user/log/deploy.log
  docker stop $IDLE_PROFILE && sudo docker rm $IDLE_PROFILE
  sleep 5
fi