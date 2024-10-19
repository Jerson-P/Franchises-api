# Gestión de Reservas - Sistema de Vuelos

Este proyecto es un sistema de gestión de reservas de vuelos, desarrollado en Java utilizando Spring Boot. El proyecto incluye funcionalidades para la búsqueda de vuelos, reservas y otras operaciones relacionadas con la gestión de vuelos.

## Requisitos

### Software Necesario
- **JDK 17**
- **Maven 3.6.3** o superior.
- **Base de datos SQL Server** (o cualquier base de datos compatible con JPA).

### Dependencias del Proyecto
Las dependencias principales que el proyecto utiliza están definidas en el archivo `pom.xml`. Algunas importantes incluyen:
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- SQL Server Driver para la conexión con bases de datos SQL Server
- Swagger para la documentación de API

## Configuración del Proyecto

### Clonar el Proyecto
1. Puedes clonar el repositorio desde el control de versiones (Git):

```bash
git clone https://github.com/Jerson-P/prueba-bigview-api.git
cd prueba-bigview-api
   
2. Compila el proyecto desde la consola usando Gradle:
   `./gradlew clean build`
   
3. Desde la consola construye la imagen Docker y levanta los contenedores con Docker Compose:
	`docker-compose up --build`

## En los dos casos la aplicación estará disponible en http://localhost:8080
## Para documentación de consumo de los servicios revisa el archivo API_Documentation.md



