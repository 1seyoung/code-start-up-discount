# Ubuntu 베이스 이미지 사용
FROM openjdk:17-jdk-apline as builder

# 패키지 설치
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    apt-get clean;

RUN ./gradlew clean -x test build --refresh-dependencies

FROM openjdk:17-jre-alpine as runner

# 애플리케이션 파일 복사
COPY --from=builder   /app/codestartup-0.0.1-SNAPSHOT.jar /app/codestartup-0.0.1-SNAPSHOT.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", " /app/codestartup-0.0.1-SNAPSHOT.jar"]


