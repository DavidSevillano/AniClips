# Proyecto Aniclips

<h1 align="center">
    <img src="https://github.com/DavidSevillano/AniClips/blob/main/logo.jpg" alt="logo_animalia"  width="300" height="300">
</h1>

## Descripción del Proyecto

AniClips es una plataforma para compartir y descubrir clips de anime. Permite a los usuarios subir clips, comentar y seguir a otros usuarios. La aplicación está diseñada para ser fácil de usar y accesible para todos los amantes del anime.

## Características

   - Registro y autenticación de usuarios.
   - Subida, búsqueda y gestión de clips de anime.
   - Comentarios, me gustas y valoraciones de clips.
   - Seguimiento de otros usuarios.
     
## Tecnologías Utilizadas

   - **Java**: Lenguaje de programación principal.
   - **Spring Boot**: Framework para construir la aplicación.
   - **PostgreSQL**: Base de datos utilizada para almacenar datos.
   - **Docker**: Contenerización de la aplicación.
   - **Maven**: Gestión de dependencias y construcción del proyecto.

## Requisitos

- **Java JDK 17** o superior
- **Maven 3.6.3** o superior
- **Docker** (versión 20.10.0 o superior)
- **Docker Compose** (versión 1.27.0 o superior)

## Configuración del entorno

1. **Clonar el repositorio:**
   ```
   git clone https://github.com/tu_usuario/AniClips.git
   cd AniClips
   ```

2. **Construir el proyecto:**
   Asegúrate de tener Maven instalado y ejecuta el siguiente comando para compilar el proyecto:
   ```
   mvn clean install
   ```

3. **Configurar Docker:**
   Asegúrate de que Docker y Docker Compose estén instalados y en funcionamiento. Puedes verificarlo con:
   ```
   docker --version
   docker-compose --version
   ```

4. **Levantar el contenedor:**
   Utiliza Docker Compose para levantar el contenedor de la aplicación. Asegúrate de estar en el directorio raíz del proyecto y ejecuta:
   ```bash
   docker-compose up
   ```

5. **Registrarse en la aplicación:**
   Puedes registrarte en la aplicación introduciendo tus datos a través de la siguiente petición en Postman:
   ```
   POST http://localhost:8081/auth/register
   ```

   **Cuerpo de la solicitud:**
   ```
   {
       "username": "tu_usuario",
       "email": "tu_email@example.com",
       "password": "tu_contraseña",
       "verifyPassword": "tu_contraseña"
   }
   ```

6. **Activar tu cuenta:**
   Después de registrarte, recibirás un correo con un código de activación. Debes introducir el código de activación con la siguiente petición:
   ```
   POST http://localhost:8081/activate/account/
   ```

   **Cuerpo de la solicitud:**
   ```json
   {
       "token": "tu_token_de_activacion"
   }
   ```

7. **Iniciar sesión:**
   Ahora solo queda iniciar sesión introduciendo tu nombre de usuario y contraseña a través de esta petición:
   ```
   POST http://localhost:8081/auth/login
   ```

   **Cuerpo de la solicitud:**
   ```json
   {
       "username": "tu_usuario",
       "password": "tu_contraseña"
   }
   ```

8. **Uso de la colección de Postman:**
   Puedes importar la colección de Postman que se encuentra en el archivo `Aniclips.postman_collection.json` para facilitar las pruebas de la API. Asegúrate de configurar la variable `BASEURL` en Postman a `http://localhost:8081`.

9. **Ejemplos de peticiones:**
    - **Obtener todos los clips:**
      ```
      GET http://localhost:8081/clip/?page=0
      ```

    - **Eliminar un clip:**
      ```
      DELETE http://localhost:8081/clip/delete/{clipId}
