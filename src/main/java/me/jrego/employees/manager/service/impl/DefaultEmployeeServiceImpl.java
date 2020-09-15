package me.jrego.employees.manager.service.impl;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.extern.log4j.Log4j2;
import me.jrego.employees.manager.model.Employee;
import me.jrego.employees.manager.model.requests.EmployeeSearchSortParameter;
import me.jrego.employees.manager.model.requests.EmployeesSearchQuery;
import me.jrego.employees.manager.repository.EmployeeRepository;
import me.jrego.employees.manager.service.EmployeesService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Log4j2
public class DefaultEmployeeServiceImpl implements EmployeesService {

    @Inject
    EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public Uni<Employee> create(Employee employee) {
        return employeeRepository.create(employee);
    }

    @Override
    public Uni<Employee> find(Long employeeId) {
        return employeeRepository.find(employeeId);
    }

    @Override
    public Multi<Employee> findAll(EmployeesSearchQuery search) {
        return employeeRepository.findAll(search);
    }

    @Override
    public Multi<Employee> findAllOrderByContractExpirationDate(EmployeesSearchQuery search) {
        search.setSortParameter(
                EmployeeSearchSortParameter.OrderBy.CONTRACT_EXPIRATION_DATE,
                EmployeeSearchSortParameter.Direction.ASC);

        return employeeRepository.findAll(search)
                .onItem().invoke(employee -> employee.getContract().calculateDaysUntilExpiration());
    }
}
