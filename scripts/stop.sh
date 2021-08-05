#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

IDLE_PROFILE=$(find_idle_profile)
echo "> 현재 프로필 : ${IDLE_PROFILE}"

CONTAINER_ID=$(docker container ls -f name=${IDLE_PROFILE} -q)
echo "> 컨테이너 ID : ${CONTAINER_ID}"

if [ -z ${CONTAINER_ID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> docker stop {IDEL_PROFILE} || sudo docker rm {IDEL_PROFILE}"
  docker stop {IDEL_PROFILE} || sudo docker rm {IDEL_PROFILE}
  sleep 5
fi