# Pasos para correr el programa
1. Clonar el repositorio.
2. Instalar Intellij Idea community
3. Instalar Docker Desktop
4. Activar Docker, tendra un boton verde activado.
5. Si es H2 la base de datos no hace falta activarla, si es mySQL activar la base de datos local.
6. Instalar las dependencias del pom, se hace automático
7. Abrir una temrinal en el IDe y ejecutar en orden
8. Se instalará y descargara el modelo, la primera vez demorará, la segunda ya no.
9. Entra a http://localhost:8501 para ver la interfaz de usuario.

## 🚀 Comandos Docker Compose

| Comando                     | Descripción                                                                 |
|-----------------------------|-----------------------------------------------------------------------------|
| `docker-compose up --build` | Construye las imágenes y levanta los contenedores nuevamente                |
| `docker-compose down`       | Detiene y elimina los contenedores, redes y volúmenes definidos en el stack |

