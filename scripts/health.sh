#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> Health Check Start!" >> /home/ec2-user/log/deploy.log
echo "> IDLE_PORT: $IDLE_PORT" >> /home/ec2-user/log/deploy.log
echo "> curl -s http://localhost:$IDLE_PORT/api/profile " >> /home/ec2-user/log/deploy.log
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/api/profile)
  UP_COUNT=$(echo ${RESPONSE} | grep 'dev' | wc -l)

  if [ ${UP_COUNT} -ge 1 ]
  then # $up_count >= 1 ("real" 문자열이 있는지 검증)
      echo "> Health check 성공" >> /home/ec2-user/log/deploy.log
      switch_proxy
      break
  else
      echo "> Health check의 응답을 알 수 없거나 혹은 실행 상태가 아닙니다." >> /home/ec2-user/log/deploy.log
      echo "> Health check: ${RESPONSE}" >> /home/ec2-user/log/deploy.log
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
    echo "> Health check 실패. " >> /home/ec2-user/log/deploy.log
    echo "> 엔진엑스에 연결하지 않고 배포를 종료합니다." >> /home/ec2-user/log/deploy.log
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..." >> /home/ec2-user/log/deploy.log
  sleep 10
done