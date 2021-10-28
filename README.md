# Spring Security Practice
Several Spring Boot apps with different approaches on security, such as mTLS, OAuth2 and JWT.

---

## JWT

Before starting the application, run `docker compose up -d` from the root of the project to start up the **PostgreSQL** database and **Adminer**.

To tear down the services mentioned above, run `docker compose down`.

By default, a **default user**, with username and password **admin**, is created when the application starts, which is stored in the `jwt_user` table. This table is always dropped and created with application shutdown and startup, so any data persisted will be lost.