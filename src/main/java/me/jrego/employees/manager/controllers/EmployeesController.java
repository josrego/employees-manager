package me.jrego.employees.manager.controllers;

import lombok.extern.log4j.Log4j2;
import me.jrego.employees.manager.models.Employee;
import me.jrego.employees.manager.models.requests.EmployeesSearchQuery;
import me.jrego.employees.manager.services.EmployeesService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Path("/employees")
@Log4j2
public class EmployeesController {

    @Inject
    private EmployeesService service;

    @GET
    @Produces("application/json")
    public List<Employee> find(@PathParam("firstName") String firstName,
                               @PathParam("lastName") String lastName,
                               @PathParam("contractExpirationDate") LocalDate contractExpirationDate) {
        EmployeesSearchQuery employeesSearchQuery = new EmployeesSearchQuery(firstName, lastName, contractExpirationDate);

        log.info("find employees with params: {}", employeesSearchQuery.toString());

        List<Employee> employees = service.find(employeesSearchQuery);
        debugEmployeeList(employees);

        return employees;
    }

    private void debugEmployeeList(List<Employee> employees) {
        if (log.isDebugEnabled()) {
            String joinedEmployeeString = employees.stream().map(Employee::toString).collect(Collectors.joining(","));
            log.debug("Result for find employees: {}", joinedEmployeeString);
        }
    }
}