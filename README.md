# TaskMaster

TaskMaster is a Spring Boot web application for task management with real‑time chat and GitHub/Slack integrations.

See `db/schema.sql` for database DDL (PostgreSQL).

## Quick start (dev)

```bash
# ensure PostgreSQL is running and database 'taskdb' exists
psql -c "CREATE DATABASE taskdb;" -U postgres

export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/taskdb
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=postgres

mvn spring-boot:run
```

Default users will be seeded: **admin/adminpass** (ROLE_ADMIN) and **user/userpass** (ROLE_USER).

