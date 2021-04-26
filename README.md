# OPERACION_FUEGO_DE_QUASAR

API REST de inteligencia para poder dectectar la posicion de una señal y su respectivo mensaje triangulandola.



## Build:

### Requisitos:
- Java 11 o superior
- Maven 3.6.0 o superior



## Build imagen Docker:

### Requisitos:
- Docker
- Maven 3.6.0 o superior

El proyecto utiliza [Jib](https://github.com/GoogleContainerTools/jib) (específicamente [jib-maven-plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin)) para realizar el build de la imagen Docker.



La imagen se puede buildear directamente al daemon Docker local con el comando:

```
mvn clean verify jib:dockerBuild
```



Esto generara la imagen con los tags:
- alianza-rebelde/operacion-fuego-quasar-api:latest
- alianza-rebelde/operacion-fuego-quasar-api:{project.version}



## Ejecución

### Requisitos
- Java 11 o superior



### Configuración

En el archivo **"application.yml"** se encuentra la configuración base de la aplicación. Las propiedades más importantes son:

```
spring:
  application.name: fuego-quasar-api_dev-h2
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: test
    password: test

  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate.ddl-auto: create


  h2.console.enabled: true
```


##### Ejemplo de configuración mediante archivo YAML:

```yml
server:
  port: 8080
  error:
    include-message: always
    include-binding-errors: always

logging.file.name: logs/@project.artifactId@.log

management:
  endpoints.web:
    exposure.include: health, info, logfile, metrics, prometheus
    cors:
      allowed-origins: "*"
      allowed-methods: GET
  endpoint.health.show-details: always


spring:
  application.name: fuego-quasar-api_dev-h2
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: test
    password: test

  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate.ddl-auto: create


  h2.console.enabled: true

```



### Ejecución desde el IDE

Ejecutar la aplicación como una aplicación SpringBoot, desde la clase principal *OperacionFuegoQuasar*.

### Ejecución como JAR
* Pre-requisito: Realizar el build del .jar

Ejemplo utilizando un archivo de propiedades llamado application.properties

```
java -jar operacion-fuego-quasar-api:{project.version}.jar --spring.config.location=application.properties
```


### Ejecución Dockerizada

Dado que la imagen solo contiene la configuración base, es necesario pasar el resto de la configuración para poder ejecutar la aplicación. Esto se puede hacer mediante variables de entorno o pasando el archivo profile al container.



#### Con docker-compose

Para definir las variables de entorno lo mas cómodo es utilizar 'docker-compose'.

En la carpeta */docker-compose* se encuentran los archivos para poder ejecutar la aplicación con distintas configuraciones.

```
version: '2'

services:

  operacion-fuego-quasar-api:
    image:  alianza-rebelde/operacion-fuego-quasar-api:1.0.0-SNAPSHOT
    restart: always
    ports:
      - 8010:8080
    environment:
      spring.application.name: operacion-fuego-quasar-api
      spring.datasource.url: jdbc:h2:mem:testdb
      spring.datasource.driverClassName: org.h2.Driver
      spring.datasource.username: 'test'
      spring.datasource.password: 'test'
      spring.h2.console.enabled: 'true'
      spring.datasource.hikari.connectionTimeout: 30000
      spring.datasource.hikari.idleTimeout: 600000
      spring.datasource.hikari.maxLifetime: 1800000
      spring.jpa.database-platform: org.hibernate.dialect.H2Dialect
      spring.jpa.hibernate.ddl-auto: create

```



Para ejecutar, dirigirse el directorio donde se encuentra el archivo docker-compose y ejecutar el comando correspondiente:



**Crear los containers e iniciarlos**
```
docker-compose up
```

**Detener los containers (sin destruirlos**)

```
docker-compose stop
```

**Iniciar los containers nuevamente**
```
docker-compose start
```

**Detener los containers y destruirlos**
```
docker-compose down
```



#### Con docker run:


Ejemplo ejecutando una imagen que existe en docker registry

```
docker run -d  alianza-rebelde/operacion-fuego-quasar-api:{project.version} -p 8080:8080
```



## Configuración Eclipse

##### Configurar Lombok plugin
- Seguir los pasos descriptos en [https://projectlombok.org/setup/eclipse](https://projectlombok.org/setup/eclipse)
- Luego importar el proyecto maven en Eclipse y realizar el build.



## Configuración Intellij

##### Configurar Lombok plugin
Seguir los pasos descriptos en [https://projectlombok.org/setup/intellij](https://projectlombok.org/setup/intellij)
