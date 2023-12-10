FROM openjdk:17
EXPOSE 8080
RUN mvn clean install

CMD mvn spring-boot:run
ENTRYPOINT ["java", "-jar", "/simple-task-planner.jar","--spring.profiles.active=prod","--server.port=8080"]
