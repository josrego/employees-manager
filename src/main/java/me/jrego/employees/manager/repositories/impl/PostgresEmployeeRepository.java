package me.jrego.employees.manager.repositories.impl;

import me.jrego.employees.manager.models.Employee;
import me.jrego.employees.manager.models.requests.EmployeesSearchQuery;
import me.jrego.employees.manager.repositories.EmployeeRepository;

import java.util.List;

public class PostgresEmployeeRepository implements EmployeeRepository {

    @Override
    public void create(Employee employee) {

    }

    @Override
    public List<Employee> find(EmployeesSearchQuery search) {
        return null;
    }
}
