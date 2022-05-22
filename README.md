# liquibase-hibernate-demo
A demo of using Liquibase + Hibernate to manage migrations in a Java application.

## Run the demo

```bash
# Compile the program
mvn package
# Run the application
mvn exec:java
```

By default, the demo will use an in-memory instance of H2.
You can change the DB engine by specifying the one you want to use with `-D exec:args <engine>`:

```bash
# Use PostgreSQL instead of H2.
mvn exec:java -Dexec:args="postgres"
```

## Starting an instance of PostgreSQL

By default, the PostgreSQL config expects an instance to be running on the local host at port 5432.
The easiest way to start one is to use docker:

```bash
docker run --rm -p 5432:5432 -e POSTGRES_PASSWORD=postgres postgres:14.2
```