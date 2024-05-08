# angular-springboot-with-docker

### Dans ce project je dois resoudre les besoin suivant 

1) deploy an Angular app with docker and use docker-compose to code my deploy infrastructure
2) deploy a springboot java app with docker install and configure the mysql database and make it communicate with the back end use docker-compose to codify my deploy infrastructure
3) automate the deployment of the Angular app with Jenkins, write a complete pipeline and deploy it in the cloud provider heroku


Link front end : https://github.com/stevymonkam/contratti_deploy

Link back end : https://github.com/stevymonkam/backendTest2new/tree/main

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


# Dockerizing Angular app

## Prepare your Angular application
     
      Make sure your Angular application works properly locally.
      Build a production version of your application using the Angular CLI command:
       ```
       ng build --prod.
       ```

## Create a Dockerfile:

     Create a file named Dockerfile in the root of your Angular project.
     This file will contain instructions for Docker on how to build your container

  ```yaml
    FROM node:20.11.0-alpine as build
    WORKDIR /app

    RUN npm install -g @angular/cli

    COPY ./package.json .
    RUN npm install --force
    COPY . .
    RUN ngbuild

    FROM nginx as runtime
    COPY --from=build /app/dist/test1 /usr/share/nginx/html
  ```

    ## Build the Docker image

    Run the following command to build your Docker image:
   
       ```
       docker build -tcontratti-image .
       ```

   ## Run the Docker container:

       ```
       docker run --network springmysql-net --name front-container -p 86:80contrati-image:latest
       ```
   ![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20145559.png)

# push into docker hub

```
docker login
```
### change image name
```
docker tag id_image username/imageName:tag
```
push image
```
docker push stevymonkam/contratti-image:1.0
```
![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20160111.png)


### Docker-compose:

use this docker-compose to get the same result:

```yaml
    version: "3"
services:
   front-container:
     image: stevymonkam/contratti-image:1.0
     ports:
       - "83:80"
     networks:
       - springmysql-net
     container_name: front-container
     volumes:
       - front-data:/usr/share/nginx/html
     detach: true # Move this line inside the service definition

volumes:
   front-data:
     driver: local


```

## Connect the front-end and the back-end:

modify the config file of your Angular app and point the ip address
of the host on the 8080 door of the back-end container as below:

   ![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20144755.png)


## App consumption:

After deployment of the front end and back end our application is finally ready and can be consumed via url: http://192.168.56.14:86

  
   ![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20154341.png)

   ![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20144934.png)




# CI CD angular app with jenkins:


automating our deployment with jenkins

```yaml
pipeline {
      environment {
        IMAGE_NAME = "contratti"
        IMAGE_TAG = "latest"
        STAGING = “contratti-staging”
        PRODUCTION = “contractti-production”
      }
      agent none
      internships {
          stage('Build image') {
              agent any
              steps {
                 script {
                   sh 'docker build -t contractti/$IMAGE_NAME:$IMAGE_TAG .'
                 }
              }
         }
         stage('Run container based on built image') {
             agent any
             steps {
                script {
                  sh'''
                     docker run --name $IMAGE_NAME -d -p 86:80 -e PORT=80 contractti/$IMAGE_NAME:$IMAGE_TAG
                     sleep 5
                  '''
                }
             }
        }
        stage('Test image') {
            agent any
            steps {
               script {
                 sh'''
                    curl http://localhost | grep -q "contratti"
                 '''
               }
            }
       }
       stage('Clean Container') {
           agent any
           steps {
              script {
                sh'''
                  docker stop $IMAGE_NAME
                  docker rm $IMAGE_NAME
                '''
              }
           }
      }
      stage('Push image into staging and deploy it') {
        when {
               expression { GIT_BRANCH == 'origin/main' }
             }
       agent any
       environment {
           HEROKU_API_KEY = credentials('heroku_api_key')
       }
       steps {
           script {
             sh'''
               heroku container:login
               heroku create $STAGING || echo "project already exists"
               heroku container:push -a $STAGING web
               heroku container:release -a $STAGING web
             '''
           }
         }
      }
      stage('Push image into production and deploy it') {
        when {
               expression { GIT_BRANCH == 'origin/main' }
             }
       agent any
       environment {
           HEROKU_API_KEY = credentials('heroku_api_key')
       }
       steps {
           script {
             sh'''
               heroku container:login
               heroku create $PRODUCTION || echo "project already exists"
               heroku container:push -a $PRODUCTION web
               heroku container:release -a $PRODUCTION web
             '''
           }
         }
      }
   }
}
```



This Jenkins pipeline is configured to build, test and deploy your Angular application to both staging and production environments on the Heroku platform. It is a complete setup to automate the deployment process



   ![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20163837.png)

   ![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20163741.png)