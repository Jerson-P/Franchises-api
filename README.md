# API de Franquicias

Este proyecto proporciona una API para manejar una lista de franquicias, sucursales y productos en Spring Boot. Permite realizar operaciones como agregar, eliminar y modificar franquicias, sucursales y productos, así como consultar el producto con mayor stock.

## Requisitos

- Java 17
- Docker
- Gradle
- Sistema de persistencia (MongoDB)

## Endpoints

### Franquicias

- **POST** `/franchises`: Agrega una nueva franquicia.
- **PUT** `/franchises/{franchiseId}/name`: Actualiza el nombre de una franquicia.

### Sucursales

- **POST** `/franchises/{franchiseId}/branches`: Agrega una nueva sucursal a una franquicia.
- **PUT** `/franchises/{franchiseId}/branches/{branchId}/name`: Actualiza el nombre de una sucursal.

### Productos

- **POST** `/franchises/{franchiseId}/branches/{branchId}/products`: Agrega un nuevo producto a una sucursal.
- **PUT** `/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock`: Actualiza el stock de un producto.
- **PUT** `/franchises/{franchiseId}/branches/{branchId}/products/{productId}/name`: Actualiza el nombre de un producto.
- **GET** `/franchises/{franchiseId}/branches/{branchName}/products/max-stock`: Devuelve el producto con mayor stock en una sucursal.
- **DELETE** `/franchises/{franchiseId}/branches/{branchName}/products/{productName}`: Elimina un producto de una sucursal.

## Instrucciones para correr el proyecto

### Ejecutar en local

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Jerson-P/Franchises-api.git
   cd Franchises-api

   
2. Compila el proyecto desde la consola usando Gradle:
   `./gradlew clean build`
   
3. Ejecuta la aplicación:
	`./gradlew bootRun`
	
### Ejecutar con DOCKER

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Jerson-P/Franchises-api.git
   cd Franchises-api

   
2. Compila el proyecto desde la consola usando Gradle:
   `./gradlew clean build`
   
3. Desde la consola construye la imagen Docker y levanta los contenedores con Docker Compose:
	`docker-compose up --build`

## En los dos casos la aplicación estará disponible en http://localhost:8080
## Para documentación de consumo de los servicios revisa el archivo API_Documentation.md



