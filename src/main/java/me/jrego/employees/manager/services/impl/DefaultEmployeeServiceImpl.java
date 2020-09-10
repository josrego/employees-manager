package me.jrego.employees.manager.services.impl;

import me.jrego.employees.manager.models.Employee;
import me.jrego.employees.manager.models.EmployeeSearchParameters;
import me.jrego.employees.manager.services.EmployeesService;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class DefaultEmployeeServiceImpl implements EmployeesService {

    @Override
    public void create(Employee employee) {

    }

    @Override
    public List<Employee> find(EmployeeSearchParameters search) {
        return null;
    }
}
