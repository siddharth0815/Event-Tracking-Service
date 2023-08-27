ARG JAVA_VERSION=17
FROM openjdk:${JAVA_VERSION}
COPY target/event-tracking-service.jar event-tracking-service.jar
EXPOSE 8080
CMD ["java","-jar","/event-tracking-service.jar"]