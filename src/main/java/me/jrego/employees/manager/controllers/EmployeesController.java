package me.jrego.employees.manager.controllers;

import lombok.extern.log4j.Log4j2;
import me.jrego.employees.manager.models.Employee;
import me.jrego.employees.manager.services.EmployeesService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/employee")
@Log4j2
public class EmployeesController {

    @Inject
    private EmployeesService service;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Employee create(Employee employee) {
        log.debug("Creating employee : {}", employee);
        return employee;
    }
}