FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY . .

ARG EMAIL_ADDRESS
ARG EMAIL_PASSWORD
ENV EMAIL_ADDRESS=$EMAIL_ADDRESS
ENV EMAIL_PASSWORD=$EMAIL_PASSWORD

RUN mvn clean package -Dmaven.test.skip=true


FROM eclipse-temurin:21-jre-alpine AS config-server
WORKDIR /app
COPY --from=build /app/config-server/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

FROM eclipse-temurin:21-jre-alpine AS discovery-service
WORKDIR /app
COPY --from=build /app/discovery-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

FROM eclipse-temurin:21-jre-alpine AS gateway-service
WORKDIR /app
COPY --from=build /app/gateway-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

FROM eclipse-temurin:21-jre-alpine AS user-service
WORKDIR /app
COPY --from=build /app/user-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

FROM eclipse-temurin:21-jre-alpine AS notification-service
WORKDIR /app
COPY --from=build /app/notification-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
