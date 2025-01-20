# Challenge Foro Hub Alura

Este proyecto es una aplicación de foro simple construida con Spring Boot. Permite a los usuarios registrarse, iniciar sesión y crear tópicos. La aplicación utiliza JWT para autenticación y autorización.

## Prerrequisitos

- Java 17
- Maven
- MySQL

## Comenzando

### Clonar el Repositorio

```sh
git clone https://github.com/saturnette/Alura-Forum.git
cd foro
```

### Configurar la Base de Datos

Crea una base de datos MySQL y actualiza el archivo `application.properties` con tus credenciales de base de datos.

```properties
# filepath: src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/tu_nombre_de_base_de_datos
spring.datasource.username=tu_usuario_de_base_de_datos
spring.datasource.password=tu_contraseña_de_base_de_datos
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=tu_token_secreto
jwt.expiration=36000000 
```

### Construir y Ejecutar la Aplicación

```sh
./mvnw spring-boot:run
```

## Registro y Autenticación de Usuarios

### Registrar un Usuario

Para registrar un nuevo usuario, envía una solicitud POST a `/auth/register` con el siguiente cuerpo JSON:

```json
{
    "username": "testuser",
    "password": "testpassword"
}
```

### Iniciar Sesión

Para iniciar sesión, envía una solicitud POST a `/auth/login` con el siguiente cuerpo JSON:

```json
{
    "username": "testuser",
    "password": "testpassword"
}
```

Si el inicio de sesión es exitoso, recibirás un token JWT en la respuesta.

## Gestión de Tópicos

### Crear un Tópico

Para crear un nuevo tópico, envía una solicitud POST a `/topics` con el siguiente cuerpo JSON. Incluye el token JWT en el encabezado `Authorization` como un token Bearer.

```json
{
    "title": "Nuevo Tópico",
    "message": "Este es el mensaje del nuevo tópico",
    "creationDate": "2025-01-19T17:00:00",
    "status": "abierto",
    "author": "Autor del Tópico",
    "course": "Curso del Tópico"
}
```

### Estructura del Tópico

Un tópico tiene la siguiente estructura:

- `title`: El título del tópico.
- `message`: El contenido del mensaje del tópico.
- `creationDate`: La fecha de creación del tópico.
- `status`: El estado del tópico (por ejemplo, "abierto").
- `author`: El autor del tópico.
- `course`: El curso relacionado con el tópico.

### Estructura del Usuario

Un usuario tiene la siguiente estructura:

- `id`: El identificador único del usuario.
- `username`: El nombre de usuario del usuario.
- `password`: La contraseña del usuario (almacenada como un valor hash).
- `role`: El rol del usuario (por ejemplo, "USER").

## Endpoints

### Endpoints de Autenticación

- **POST /auth/register**: Registrar un nuevo usuario.
  - Cuerpo de la Solicitud:
    ```json
    {
        "username": "testuser",
        "password": "testpassword"
    }
    ```
  - Respuesta:
    - `201 Created`: Usuario registrado con éxito.
    - `409 Conflict`: El nombre de usuario ya existe.

- **POST /auth/login**: Iniciar sesión de un usuario.
  - Cuerpo de la Solicitud:
    ```json
    {
        "username": "testuser",
        "password": "testpassword"
    }
    ```
  - Respuesta:
    - `200 OK`: Token JWT.
    - `401 Unauthorized`: Credenciales inválidas.

### Endpoints de Tópicos

- **GET /topics**: Obtener todos los tópicos.
  - Respuesta:
    - `200 OK`: Lista de tópicos.

- **GET /topics/{id}**: Obtener un tópico por ID.
  - Respuesta:
    - `200 OK`: Detalles del tópico.
    - `404 Not Found`: Tópico no encontrado.

- **POST /topics**: Crear un nuevo tópico.
  - Encabezado de la Solicitud:
    - `Authorization: Bearer <JWT_TOKEN>`
  - Cuerpo de la Solicitud:
    ```json
    {
        "title": "Nuevo Tópico",
        "message": "Este es el mensaje del nuevo tópico",
        "creationDate": "2025-01-19T17:00:00",
        "status": "abierto",
        "author": "Autor del Tópico",
        "course": "Curso del Tópico"
    }
    ```
  - Respuesta:
    - `201 Created`: Tópico creado con éxito.
    - `401 Unauthorized`: Token JWT inválido o ausente.

- **PUT /topics/{id}**: Actualizar un tópico existente.
  - Encabezado de la Solicitud:
    - `Authorization: Bearer <JWT_TOKEN>`
  - Cuerpo de la Solicitud:
    ```json
    {
        "title": "Título Actualizado",
        "message": "Mensaje Actualizado",
        "creationDate": "2025-01-19T17:00:00",
        "status": "cerrado",
        "author": "Autor Actualizado",
        "course": "Curso Actualizado"
    }
    ```
  - Respuesta:
    - `200 OK`: Tópico actualizado con éxito.
    - `404 Not Found`: Tópico no encontrado.
    - `401 Unauthorized`: Token JWT inválido o ausente.

- **DELETE /topics/{id}**: Eliminar un tópico.
  - Encabezado de la Solicitud:
    - `Authorization: Bearer <JWT_TOKEN>`
  - Respuesta:
    - `204 No Content`: Tópico eliminado con éxito.
    - `404 Not Found`: Tópico no encontrado.
    - `401 Unauthorized`: Token JWT inválido o ausente.

## Paquetes Utilizados

- **com.alura.forum.controller**: Contiene los controladores REST para manejar las solicitudes HTTP.
  - `AuthController`: Maneja el registro y la autenticación de usuarios.
  - `TopicController`: Maneja la creación, actualización, eliminación y obtención de tópicos.

- **com.alura.forum.model**: Contiene las clases de modelo que representan las entidades de la base de datos.
  - `User`: Representa un usuario en el sistema.
  - `Topic`: Representa un tópico en el foro.

- **com.alura.forum.repository**: Contiene las interfaces de repositorio para interactuar con la base de datos.
  - `UserRepository`: Interfaz para operaciones CRUD en la entidad `User`.
  - `TopicRepository`: Interfaz para operaciones CRUD en la entidad `Topic`.

- **com.alura.forum.service**: Contiene las clases de servicio que contienen la lógica de negocio.
  - `JwtService`: Maneja la generación y validación de tokens JWT.
  - `TopicService`: Maneja la lógica de negocio relacionada con los tópicos.

- **com.alura.forum.config**: Contiene las clases de configuración de seguridad.
  - `JwtAuthFilter`: Filtro de autenticación JWT para proteger las rutas de la API.
