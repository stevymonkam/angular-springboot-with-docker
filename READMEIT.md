#angular-springboot-con-docker

### In questo progetto devo risolvere le seguenti esigenze

1) distribuisci un'applicazione Angular con docker e utilizza docker-compose per codificare la mia infrastruttura di distribuzione
2) distribuire un'applicazione Java Springboot con Docker, installare e configurare il database MySQL e farlo comunicare con il back-end, utilizzare Docker-compose per codificare la mia infrastruttura di distribuzione
3) automatizzare la distribuzione dell'applicazione Angular con Jenkins, scrivere una pipeline completa e distribuirla al provider cloud heroku


Collegamento front-end: https://github.com/stevymonkam/contratti_deploy

Collegamento al backend: https://github.com/stevymonkam/backendTest2new/tree/main

------------

Autore: MONKAM NAKTAKWEN STEVY

LinkedIn: https://www.linkedin.com/in/stevy-monkam-naktakwen-a84299181/

# Dockerizzare un'applicazione Spring Boot + MySQL

## Anteprima

Questa guida spiega due metodi per dockerizzare un'applicazione Spring Boot con un database MySQL: utilizzare una rete Docker e utilizzare Docker-compose.

###Descrizione dell'applicazione

L'applicazione facilita le operazioni CRUD per un sistema di gestione dei dipendenti, comprese funzionalità come l'aggiunta, l'acquisizione e l'aggiornamento dei dipendenti.

#### Struttura del progetto

![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20170819.png)

#### Configurazione dell'applicazione

Per configurare il database, fare riferimento al file "application.properties".

![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20170538.png)

#### Costruisci il jar dell'app

Per creare il file jar dell'applicazione Spring Boot:

1. Fare clic con il tasto destro su `pom.xml` → Esegui come → crea Maven.
2. Il file jar generato (`backend-0.0.1-SNAPSHOT.jar`) si troverà nella cartella "target".

![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20171127.png)


## Processo di dockerizzazione

### 1. Utilizzare una rete Docker

1. Estrai l'immagine MySQL da Docker Hub:
```
finestra mobile pull mysql:5.7
```

2. Crea un'immagine Docker per l'applicazione Spring Boot:
```
docker build -t backend1 .
```
![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20175618.png)

3. Crea una rete Docker:
```
La rete Docker crea springmysql-net
```
4. Esegui il contenitore MySQL sulla rete:
```
docker run --name mysqldb --network springmysql-net -e MYSQL_ROOT_PASSWORD=manounou -e MYSQL_DATABASE=ecomerce -e MYSQL_USER=stevy -e MYSQL_PASSWORD=manounou -d mysql:5.7
```
5. Controlla i log del contenitore MySQL:
```
Docker salva mysqldb
```
![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20202002.png)

6. Controlla se il database "ecommerce" è stato creato:
```
docker exec -it mysqldb mysql -uroot -proot -e "mostra database;"
```
7. Eseguire il contenitore Spring Boot sulla stessa rete:
```
docker esegui --name spring-boot-app --network springmysql-net -p 8080:8080 -d backend1:latest
```
![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20192546.png)


8. Verificare che i contenitori funzionino correttamente:
```
finestra mobile ps
```
![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20180303.png)

9. Controllare i log del contenitore Spring Boot:
```
Docker registra l'app spring-boot
```
![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20202425.png)

# inserisci l'hub Docker

```
Connessione Docker
```
### cambia il nome dell'immagine
```
tag docker ID_immagine nomeutente/Nomeimmagine:tag
```
spingere l'immagine
```
docker push stevymonkam/backend1:1.0
```

![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20200456.png)

# Utilizzo di docker-compose
Senza eseguire tutti questi passaggi, possiamo fare la stessa cosa con un singolo comando docker-compose.

Per fare ciò, dobbiamo creare un file docker-compose.yml che includa i seguenti elementi.

```yaml
      versione: "3"
      Servizi:
        cameriere:
          immagine: stevymonkam/backend1:1.0
          porti:
            - "8080:8080"
          ambiente:
            - spring.datasource.url=jdbc:mysql://mysqldb:3306/ecomerce?useSSL=false
          reti:
            -springmysql-net
          dipende da:
            -mysqldb
    
        mysqldb:
          immagine: mysql:5.7
          reti:
            -springmysql-net
          ambiente:
            - MYSQL_ROOT_PASSWORD=manounou
            - MYSQL_DATABASE=e-commerce
            -MYSQL_USER=stevy
            - MYSQL_PASSWORD=manounou
          volumi:
            - ./mysql-data:/var/lib/mysql # Monta il volume locale nel contenitore MySQL
    
      reti:
        springmysql-net:

```

# crea una directory

```
mkdir dati mysql
```

Successivamente, apri il terminale di comando nella cartella del progetto ed esegui il comando seguente.
```
docker-compose
```
![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20200200.png)

![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-06%20192546.png)



