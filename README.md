# angular-springboot-with-docker

------------

Auteur : MONKAM NAKTAKWEN STEVY

LinkedIn : https://www.linkedin.com/in/stevy-monkam-naktakwen-a84299181/

# Dockerizing a Spring Boot + MySQL Application

## Overview

This guide explains two methods for dockerizing a Spring Boot application with a MySQL database: using a docker network and utilizing docker-compose.

### Application Description

The application facilitates CRUD operations for an employee-management system, including functionalities like adding, getting, and updating employees.

#### Project Structure

![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20170819.png)

#### Application Configuration

To configure the database, refer to the `application.properties` file.

![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20170538.png)

#### Building the Application Jar

To build the Spring Boot application jar file:

1. Right-click on `pom.xml` → Run As → Maven build.
2. The generated jar file (`backend-0.0.1-SNAPSHOT.jar`) will be in the `target` folder.

![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20171127.png)


## Dockerization Process

### 1. Using a Docker Network

1. Pull the MySQL image from Docker Hub:
```
docker pull mysql:5.7
```

2. Create a Docker image for the Spring Boot app:
```
docker build -t backend1 .
```
![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20175618.png)

3. Create a Docker network:
```
docker network create springmysql-net
```
4. Run the MySQL container in the network:
```
docker run --name mysqldb --network springmysql-net -e MYSQL_ROOT_PASSWORD=manounou -e MYSQL_DATABASE=ecomerce -e MYSQL_USER=stevy -e MYSQL_PASSWORD=manounou -d mysql:5.7
```
5. Verify MySQL container logs:
```
docker logs mysqldb
```
![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20202002.png)

6. Verify if the database `ecomerce` is created:
```
docker exec -it mysqldb mysql -uroot -proot -e "show databases;"
```
7. Run the Spring Boot container in the same network:
```
docker run --name spring-boot-app --network springmysql-net -p 8080:8080 -d backend1:latest
```
![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20192546.png)


8. Verify containers are running correctly:
```
docker ps
```
![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20180303.png)

9. Check Spring Boot container logs:
```
docker logs spring-boot-app
```
![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20202425.png)

# push in docker hub 

```
docker login
```
### change image name
```
docker tag id_image  username/imageName:tag
```
push image
```
docker push stevymonkam/backend1:1.0
```
![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20200456.png)

# Using docker-compose
Without going through these many steps we can do the same thing with one command docker-compose.

To do that, we need to create docker-compose.yml file which includes the following.

```yaml
    version: "3"
    services:
      server:
        image: stevymonkam/backend1:1.0
        ports:
          - "8080:8080"
        environment:
          - spring.datasource.url=jdbc:mysql://mysqldb:3306/ecomerce?useSSL=false
        networks:
          - springmysql-net
        depends_on:
          - mysqldb
    
      mysqldb:
        image: mysql:5.7
        networks:
          - springmysql-net
        environment:
          - MYSQL_ROOT_PASSWORD=manounou
          - MYSQL_DATABASE=ecomerce
          - MYSQL_USER=stevy
          - MYSQL_PASSWORD=manounou
        volumes:
          - ./mysql-data:/var/lib/mysql  # Montage du volume local dans le conteneur MySQL
    
    networks:
      springmysql-net:

```

# create diretory 

```
mkdir mysql-data
```

Then Open the command terminal inside the project folder and do the following command.
```
docker-compose up
```
![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20200200.png)

![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20192546.png)
