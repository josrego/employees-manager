package me.jrego.employees.manager.service.impl;

import io.smallrye.mutiny.Uni;
import lombok.extern.log4j.Log4j2;
import me.jrego.employees.manager.model.Employee;
import me.jrego.employees.manager.model.requests.EmployeesSearchQuery;
import me.jrego.employees.manager.repository.ContactRepository;
import me.jrego.employees.manager.repository.EmployeeRepository;
import me.jrego.employees.manager.service.EmployeesService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Log4j2
public class DefaultEmployeeServiceImpl implements EmployeesService {

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    ContactRepository contactRepository;

    @Override
    @Transactional
    public Uni<Employee> create(Employee employee) {
        return employeeRepository.create(employee)
                .chain(employeeId -> contactRepository.create(employee.getContract(), employeeId))
                .chain(employeeId -> employeeRepository.get(employeeId));
    }

    @Override
    public List<Employee> find(EmployeesSearchQuery search) {
        return null;
    }
}
