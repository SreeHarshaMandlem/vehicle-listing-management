# Vehicle Listing Management

Vehicle Listing Management provides a platform that can receive the listings from the dealers through different providers, and make them available on the platform.

* Dealer 	- The company that is publishing the car
* Listing	- The car that is being published
* Provider	- The platform the dealers already use to manage their own listings. Different providers send data in different formats, like CSV, JSON. 

Vehilce Listing Management enables dealers to get listings from different sources(providers) and make them available here in a standardized format.

## Architecture
![Architecture](./docs/architecture.png?raw=true "architecture.png")

## Project Setup
### Libraries used
1. Spring Initializer for Spring Boot Starter setup
2. Spring Boot Actuator for application monitoring/managing
3. Swagger for describing, visualizing the API
4. Commons CSV for processing CSV
5. H2 as database
6. Flyway for DB migrations and local test data setup
7. SLF4J for logging

### Local Setup
The following are used for developing the application,
1. Intellij Enterprise Edition (Available here: https://www.jetbrains.com/idea/download/)
2. Java 8 (Available here: https://www.oracle.com/java/technologies/downloads/#java8)
3. Docker Desktop (Available here: https://www.docker.com/products/docker-desktop)
4. Github
5. Git CLI (Available here: https://git-scm.com/downloads)

#### Adding the project to local development environment
1. Import using Git CLI:
Use following command from command line.
`git clone git@github.com:SreeHarshaMandlem/vehicle-listing-management.git`

2. Import using Intellij: 
Use instructions from here: https://blog.jetbrains.com/idea/2020/10/clone-a-project-from-github/

#### Runnning Application Locally
Prerequisites:
The application uses default port `8080`. Make sure that there is no other application running on this port. Otherwise the default port needs to be changed, depending on how you want to run the application

In order to run the application locally and add necessary properties, a spring profile called `local` is used. This profile has to be added to application environment while running locally.
##### Run as Intellij Spring Boot configuration.
* Intellij automatically adds the application in its configurations with name `VehicleListingManagementApplication`. 
* Under `Run/Debug Configurations`, select the application with the name above. 
* Under `Active Profiles:` add local. Then `Run` the application from toolbar.
* If you want to use port other than 8080, under `Run/Debug Configurations` -> `Environment` -> `VM Options`, add `-Dserver.port=<desired_port>`.
    
##### Run as Docker image.    
* Move to root folder of the project where file named `Dockerfile` is present.
* Run command `docker image build -t vehicle-listing-management .` to build image with name `vehicle-listing-management`
* Run command `docker container run run -e "SPRING_PROFILES_ACTIVE=local" --publish 8080:8080 --detach --name vehicle-listing-management vehicle-listing-management` to start the container.
* If you want to use port other than 8080, use `--publish <desired_port>:8080` instead.

##### Testing Locally.    
Postman can be used to test the APIs from local machine. Import the following collection into local postman(Also attached sample CSV file to test).
[postman_collection.json.zip](./docs/postman_collection.json.zip) \
[Example file.csv](./docs/file.csv)

#### API Documentation and Swagger/Spring Fox
Swagger/Spring Fox provides API documentation out of the box. Once the application is run as stated above, the documentation is made available at http://localhost:8080/swagger-ui/ (if port is changed, use it instead of 8080)