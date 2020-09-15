package me.jrego.employees.manager.repository.impl;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlClient;
import io.vertx.mutiny.sqlclient.SqlClientHelper;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.extern.log4j.Log4j2;
import me.jrego.employees.manager.model.Contract;
import me.jrego.employees.manager.model.Employee;
import me.jrego.employees.manager.model.requests.EmployeesSearchQuery;
import me.jrego.employees.manager.repository.EmployeeRepository;
import me.jrego.employees.manager.repository.table.EmployeeTable;
import me.jrego.employees.manager.repository.util.Queries;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Log4j2
@ApplicationScoped
public class PostgresEmployeeRepository implements EmployeeRepository {

    @Inject
    PgPool client;

    @Override
    public Uni<Employee> create(Employee employee) {
        log.info("inserting employee in db - {}", employee.toString());

        return SqlClientHelper.inTransactionUni(client, tx -> tx.preparedQuery(Queries.EmployeesQueries.INSERT)
                .execute(
                        Tuple.of(employee.getFirstName(), employee.getLastName(), employee.getAge())
                ).onItem().transform(rowSet -> rowSet.iterator().next().getLong(EmployeeTable.Columns.ID))
                .onItem().transformToUni(employeeId -> createContract(tx, employee.getContract(), employeeId))
                .onItem().transformToUni(employeeId -> get(tx, employeeId))
        );
    }

    @Override
    public Uni<Employee> get(Long employeeId) {
        return get(client, employeeId);
    }

    @Override
    public Multi<Employee> find(EmployeesSearchQuery search) {
        String baseQuery = Queries.SELECT_ALL_EMPLOYEES_WITH_CONTRACT;
        String fullQuery = applyConstraints(baseQuery, search);

        log.info("searching employees in db from query {}", fullQuery);

        return client.preparedQuery(fullQuery)
                .execute(search.getArgumentValues())
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                .onItem().transform(Employee::from);
    }

    private Uni<Long> createContract(SqlClient transactionClient, Contract contract, Long employeeId) {
        log.info("inserting contract {} in db to employee id: {}", contract.toString(), employeeId);

        return transactionClient.preparedQuery(Queries.ContractQueries.INSERT)
                .execute(
                        Tuple.of(employeeId, contract.getExpirationDate())
                ).onItem().transform(r -> employeeId);
    }

    public Uni<Employee> get(SqlClient sqlClient, Long employeeId) {
        log.info("Retrieving employee {}", employeeId);

        return sqlClient.preparedQuery(Queries.EmployeesQueries.SELECT_ONE_FULL)
                .execute(
                        Tuple.of(employeeId)
                ).onItem().transform(set -> set.iterator().next())
                .onItem().transform(Employee::from);
    }

    private String applyConstraints(String baseQuery, EmployeesSearchQuery search) {
        if (search.isEmpty()) {
            return baseQuery;
        }

        return baseQuery + Queries.WHERE + search.getConstrains();
    }
}
