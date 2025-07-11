# 🧪 API de Usuarios — Prueba Técnica

Este proyecto es una **API RESTful** construida con **Java 17**, **Spring Boot 3.4.7** y **Maven**, como parte de una prueba técnica. La API permite gestionar usuarios y sus direcciones de forma dinámica y completamente en memoria.

---

## ✅ Características

- Almacena usuarios con los siguientes atributos:
    - `id`, `email`, `name`, `password`, `createdAt`, `addresses[]`
- Las contraseñas se almacenan con el algoritmo **SHA-1**
- El campo `createdAt` se genera automáticamente en **zona horaria UK**
- Las contraseñas no se devuelven en las respuestas
- Soporte completo para múltiples direcciones por usuario

---

## 📚 Endpoints disponibles

| Método | Endpoint                                             | Descripción                                                              |
|--------|------------------------------------------------------|--------------------------------------------------------------------------|
| GET    | `/users?sortedBy=email` or `id` or `name` or `created_at` | Lista los usuarios, ordenados si se especifica el parámetro `sortedBy`  |
| GET    | `/users/{user_id}/addresses`                         | Devuelve las direcciones de un usuario por su `id`                       |
| PUT    | `/users/{user_id}/addresses/{address_id}`            | Actualiza una dirección específica de un usuario                         |
| POST   | `/users`                                             | Crea un nuevo usuario                                                    |
| PATCH  | `/users/{id}`                                        | Actualiza parcialmente los atributos de un usuario                       |
| DELETE | `/users/{id}`                                        | Elimina un usuario por su `id`                                           |

---

## Instrucciones

Asegúrate de tener la versión 17 del JDK. De igual manera, se recomienda que el proyecto 
sea ejecutado en **IntelliJ IDEA**. Si es la versión Community, asegúrate de tener configurado 
el Run/Debug con **spring-boot:run**.

Una vez que el proyecto se encuentre en ejecución, por defecto se levantará en el puerto 8080. 
En este momento podrás probar los Endpoints. Para mayor facilidad, incluí una colección de **Postman** en el proyecto o de igual forma puedes probar con **Swagger** con la dirección http://localhost:8080/swagger-ui/index.html.