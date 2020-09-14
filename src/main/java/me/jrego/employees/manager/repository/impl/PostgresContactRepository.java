package me.jrego.employees.manager.repository.impl;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import me.jrego.employees.manager.model.Contract;
import me.jrego.employees.manager.repository.ContactRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PostgresContactRepository implements ContactRepository {

    private final static String CREATE_CONTRACT = "CREATE contract (employee_id, type, expiration_date) " +
            "values ($1, $2, $3)";

    @Inject
    PgPool client;

    @Override
    public Uni<Long> create(Contract contract, Long employeeId) {
        return client.preparedQuery(CREATE_CONTRACT)
                .execute(Tuple.of(employeeId, contract.getType(), contract.getExpirationDate()))
                .onItem().transform(r -> employeeId);
    }
}
