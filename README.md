# reactive-world-cities

#### A simple reactive Spring Boot web application.

**Prerequisites:**

* Java 15
* [Apache Maven](https:http://maven.apache.org/)
* [Docker] (https://www.docker.com/) 

You can download PostgreSQL on the above website, or if you have Docker installed,
grab an image from Docker Hub and run the image. 
```bash
docker pull bitnami/postgresql:12
docker run --name postgresql -e POSTGRESQL_PASSWORD=password123 -e POSTGRES_DB=spring -p 5432:5432 bitnami/postgresql:12
```

## How to build and run

Assuming Java, Maven and Docker are already installed on your local machine, type

```bash
mvn package
mvn spring-boot:run
```

to build and run the application.