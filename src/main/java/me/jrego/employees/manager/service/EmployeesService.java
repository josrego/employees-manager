package me.jrego.employees.manager.service;

import io.smallrye.mutiny.Uni;
import me.jrego.employees.manager.model.Employee;
import me.jrego.employees.manager.model.requests.EmployeesSearchQuery;

import java.util.List;

public interface EmployeesService {
    Uni<Employee> create(Employee employee);
    List<Employee> find(EmployeesSearchQuery search);
}
