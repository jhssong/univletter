FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

ENV TZ=Asia/Seoul

COPY build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]
