# angulaire-springboot-avec-docker

### Dans ce projet je dois résoudre les besoins suivant

1) déployer une application angulaire avec docker et utiliser docker-compose pour coder mon infrastructure de déploiement
2) déployer une application Java Springboot avec Docker, installer et configurer la base de données MySQL et la faire communiquer avec le back-end, utiliser Docker-compose pour codifier mon infrastructure de déploiement
3) automatiser le déploiement de l'application Angular avec Jenkins, écrire un pipeline complet et le déployer dans le fournisseur de cloud heroku


Lien frontal : https://github.com/stevymonkam/contratti_deploy

Lien back-end : https://github.com/stevymonkam/backendTest2new/tree/main

------------

Auteur : MONKAM NAKTAKWEN STEVY

LinkedIn : https://www.linkedin.com/in/stevy-monkam-naktakwen-a84299181/

# Dockeriser une application Spring Boot + MySQL

## Aperçu

Ce guide explique deux méthodes pour dockeriser une application Spring Boot avec une base de données MySQL : utiliser un réseau Docker et utiliser Docker-compose.

###Description de l'application

L'application facilite les opérations CRUD pour un système de gestion des employés, y compris des fonctionnalités telles que l'ajout, l'obtention et la mise à jour d'employés.

#### Structure du projet

![architecture-suggérée](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20170819.png)

#### Configuration des applications

Pour configurer la base de données, reportez-vous au fichier `application.properties`.

![architecture-suggérée](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20170538.png)

#### Construire le pot d'application

Pour créer le fichier jar de l'application Spring Boot :

1. Faites un clic droit sur `pom.xml` → Exécuter en tant que → build Maven.
2. Le fichier jar généré (`backend-0.0.1-SNAPSHOT.jar`) sera dans le dossier `target`.

![architecture-suggérée](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20171127.png)


## Processus de dockérisation

### 1. Utiliser un réseau Docker

1. Extrayez l'image MySQL de Docker Hub :
```
docker pull mysql: 5.7
```

2. Créez une image Docker pour l'application Spring Boot :
```
docker build -t backend1 .
```
![architecture-suggérée](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20175618.png)

3. Créez un réseau Docker :
```
réseau Docker créer springmysql-net
```
4. Exécutez le conteneur MySQL sur le réseau :
```
docker run --name mysqldb --network springmysql-net -e MYSQL_ROOT_PASSWORD=manounou -e MYSQL_DATABASE=ecomerce -e MYSQL_USER=stevy -e MYSQL_PASSWORD=manounou -d mysql:5.7
```
5. Vérifiez les journaux du conteneur MySQL :
```
Docker enregistre mysqldb
```
![architecture-suggérée](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20202002.png)

6. Vérifiez si la base de données `ecommerce` est créée :
```
docker exec -it mysqldb mysql -uroot -proot -e "afficher les bases de données ;"
```
7. Exécutez le conteneur Spring Boot sur le même réseau :
```
docker run --name spring-boot-app --network springmysql-net -p 8080:8080 -d backend1:latest
```
![architecture-suggérée](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20192546.png)


8. Vérifiez que les conteneurs fonctionnent correctement :
```
docker ps
```
![architecture-suggérée](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20180303.png)

9. Vérifiez les journaux du conteneur Spring Boot :
```
Docker journaux spring-boot-app
```
![architecture-suggérée](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20202425.png)

# insérer le hub Docker

```
connexion Docker
```
### changer le nom de l'image
```
balise docker id_image nom d'utilisateur/imageName:tag
```
pousser l'image
```
docker push stevymonkam/backend1:1.0
```
![architecture-suggérée](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20200456.png)

# Utilisation de docker-compose
Sans passer par ces nombreuses étapes, nous pouvons faire la même chose avec une seule commande docker-compose.

Pour ce faire, nous devons créer un fichier docker-compose.yml qui comprend les éléments suivants.

```yaml
     version : "3"
     prestations de service:
       serveur:
         image : stevymonkam/backend1:1.0
         ports :
           - "8080:8080"
         environnement:
           - spring.datasource.url=jdbc:mysql://mysqldb:3306/ecomerce?useSSL=false
         réseaux :
           -springmysql-net
         dépend de:
           - mysqldb
    
       mysqldb :
         image : mysql : 5.7
         réseaux :
           -springmysql-net
         environnement:
           - MYSQL_ROOT_PASSWORD=manounou
           - MYSQL_DATABASE=ecommerce
           - MYSQL_USER=stevy
           - MYSQL_PASSWORD=manounou
         tomes :
           - ./mysql-data:/var/lib/mysql # Montage du volume local dans le conteneur MySQL
    
     réseaux :
       springmysql-net :

```

# créer un répertoire

```
mkdir données mysql
```

Ensuite, ouvrez le terminal de commande dans le dossier du projet et exécutez la commande suivante.
```
docker-composer
```
![architecture-suggérée](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20200200.png)

![architecture-suggérée](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20192546.png)



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

 ```yaml
   FROM node:20.11.0-alpine as build
   WORKDIR /app

   RUN npm install -g @angular/cli

   COPY ./package.json .
   RUN npm install --force
   COPY . .
   RUN ng build

   FROM nginx as runtime
   COPY --from=build /app/dist/test1 /usr/share/nginx/html
 ```

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




# CI CD app angular avec jenkins :


automatison notre deployement avec jenkins 


```yaml
pipeline {
     environment {
       IMAGE_NAME = "contratti"
       IMAGE_TAG = "latest"
       STAGING = "contratti-staging"
       PRODUCTION = "contratti-production"
     }
     agent none
     stages {
         stage('Build image') {
             agent any
             steps {
                script {
                  sh 'docker build -t contratti/$IMAGE_NAME:$IMAGE_TAG .'
                }
             }
        }
        stage('Run container based on builded image') {
            agent any
            steps {
               script {
                 sh '''
                    docker run --name $IMAGE_NAME -d -p 86:80 -e PORT=80 contratti/$IMAGE_NAME:$IMAGE_TAG
                    sleep 5
                 '''
               }
            }
       }
       stage('Test image') {
           agent any
           steps {
              script {
                sh '''
                   curl http://localhost | grep -q "contratti"
                '''
              }
           }
      }
      stage('Clean Container') {
          agent any
          steps {
             script {
               sh '''
                 docker stop $IMAGE_NAME
                 docker rm $IMAGE_NAME
               '''
             }
          }
     }
     stage('Push image in staging and deploy it') {
       when {
              expression { GIT_BRANCH == 'origin/main' }
            }
      agent any
      environment {
          HEROKU_API_KEY = credentials('heroku_api_key')
      }  
      steps {
          script {
            sh '''
              heroku container:login
              heroku create $STAGING || echo "project already exist"
              heroku container:push -a $STAGING web
              heroku container:release -a $STAGING web
            '''
          }
        }
     }
     stage('Push image in production and deploy it') {
       when {
              expression { GIT_BRANCH == 'origin/main' }
            }
      agent any
      environment {
          HEROKU_API_KEY = credentials('heroku_api_key')
      }  
      steps {
          script {
            sh '''
              heroku container:login
              heroku create $PRODUCTION || echo "project already exist"
              heroku container:push -a $PRODUCTION web
              heroku container:release -a $PRODUCTION web
            '''
          }
        }
     }
  }
}
```



Cette pipeline Jenkins est configuré pour construire, tester et déployer votre application Angular à la fois sur des environnements de staging et de production sur la plateforme Heroku. C'est une configuration complète pour automatiser le processus de déploiemen



  ![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20163837.png)

  ![suggested-architecture](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20163741.png)
