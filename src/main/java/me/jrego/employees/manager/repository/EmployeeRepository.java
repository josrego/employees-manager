package me.jrego.employees.manager.repository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import me.jrego.employees.manager.model.Employee;
import me.jrego.employees.manager.model.requests.EmployeesSearchQuery;

public interface EmployeeRepository {
    Uni<Employee> create(Employee employee);

    Uni<Employee> get(Long employeeId);

    Multi<Employee> find(EmployeesSearchQuery search);
}
