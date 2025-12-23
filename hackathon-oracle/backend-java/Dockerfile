# ETAPA 1: Construcción (Build)
# Usamos una imagen que ya tiene Maven y Java 21 para compilar el proyecto
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos el archivo de configuración y el código fuente
COPY pom.xml .
COPY src ./src

# Ordenamos a Maven que compile el proyecto (saltando los tests para ir rápido)
RUN mvn clean package -DskipTests

# -------------------------------------------------------------------

# ETAPA 2: Ejecución (Run)
# Usamos una imagen ligera de Java 21 solo para correr la app (sin Maven)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos el archivo .jar que se creó en la Etapa 1
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto 8080
EXPOSE 8080

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]