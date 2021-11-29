# Offers API

## Description
A Spring Boot RESTful Web Service application that will be used to manage product offers.

## Requirements
### Offers API

You are required to create a simple RESTful software service that will
allow a merchant to create a new simple offer. Offers, once created, may be
queried. After the period of time defined on the offer it should expire and
further requests to query the offer should reflect that somehow. Before an offer
has expired users may cancel it.


The following are tasks that the application supports:
- Create a new offer with expiry date in the backend system - `POST /offers`
- Get the offer using the offer id - `GET /offers/{id}`
- Get all the offers in the backend system - `GET /offers`
- Cancel an existing offer before it is expired in the backend system - `POST /offers/{id}/cancel`
- Delete an existing offer in the backend system - `DELETE /offers/{id}`
- A Scheduler configured with a cron expression to run everyday midnight which updates the status for expired offers

Technologies used:

- JDK 11
- Gradle 7.2
- Spring Boot 2.5.5
- Junit 4


## Building the Application
Application can be build and packaged in a docker image using any of below options
#### Option 1 
- Spring boot latest version uses built in module to generate docker images by executing the below gradle task from project root path
```
./gradlew bootBuildImage
```

#### Option 2 
- Traditional way of building docker image, by running below commands from project root path
  - Build the project by running the gradle task
    ```
    ./gradlew build
    ```
  - Build the docker image by running the docker command
    ```
    docker build --tag=offers-api:1.0.0 . 
    ```

## Running the Application
Start the service by running the docker container for the image generated in above steps.
- Docker
```
docker run -p8050:8050 offers-api:1.0.0
```

- Commandline, from project root path
```
./gradlew bootRun
```

## Swagger Document and testing
Below endpoint can be used to see the documentation in browser and also tryout the APIs

http://localhost:8050/api-docs

## Access Database
Offers table will be automatically generated in Database once the application is started.

To access the database, open [H2-Console](http://localhost:8050/h2-console)
```
database=jdbc:h2:mem:testdb
username=admin
password=admin
```
