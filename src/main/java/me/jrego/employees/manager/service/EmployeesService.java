package me.jrego.employees.manager.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import me.jrego.employees.manager.model.Employee;
import me.jrego.employees.manager.model.requests.EmployeesSearchQuery;

public interface EmployeesService {
    Uni<Employee> create(Employee employee);
    Multi<Employee> find(EmployeesSearchQuery search);
}
