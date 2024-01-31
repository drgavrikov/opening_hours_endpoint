FROM openjdk:21
WORKDIR /app
COPY build/libs/OpeningHoursEndpoint-*.jar opening-hours-endpoint.jar
CMD ["java", "-jar", "opening-hours-endpoint.jar"]
