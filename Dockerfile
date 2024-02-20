# Ubuntu 베이스 이미지 사용
FROM ubuntu:latest

# 패키지 설치
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    apt-get clean;

# 애플리케이션 파일 복사
COPY ./build/libs/codestartup-0.0.1-SNAPSHOT.jar /app/codestartup-0.0.1-SNAPSHOT.jar
# 애플리케이션 실행
CMD ["java", "-jar", "/app/codestartup-0.0.1-SNAPSHOT.jar"]
