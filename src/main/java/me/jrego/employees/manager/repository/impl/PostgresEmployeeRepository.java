package me.jrego.employees.manager.repository.impl;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.extern.log4j.Log4j2;
import me.jrego.employees.manager.model.Employee;
import me.jrego.employees.manager.model.requests.EmployeesSearchQuery;
import me.jrego.employees.manager.repository.EmployeeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@Log4j2
@ApplicationScoped
public class PostgresEmployeeRepository implements EmployeeRepository {

    public static final String CREATE_EMPLOYEE_QUERY = "INSERT INTO employee (firstName, lastName, age) " +
            "VALUES ($1, $2, $3) RETURNING (id)";

    public static final String FIND_EMPLOYEE_QUERY = "SELECT * FROM EMPLOYEE e INNER JOIN contract c " +
            "on e.id = c.id = employee_id WHERE e.id = $1";

    @Inject
    PgPool client;

    @Override
    public Uni<Long> create(Employee employee) {
        return client.preparedQuery(CREATE_EMPLOYEE_QUERY)
                .execute(
                        Tuple.of(employee.getFirstName(), employee.getLastName(), employee.getAge()))
                .onItem().transform(rowSet -> rowSet.iterator().next().getLong("id"));
    }

    @Override
    public Uni<Employee> get(Long employeeId) {
        return client.preparedQuery(FIND_EMPLOYEE_QUERY)
                .execute(
                        Tuple.of(employeeId)
                ).onItem().transform(set -> set.iterator().next())
                .onItem().transform(Employee::from);

    }

    @Override
    public List<Employee> find(EmployeesSearchQuery search) {
        return null;
    }
}
