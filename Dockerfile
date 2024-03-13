FROM  eclipse-temurin:17-jdk-jammy as builder

COPY . /app
WORKDIR /app

RUN ./gradlew clean -x test build

FROM eclipse-temurin:17-jre-jammy as runner

COPY --from=builder /app/build/libs/codestartup-1.0.jar /app/app.jar

EXPOSE 8080/tcp

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=sandbox", "/app/app.jar"]

# multi-stage build
# cluster -> k8s -> image
# process 격리
# jvm option
# env di -> yaml

# java -> bytecode (기계어가 아님) -> jit