FROM eclipse-temurin:17

LABEL maintainer="fidannceferli@gmail.com"

WORKDIR  /app

COPY target/spring-security-0.0.1-SNAPSHOT.jar /app/springboot-docker-demo.jar


ENTRYPOINT ["java", "-jar", "springboot-docker-demo.jar"]
