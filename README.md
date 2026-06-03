# ms-proyectos

Microservicio de **Proyectos** del sistema SPSRT (UNIR, MISSI): proyectos (sistemas),
subproyectos y tareas — gestión operativa del área de desarrollo.

Parte del sistema **SPSRT — Sistema de Planificación y Seguimiento de Recursos Técnicos**.
El stack completo se orquesta desde el repo
[`orquestacion`](https://github.com/tfm-missi-2026/orquestacion).

## Datos del servicio

| | |
|---|---|
| Puerto | **8082** |
| Base de datos | `spsrt_proyectos` (PostgreSQL 16) |
| Prefijo de tablas | `msp_` |
| Paquete base | `pe.unir.tfm.srp.proyectos` |
| Stack | Java 21 · Spring Boot 3.5.14 · MyBatis · Flyway · Eureka client |

## URLs útiles (con el stack completo levantado)

- Eureka dashboard — http://localhost:8761
- API Gateway — http://localhost:8080 (`/actuator/health`)
- Swagger — ms-administracion `:8081` · ms-proyectos `:8082` · ms-seguimiento `:8083` (`/swagger-ui.html`)

## Requisitos

- **Docker Desktop 24+** con docker compose v2 (forma recomendada), **o**
- Java 21 + Maven 3.9+ para compilar/ejecutar fuera de contenedor.

## Levantar standalone (solo este servicio + su PostgreSQL)

```bash
cp .env.example .env
docker compose up -d --build
```

Arranca el microservicio + un PostgreSQL propio (sin Eureka). La BD y el usuario se crean
solos con las variables del `.env`; Flyway aplica las migraciones al arrancar.

- API:     http://localhost:8082
- Health:  http://localhost:8082/actuator/health
- Swagger: http://localhost:8082/swagger-ui.html

> Los endpoints de negocio exigen un **JWT** emitido por `ms-administracion`. En standalone
> puedes levantar también `ms-administracion` para obtener el token, o probar
> `/actuator/health` sin autenticación.

## Dentro del stack completo

Para correrlo junto a Eureka, el gateway y los demás microservicios, usa el repo
[`orquestacion`](https://github.com/tfm-missi-2026/orquestacion). Allí entra por el gateway
(`http://localhost:8080/api/...`).

## Endpoints principales

| Recurso | Ruta |
|---|---|
| Proyectos | `/api/proyectos` |
| Subproyectos | `/api/subproyectos` |
| Tareas | `/api/tareas` |

## Migraciones

`src/main/resources/db/migration/V*.sql` (Flyway, se aplican al arrancar). El `V1` instala
la extensión `pgcrypto` necesaria para `gen_random_uuid()`.
