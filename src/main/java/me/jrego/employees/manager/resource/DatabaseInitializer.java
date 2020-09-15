package me.jrego.employees.manager.resource;

import io.quarkus.runtime.Startup;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import me.jrego.employees.manager.repository.util.Queries;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

@Startup
@Singleton
@Slf4j
public class DatabaseInitializer {

    @Inject
    @ConfigProperty(name = "schema.create", defaultValue = "false")
    boolean schemaCreate;

    @Inject
    PgPool client;

    @PostConstruct
    public void init() {
        if(schemaCreate) {
            initDb();
        }
    }

    private void initDb() {
        log.info("Creating database");
        client.query("DROP TABLE IF EXISTS contract").execute()
                .flatMap(r -> client.query("DROP TABLE IF EXISTS employee").execute())
                .flatMap(r -> client.query(Queries.EmployeesQueries.CREATE_TABLE).execute())
                .flatMap(r -> client.query(Queries.ContractQueries.CREATE_TABLE).execute())
                .await().indefinitely();
    }
}
