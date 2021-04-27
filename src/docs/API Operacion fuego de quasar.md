#  API Operacion Fuego de quasar

La presente aplicacion tiene como objetivo poder decifrar un mensje y el lugarde origen, para ello usara una red de satellite para poder
determinar su origen.


## Respuestas de error:

Todas las respuestas erróneas retornan un código HTTP de la serie 4xx o 5xx con un mensaje de la forma:

```json
{
  "timestamp": "2021-01-21T00:29:40.090+00:00",
  "status": 404,
  "error": "not_found",
  "message": "satellite xxx not found",
  "path": "/rebel/api/satellite"
}
```

## Especificación de la API

### CRUD de Satellites

Se requiere almenos 3 satellite para poder triangula la posicion y mesaje que se recibe de una señal desconocida.

#### POST /rebel/api/satellite

- Registra un satellite recibiendo en el body nombre que se asignara y sus cordenadas.

#### Request:


- Body
```
Content-Type: application/json
```

```json
{
"name":"kenobi",
"position":{
"x":100,
"y":-200
}
}
```



**Parámetros**:

| Nombre     | Requerido | Tipo             	 | Descripción                      |
| ---------- | --------- | ----------------------| -------------------------------- |
| name   	 | si        | string            	 | Nombre del satelite 				|
| position   | si        | object 				 | Cordenadas del satelite 			|



#### Responses:

**Exitosa**

```
201 - Created
{
    "id": 1,
    "name": "kenobi",
    "x": -500.0,
    "y": -200.0
}
```


**Errores**

En caso de respuesta errónea

| HTTP Status | Error code                                | Description                                                  |
| ----------- | ----------------------------------------- | ------------------------------------------------------------ |
| 400         | Bad Request                      		  | Parámetros incorrectos, request mal formado                  |
| 409         | Conflict							      | No puede registrar el satellite por que ya esta registrado   |                  

#### PUT /rebel/api/satellite

- actualiza un satellite recibiendo en el body nombre del satelite que se actualizara y sus nuevas cordenadas.

#### Request:
- Body
```
Content-Type: application/json
```

```json
{
"name":"kenobi",
"position":{
"x":100,
"y":-200
}
}
```



**Parámetros**:

| Nombre     | Requerido | Tipo             	 | Descripción                      |
| ---------- | --------- | ----------------------| -------------------------------- |
| name   	 | si        | string            	 | Nombre del satelite 				|
| position   | si        | object 				 | Cordenadas del satelite 			|



#### Responses:

**Exitosa**

```
200 - Ok
{
    "id": 1,
    "name": "kenobi",
    "x": -500.0,
    "y": -200.0
}
```


**Errores**

En caso de respuesta errónea

| HTTP Status | Error code                                | Description                                                  |
| ----------- | ----------------------------------------- | ------------------------------------------------------------ |
| 400         | Bad Request                      		  | Parámetros incorrectos, request mal formado                  |
| 404         | Not Found							      | No encuentra el satellite que se desea actualizar			 |      

#### GET /rebel/api/satellite

- Se busca todo los satelites Existentes.



#### Responses:

**Exitosa**

```
200 - Ok
```
```
[
    {
        "id": 1,
        "name": "kenobi",
        "x": -500.0,
        "y": -200.0
    },
    {
        "id": 2,
        "name": "skywalker",
        "x": 100.0,
        "y": -100.0
    }
]
```

#### GET /rebel/api/satellite/{sateliteName}

- Se buscara el satelite indicado en el parametro.

**Parámetros Path**:

| Nombre               | Tipo                   | Descripción                                                  |
| -------------------- | ---------------------- | ------------------------------------------------------------ |
| sateliteName          | string            	| Nombre de satelite que se buscara 					       |

#### Responses:

**Exitosa**

```
200 - Ok
```
```

    {
        "id": 1,
        "name": "kenobi",
        "x": -500.0,
        "y": -200.0
    }

```

**Errores**

En caso de respuesta errónea

| HTTP Status | Error code                                | Description                                                  |
| ----------- | ----------------------------------------- | ------------------------------------------------------------ |
| 404         | Not Found							      | No encuentra el satellite indicado							 |      

#### DELETE /rebel/api/satellite/{sateliteName}

- Se Eliminara el satelite indicado en el parametro.

**Parámetros Path**:

