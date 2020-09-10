package me.jrego.employees.manager.repositories;

import me.jrego.employees.manager.models.Employee;
import me.jrego.employees.manager.models.requests.EmployeesSearchQuery;

import java.util.List;

public interface EmployeeRepository {
    void create(Employee employee);
    List<Employee> find(EmployeesSearchQuery search);
}
