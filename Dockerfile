FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 8081

COPY target/task_8_5-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]