package me.jrego.employees.manager.resource;


import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Collections;
import java.util.Map;

public class PostgresDatabaseResource implements QuarkusTestResourceLifecycleManager {

    public static final String SCHEMA_SQL_SCRIPT = "db/schema.sql";
    public static final PostgreSQLContainer DATABASE = new PostgreSQLContainer<>("postgres:10.5")
            .withDatabaseName("employees_api")
            .withUsername("employees")
            .withPassword("employees")
            .withExposedPorts(5432)
            .withInitScript(SCHEMA_SQL_SCRIPT);

    @Override
    public Map<String, String> start() {
        DATABASE.start();
        return Collections.singletonMap("quarkus.datasource.reactive.url", DATABASE.getJdbcUrl().replace("jdbc:", ""));
    }

    @Override
    public void stop() {
        DATABASE.stop();
    }
}
