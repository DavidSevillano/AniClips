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

   🖥️ Backend
   
   - **Java**: Lenguaje de programación principal.
   - **Spring Boot**: Framework para construir la aplicación.
   - **PostgreSQL**: Base de datos utilizada para almacenar datos.
   - **Docker**: Contenerización de la aplicación.
   - **Maven**: Gestión de dependencias y construcción del proyecto.

   📱 Frontend (Android)

   - **Java (Android)**: Desarrollo de la aplicación móvil nativa.
   - **XML**: Definición de interfaces gráficas (layouts) en Android Studio
   - **Android Studio**: Entorno de desarrollo para la app móvil.
   - **OkHttp**: Cliente HTTP para consumir la API REST desde Android.

## Requisitos

   🖥️ Backend

   - **Java JDK 17** o superior
   - **Maven 3.6.3** o superior
   - **Docker** (versión 20.10.0 o superior)
   - **Docker Compose** (versión 1.27.0 o superior)

   📱 Frontend (Android)

   - **Android Studio Giraffe** o superior
   - **JDK 11 o 17** configurado en Android Studio
   - **SDK** de Android API 26+
   - **Emulador o dispositivo físico** para pruebas

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

3. **Ajustar rutas de imágenes y videos:**
   Asegúrate de modificar las URLs de imágenes y videos en el archivo import.sql, ya que apuntan a una IP de desarrollo, reemplazala por tu ip local (ej: 192.168.x.x:8080).
   ```
   'http://192.168.x.x:8080/iconos/icono_ejemplo1.jpg', 'Soy Naruto Uzumaki, futuro Hokage!'
   'Bleach', 'Naruto vs Pain', 'http://192.168.x.x:8080/videos/Bleach_1.mp4'
   ```

4. **Configurar Docker:**
   Asegúrate de que Docker y Docker Compose estén instalados y en funcionamiento. Puedes verificarlo con:
   ```
   docker --version
   docker-compose --version
   ```

5. **Levantar el contenedor:**
   Utiliza Docker Compose para levantar el contenedor de la aplicación. Asegúrate de estar en el directorio raíz del proyecto y ejecuta:
   ```bash
   docker-compose up
   ```

6. **Registrarse en la aplicación:**
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

7. **Activar tu cuenta:**
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

8. **Iniciar sesión:**
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

9. **Uso de la colección de Postman:**
   Puedes importar la colección de Postman que se encuentra en el archivo `Aniclips.postman_collection.json` para facilitar las pruebas de la API. Asegúrate de configurar la variable `BASEURL` en Postman a `http://localhost:8081`.

10. **Ejemplos de peticiones:**
    - **Obtener todos los clips:**
      ```
      GET http://localhost:8081/clip/?page=0
      ```

    - **Eliminar un clip:**
      ```
      DELETE http://localhost:8081/clip/delete/{clipId}

## Si quieres probarlo con un dispositivo movil físico o virtual solo tienes que:

1. **Abrir el proyecto en Android Studio**

    - Abre Android Studio.

    - Selecciona "Open an existing project".

    - Navega hasta la carpeta AniClips-Android (o el nombre de tu carpeta frontend) y ábrela.
  
2. **Configurar la IP del backend**

Como el backend está dockerizado, localhost no funcionará desde un dispositivo físico ni desde un emulador. Para solucionarlo:

   - Asegúrate de estar en la misma red Wi-Fi que el dispositivo.
     
   - Obtén la IP local de tu máquina desde el terminal con:
        ```
     **ipconfig**
        ```

   - En el proyecto abre la clase **Constantes.java** y cambia la urlbase.
        ```
     public static final String BASE_URL = "http://<IP_LOCAL>:8081";
        ```

3. **Ejecutar en un dispositivo o emulador**

   - Conecta tu dispositivo Android por USB (modo desarrollador activado) o usa un emulador
     
   - Presiona el botón de Run (▶️) en Android Studio.
     
   - La app se iniciará y si el backend está levantado correctamente con Docker, podrás consumir todos los endpoints disponibles.
