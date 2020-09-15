package me.jrego.employees.manager.controller;

import io.smallrye.mutiny.Uni;
import lombok.extern.log4j.Log4j2;
import me.jrego.employees.manager.model.Employee;
import me.jrego.employees.manager.service.EmployeesService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/employee")
@Log4j2
public class EmployeeController {

    @Inject
    private EmployeesService service;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Uni<Employee> create(@Valid @NotNull Employee employee) {
        log.debug("Creating employee : {}", employee);
        return service.create(employee);
    }
}