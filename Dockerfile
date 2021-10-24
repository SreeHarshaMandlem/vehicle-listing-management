FROM openjdk:8
EXPOSE 8080
ADD ./target/dealer-listing-management.jar dealer-listing-management.jar
ENTRYPOINT ["java", "-jar", "dealer-listing-management.jar"]