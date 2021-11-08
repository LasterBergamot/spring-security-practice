# Spring Security Practice
Several Spring Boot apps with different approaches on security, such as mTLS, OAuth2 and JWT.

---

## For JWT and OAuth2

Before starting the application, run `docker compose up -d` from the root of the project to start up the **PostgreSQL** database and **Adminer**.

To tear down the services mentioned above, run `docker compose down`.

The user related tables are always created when the application starts and always dropped when the application is shut down.