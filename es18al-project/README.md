# Adventure Builder [![Build Status](https://codecov.io/gh/tecnico-softeng/es18al_29-project/branch/develop/graph/badge.svg?token=OzQ7MyCEmo)](https://codecov.io/gh/tecnico-softeng/es18al_29-project))

To run tests execute: mvn clean install

To see the coverage reports, go to <module name>/target/site/jacoco/index.html.


|   Number   |          Name           |            Email        |   GitHub Username  | Group |
| ---------- | ----------------------- | ----------------------- | -------------------| ----- |
|  82343     | Pedro Cunha             |    pcunha288@gmailcom   |    Ph0t0nMan       |   1   |
|  81633     | João Tiago Henriques    |tiago-toscano@hotmail.com|   BeatrizToscano   |   1   |
|  83434     | Beatriz Carrilho Toscano|beatrizctoscano@gmail.com|    Toscan0         |   1   |
|  83416     | Afonso Mercier          |colector-2@hotmail.com   |   MiniMercias      |   2   |
|  83536     | Patrícia Silva          |ptrc.silva1@live.com.pt  |     PatSilvarte    |   2   |
|  78614     | José Cordeiro           |josemc.95@hotmail.com    | josedassiscordeiro |   2   |

- **Group 1:Broker, Bank, Hotel
- **Group 2:Car, Tax, activity

### Infrastructure

This project includes the persistent layer, as offered by the FénixFramework.
This part of the project requires to create databases in mysql as defined in `resources/fenix-framework.properties` of each module.

See the lab about the FénixFramework for further details.

#### Docker (Alternative to installing Mysql in your machine)

To use a containerized version of mysql, follow these stesp:

```
docker-compose -f local.dev.yml up -d
docker exec -it mysql sh
```

Once logged into the container, enter the mysql interactive console

```
mysql --password
```

And create the 7 databases for the project as specified in
the `resources/fenix-framework.properties`.
