#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

BUILD_JAR=$(ls /home/ec2-user/jenkins/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
DEPLOY_PATH=/home/ec2-user/deploy/

echo "> Build 파일 복사"  >> /home/ec2-user/log/deploy.log
cp $BUILD_JAR $DEPLOY_PATH

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> DEPLOY_JAR 배포"    >> /home/ec2-user/log/deploy.log
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 를 profile=$IDLE_PROFILE 로 실행합니다."  >> /home/ec2-user/log/deploy.log
nohup java -jar \
    -Dspring.profiles.active=$IDLE_PROFILE \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &