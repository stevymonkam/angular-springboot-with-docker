# angular-springboot-with-docker

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

   ## Préparer votre application Angular 
     
     Assurez-vous que votre application Angular fonctionne correctement localement.
     Générez une version de production de votre application à l'aide de la commande Angular CLI : 
      ```
      ng build --prod.
      ```

   ## Créer un fichier Dockerfile :

    Créez un fichier nommé Dockerfile à la racine de votre projet Angular.
    Ce fichier contiendra les instructions pour Docker sur la manière de construire votre conteneur

  ![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20144436.png)

   ## Construire l'image Docker 

   Exécutez la commande suivante pour construire votre image Docker :
   
      ```
      docker build -t contratti-image .
      ```

  ## Exécuter le conteneur Docker :

      ```
      docker run --network springmysql-net --name front-container -p 86:80 contratti-image:latest
      ```
  ![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20145559.png)

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
docker push stevymonkam/contratti-image:1.0
```
![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20160111.png)


## ##Docker-compose :

utiliser ce docker-compose pour obtenir le meme resultat:

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
    detach: true  # Déplacer cette ligne à l'intérieur de la définition du service

volumes:
  front-data:
    driver: local


```

## Mettre en relation le front-end et le back-end :

modifier le fichier config de son app angular et faire pointer l'adress ip 
de l'host sur la porte 8080 du container back-end comme ci-desous :

  ![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20144755.png)


## Consomation de l'app :

Apres deployement du front end et back end notre application è enfin prete et peut etre consomer via url : http://192.168.56.14:86

  
  ![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20154341.png)

  ![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20144934.png)


