package me.jrego.employees.manager.repository;

import io.smallrye.mutiny.Uni;
import me.jrego.employees.manager.model.Employee;
import me.jrego.employees.manager.model.requests.EmployeesSearchQuery;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    Uni<Long> create(Employee employee);

    Uni<Employee> get(Long employeeId);

    List<Employee> find(EmployeesSearchQuery search);
}
