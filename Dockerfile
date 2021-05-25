FROM openjdk:11-jre-slim
VOLUME /tmp
ARG JAR_FILE=/home/ec2-user/compose/jenkins_home/jenkins_home/jobs/SpringbootPractice/workspace/build/libs/ars-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