# Dockerizzazione dell'app Angular

    ## Prepara la tua applicazione Angular
    
      Assicurati che la tua applicazione Angular funzioni correttamente a livello locale.
      Crea una versione di produzione della tua applicazione utilizzando il comando Angular CLI:

       ```
       ng build --prod.
       ```

    ## Crea un Dockerfile:

     Crea un file denominato Dockerfile nella root del tuo progetto Angular.
     Questo file conterrà le istruzioni per Docker su come creare il tuo contenitore

  ```yaml
    FROM nodo:20.11.0-alpine come build
    DIR LAVORO /app

    ESEGUI npm install -g @angular/cli

    COPIA ./pacchetto.json .
    ESEGUI npm install --force
    COPIA . .
    ESEGUI ngbuild

    DA nginx come runtime
    COPY --from=build /app/dist/test1 /usr/share/nginx/html
  ```

    ## Costruisci l'immagine Docker

    Esegui il comando seguente per creare la tua immagine Docker:
   
       ```
       docker build -tcontratti-immagine .
       ```

   ## Esegui il contenitore Docker:

       ```
       docker run --network springmysql-net --name front-container -p 86:80contrati-image:latest
       ```
   ![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20145559.png)

# spingere nell'hub della finestra mobile

```
accesso alla finestra mobile
```
### cambia il nome dell'immagine
```
tag docker id_immagine nomeutente/nomeimmagine:tag
```
spingere l'immagine
```
docker push stevymonkam/contratti-image:1.0
```
![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20160111.png)


## ##Docker-composizione:

usa questo docker-compose per ottenere lo stesso risultato:

```yaml
    versione: "3"
Servizi:
   contenitore anteriore:
     immagine: stevymonkam/contratti-immagine:1.0
     porti:
       -"83:80"
     reti:
       - springmysql-net
     nome_contenitore: contenitore-frontale
     volumi:
       - front-data:/usr/share/nginx/html
     distacca: true # Sposta questa riga all'interno della definizione del servizio

volumi:
   dati frontali:
     autista: locale


```

## Collega il front-end e il back-end:

modifica il file di configurazione della tua app Angular e indica l'indirizzo IP
dell'host sulla porta 8080 del contenitore back-end come di seguito:

   ![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20144755.png)


## Consumo dell'app:

Dopo l'implementazione del front-end e del back-end, la nostra applicazione è finalmente pronta e può essere utilizzata tramite l'URL: http://192.168.56.14:86

  
   ![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20154341.png)

   ![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20144934.png)

# App angolare CD CI con jenkins:


automatizzando la nostra distribuzione con Jenkins


```yaml
tubatura {
      ambiente {
        IMAGE_NAME = "contratti"
        IMAGE_TAG = "più recente"
        STAGING = “contratti-staging”
        PRODUZIONE = “produzione-contratti”
      }
      agente nessuno
      tirocini {
          stage('Crea immagine') {
              agente qualsiasi
              passi {
                 copione {
                   sh 'docker build -t contratti/$IMAGE_NAME:$IMAGE_TAG .'
                 }
              }
         }
         stage('Esegui il contenitore in base all'immagine creata') {
             agente qualsiasi
             passi {
                copione {
                  sh'''
                     docker esegui --name $IMAGE_NAME -d -p 86:80 -e PORT=80 contractti/$IMAGE_NAME:$IMAGE_TAG
                     dormire 5
                  '''
                }
             }
        }
        stage('Immagine di prova') {
            agente qualsiasi
            passi {
               copione {
                 sh'''
                    arricciare http://localhost | grep -q "contratti"
                 '''
               }
            }
       }
       stage('Pulisci contenitore') {
           agente qualsiasi
           passi {
              copione {
                sh'''
                  fermata finestra mobile $IMAGE_NAME
                  finestra mobile rm $IMAGE_NAME
                '''
              }
           }
      }
      stage('Inserisci l'immagine nello staging e distribuiscila') {
        Quando {
               espressione { GIT_BRANCH == 'origine/principale' }
             }
       agente qualsiasi
       ambiente {
           HEROKU_API_KEY = credenziali('heroku_api_key')
       }
       passi {
           copione {
             sh'''
               contenitore heroku: login
               heroku crea $STAGING || echo "il progetto esiste già"
               contenitore heroku:push -a $STAGING web
               contenitore heroku:release -a $STAGING web
             '''
           }
         }
      }
      stage('Inserisci l'immagine in produzione e distribuiscila') {
        Quando {
               espressione { GIT_BRANCH == 'origine/principale' }
             }
       agente qualsiasi
       ambiente {
           HEROKU_API_KEY = credenziali('heroku_api_key')
       }
       passi {
           copione {
             sh'''
               contenitore heroku: login
               heroku crea $PRODUZIONE || echo "il progetto esiste già"
               contenitore heroku:push -a $PRODUZIONE web
               contenitore heroku:release -a $PRODUZIONE web
             '''
           }
         }
      }
   }
}
```



Questa pipeline Jenkins è configurata per creare, testare e distribuire la tua applicazione Angular sia in ambienti di staging che di produzione sulla piattaforma Heroku. Si tratta di una configurazione completa per automatizzare il processo di distribuzione



   ![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20163837.png)

   ![architettura-suggerita](https://github.com/stevymonkam/angular-springboot-with-docker/blob/main/img/Screenshot%202024-05-08%20163741.png)