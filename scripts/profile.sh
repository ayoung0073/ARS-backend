#!/usr/bin/env bash

function find_idle_profile()
{
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/api/profile)

    if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함)
    then
        CURRENT_PROFILE=dev2
    else
        CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    if [ ${CURRENT_PROFILE} == dev1 ]
    then
      IDLE_PROFILE=dev2
    else
      IDLE_PROFILE=dev1
    fi

    echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile 의 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == dev1 ]
    then
      echo "8082"
    else
      echo "8083"
    fi
}