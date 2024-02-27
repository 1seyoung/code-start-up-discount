#Docker Task1 : 멀티스테이지 빌드


#base image ubunt >> jdk
FROM openjdk:17-jdk-slim-buster as builder

WORKDIR /app


#gi clone
RUN apt-get update && \
    apt-get install -y git && \
    git clone -b 2week_assignment https://github.com/1seyoung/code-start-up-discount.git .

# gradlew >> 권한 문제
RUN chmod +x ./gradlew && ./gradlew build


#base image ubunt >> jdk
FROM openjdk:17-jdk-slim-buster as run
WORKDIR /app

#copy jar in builder stage
COPY --from=builder /app/build/libs/codestartup-0.0.1-SNAPSHOT.jar /app/codestartup-0.0.1-SNAPSHOT.jar

#Docker Task2 : entrypoin 와 cmd
#Docker Task3 : option 실행 시 자주 쓰는 옵션
#run app
#CMD ["java", "-jar", "/app/codestartup-0.0.1-SNAPSHOT.jar"]

#CMD : 기본 인자를 제공
#ENTRYPOINT : 컨테이너가 시작될 때 실행되는 기본 명령을 설정
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "/app/codestartup-0.0.1-SNAPSHOT.jar"]

