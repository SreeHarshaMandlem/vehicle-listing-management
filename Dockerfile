FROM openjdk:8
EXPOSE 8080
ADD ./target/vehicle-listing-management.jar vehicle-listing-management.jar
ENTRYPOINT ["java", "-jar", "vehicle-listing-management.jar"]