| Nombre               | Tipo                   | Descripción                                                  |
| -------------------- | ---------------------- | ------------------------------------------------------------ |
| sateliteName          | string            	| Nombre de satelite que se eliminara 					       |

#### Responses:

**Exitosa**

```
204 - NO_CONTENT
```


**Errores**

En caso de respuesta errónea

| HTTP Status | Error code                                | Description                                                  |
| ----------- | ----------------------------------------- | ------------------------------------------------------------ |
| 404         | Not Found							      | No encuentra el satellite indicado							 |      


### Procesos de Inteligencia 

#### POST /rebel/api/topsecret

Se enviara un listados con los satelites que recibieron una señal para poder determinar su ubicacion y el mensaje que envio.

#### Request:

```
Content-Type: application/json
```

```json
{
   "satellites":[
      {
         "name":"kenobi",
         "distance":100,
         "message":[
            "este",
            "",
            "",
            "mensaje",
            ""
         ]
      },
      {
         "name":"skywalker",
         "distance":115.5,
         "message":[
            "",
            "es",
            "",
            "",
            "secreto"
         ]
      },
      {
         "name":"sato",
         "distance":142.7,
         "message":[
            "este",
            "",
            "un",
            "",
            ""
         ]
      }
   ]
}
```

**Parámetros Body**:

| Nombre               | Requerido | Tipo                   | Descripción                                                  |
| -------------------- | --------- | ---------------------- | ------------------------------------------------------------ |
| name        		   | si        | string (6 caracteres)  | Nombre del satelite                             			   |
| distance        	   | si        | double 				| distancia del satelite con la señal                            			   |
| message        	   | si        | string[] 				| mensaje que recibio el satelite                            			   |



#### Responses:

**Exitosa**

```
200 - OK
```
```
{
    "position": {
        "x": -58.32,
        "y": -69.55
    },
    "message": "este es un mensaje secreto"
}
```

**Errores**

En caso de respuesta errónea

| HTTP Status | Error code                               | Description                                       		|
| ----------- | ---------------------------------------- | -------------------------------------------------------- |
| 400         | Bad Request		                         | Parámetros incorrectos, request mal formado              |
| 404         | Not Found		           				 | No se puedo encontrar alguno de los satelites enviados   |

#### POST /rebel/api/topsecret_split/{sateliteName}

se envia la distancia y el mensaje que recibio uno de los satelites

**Pre-Requisito**
	- los satelites deben estar registrados

#### Request:

```
Content-Type: application/json
```

```json
{

         "distance":100,
         "message":[
            "este",
            "",
            "",
            "mensaje",
            ""
         ]
}
```

**Parámetros Path**:

| Nombre               | Tipo                   | Descripción                                                  |
| -------------------- | ---------------------- | ------------------------------------------------------------ |
| sateliteName          | string            	| Nombre de satelite					 				       |


**Parámetros Body**:

| Nombre               | Requerido | Tipo                   | Descripción                                                  |
| -------------------- | --------- | ---------------------- | ------------------------------------------------------------ |
| distance        	   | si        | double 				| distancia del satelite con la señal                          |
| message        	   | si        | string[] 				| mensaje que recibio el satelite                              |



#### Responses:

**Exitosa**

```
201 - Create
```

**Errores**

En caso de respuesta errónea

| HTTP Status | Error code                               | Description                                 			  |
| ----------- | ---------------------------------------- | ------------------------------------------------------ |
| 400         | Bad Request		                         | Parámetros incorrectos, request mal formado     		  |
| 404         | Not Found		           				 | No se puedo encontrar el satelites enviado             |


#### GET /rebel/api/topsecret_split

Devolvera la pocision y el mensaje triagunlando almenos 3 señales anteriormente registrada.

#### Responses:

**Exitosa**

```
200 - OK
```
```
{
    "position": {
        "x": -58.32,
        "y": -69.55
    },
    "message": "este es un mensaje secreto"
}
```

**Errores**

En caso de respuesta errónea

| HTTP Status | Error code                               | Description                                       		|
| ----------- | ---------------------------------------- | -------------------------------------------------------- |
| 409         | Conflict		           				 | Debe existir almenos 3 señales registradas de satelites diferentes      |
| 404         | Not Found		           				 | No se puedo encontrar alguno de los satelites registrado con anterioridad   |
