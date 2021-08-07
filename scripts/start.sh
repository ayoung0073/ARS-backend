#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)
DEPLOY_PATH=/home/ec2-user/deploy/
REPOSITORY=/home/ec2-user/deploy

BUILD_JAR=$(ls /home/ec2-user/jenkins/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_JAR)

echo "> Build 파일 복사"  >> /home/ec2-user/log/deploy.log
cp $BUILD_JAR $DEPLOY_PATH 	# 새로운 jar 파일을 덮어쓴다.

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> DEPLOY_JAR 배포"    >> /home/ec2-user/log/deploy.log
JAR_NAME=$(ls -tr $DEPLOY_PATH*.jar | tail -n 1)

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 를 profile=$IDLE_PROFILE 로 실행합니다."  >> /home/ec2-user/log/deploy.log

cd $DEPLOY_PATH

docker build -t ars ./
docker run -it --name $IDLE_PROFILE -d -e active=$IDLE_PROFILE -p $IDLE_PORT:$IDLE_PORT ars
echo ">  Success Docker run"  >> /home/ec2-user/log/deploy.log
