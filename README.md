# reactive-world-cities

#### A simple reactive Spring Boot web application.

**Prerequisites:**

* Java 15
* [Apache Maven](https:http://maven.apache.org/)
* [Docker](https://www.docker.com/) 

If you have Docker installed, grab an image from Docker Hub and run the image. 
```bash
docker pull bitnami/postgresql:12
docker run --name postgresql -e POSTGRESQL_PASSWORD=password123 -e POSTGRES_DB=spring -p 5432:5432 bitnami/postgresql:12
```

## How to build and run

Type

```bash
mvn package
mvn spring-boot:run
```

to build and run the application.