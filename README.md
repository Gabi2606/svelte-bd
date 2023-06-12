## Kotlin + SpringBoot en un solo proyecto

Podéis utilizar este repositorio para comenzar el proyecto GetFitApp, pero también podéis
hacerlo de otra manera. 

### Estructura del proyecto

La estructura de este repositorio es similar a la de un proyecto de SpringBoot, pero también contiene
una carpeta `web-client`, en la que está el código del cliente web (frontend hecho con Svelte).

### Cómo desarrollar con este proyecto

##### Backend 

Para trabajar con el backend, desde la carpeta principal podemos ejecutar los comandos de
Gradle (o usar IntelliJ, Visual Studio, etc):

```
# en windows
gradlew.bat bootRun

# en linux/mac
./gradlew bootRun
```

##### Compilación automática del frontend

La configuración del archivo `build.gradle.kts` obliga a compilar el proyecto
de frontend (carpeta `web-client`) antes de compilar el backend, de modo que la SPA se incluye en el `.jar` generado por Gradle.
El resultado es que podemos acceder a la app del cliente web en `localhost:8080/` si lanzamos el servidor de SpringBoot.



#### Frontend

Para trabajar con el frontend por separado es posible usar npm desde la carpeta `web-client`:

##### Instalar dependencias

```
npm install
```

##### Servidor de desarrollo con autorrecarga

```
npm run dev
```
