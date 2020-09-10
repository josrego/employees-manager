package me.jrego.employees.manager.repositories.impl;

import me.jrego.employees.manager.models.Employee;
import me.jrego.employees.manager.models.EmployeeSearchParameters;
import me.jrego.employees.manager.repositories.EmployeeRepository;

import java.util.List;

public class PostgresEmployeeRepository implements EmployeeRepository {

    @Override
    public void create(Employee employee) {

    }

    @Override
    public List<Employee> find(EmployeeSearchParameters search) {
        return null;
    }
}
