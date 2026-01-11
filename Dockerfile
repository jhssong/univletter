## 1. Build stage (Gradle)
#FROM gradle:8.7-jdk21-alpine AS build
#
#WORKDIR /app
#
#ARG GPR_USER
#ARG GPR_KEY
#
#COPY build.gradle settings.gradle gradlew ./
#COPY gradle ./gradle
#
#RUN chmod +x gradlew
#
#RUN ./gradlew dependencies --no-daemon \
#    -Pgpr.user=${GPR_USER} \
#    -Pgpr.key=${GPR_KEY}
#
#COPY src ./src
#
#RUN ./gradlew bootJar -x test --no-daemon \
#    -Pgpr.user=${GPR_USER} \
#    -Pgpr.key=${GPR_KEY}
#
## 2. Run stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

ENV TZ=Asia/Seoul

#COPY --from=build /app/build/libs/*.jar app.jar
COPY build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]
