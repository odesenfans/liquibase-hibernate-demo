# liquibase-hibernate-demo
A demo of using Liquibase + Hibernate to manage migrations in a Java application.

## Start the dev environment

We currently recommend using PostgreSQL to run the demo. First, start an instance of PostgreSQL.
We recommend using Docker:

```bash
docker run --rm -p 5432:5432 -e POSTGRES_PASSWORD=postgres postgres:14.2
```

## Run migrations

We use the [liquibase-hibernate](https://github.com/liquibase/liquibase-hibernate/wiki) plugin
to run and generate DB migrations. Migration scripts are stored in infra/liquibase/scripts.

```bash

```

## Run the demo

```bash
# Compile the program
mvn package
# Run the application
mvn exec:java
```

By default, the demo will use PostgreSQL.
You can change the DB engine by specifying the one you want to use with `-D exec:args <engine>`:

```bash
# Use H2 instead of PostgreSQL.
mvn exec:java -Dexec:args="h2"
```

# How to

## Generate migration files

The point of the demo is to show that you can "easily" generate migration scripts based on changes
to your Hibernate entities. Let's imagine you add a "salary" column to the `Employee` entity:

```java
@Entity
@Table(name = "employees", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "email") })
public class Employee implements Serializable {
    @Column(name = "salary", unique = false, nullable = false)
    private int salary;
    
    // ...
}
```

With this setup, you can generate a new migration script by running the following commands:

```bash
# First, rebuild the package
mvn package
# Generate a migration script based on the package
mvn liquibase:diff
```

This will generate a new timestamped file in infra/scripts (example: 20220522214304_changelog.xml).

```xml
    <changeSet author="odesenfans (generated)" id="1653256599443-4">
    <addColumn tableName="employees">
        <column name="salary" type="int4">
            <constraints nullable="false"/>
        </column>
    </addColumn>
</changeSet>
```

The next step is to review this file to make sure that it will not impact the DB. Pay attention
to things like default values not being set, as this could make the migration fail.
For example, the migration above will only work if the table does not contain data.
Otherwise, you will need to split the change set in several parts:
1. Create the salary column (nullable)
2. Update the table to set a default value
3. Make the salary column non-nullable.

You can also simplify the script in any way you like as long as the end result remains the same.

Once you are happy with the migration script, rename it to the name of your choice and add it
to the main changelog. For example, this project uses numerical IDs for the scripts, so we would
rename the script to `0002_employees_add_salary_column.xml` and then add the following line
to `dbchangelog.xml`:

```xml
<include file="scripts/0002_employees_add_salary_column.xml" relativeToChangelogFile="true"/>
```

You can then test the migration with:

```bash
mvn liquibase:update
```

# Design choices

## Migration scripts

Liquibase works with a single changelog XML file that can grow quite large.
We recommend to split this file as soon as possible into multiple smaller files,
as showcased in the infra/liquibase directory.

The main `dbchangelog.xml` file only includes other migration files, making the whole
system easier to maintain.

## Automatic model detection vs hibernate:classic

When setting up Liquibase + Hibernate, [the documentation suggests](https://github.com/liquibase/liquibase-hibernate/wiki) 
that it is possible to configure Liquibase to use the hibernate configuration file to detect entities.
It seems that this feature is currently buggy, as suggested by [this Github issue](https://github.com/liquibase/liquibase-hibernate/issues/249).

To resolve this, we moved to the automatic detection of JPA models using Spring Boot.
This adds an unwanted dependency (Spring Boot), but it seems to be the only solution at the time of writing.
This also requires all models to reside in the same package.
