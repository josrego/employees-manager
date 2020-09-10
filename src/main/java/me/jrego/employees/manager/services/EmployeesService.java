package me.jrego.employees.manager.services;

import me.jrego.employees.manager.models.Employee;
import me.jrego.employees.manager.models.requests.EmployeesSearchQuery;

import java.util.List;

public interface EmployeesService {
    void create(Employee employee);
    List<Employee> find(EmployeesSearchQuery search);
}
