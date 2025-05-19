FROM gradle:jdk17 AS gradle-build

WORKDIR /app

COPY . .

RUN chmod +x /app/gradlew

RUN /app/gradlew clean build

FROM amazoncorretto:17-alpine

COPY --from=gradle-build /app/build/libs/mina_marken-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app.jar"]