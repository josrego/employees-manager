package me.jrego.employees.manager.controller;

import io.smallrye.mutiny.Multi;
import lombok.extern.log4j.Log4j2;
import me.jrego.employees.manager.model.Employee;
import me.jrego.employees.manager.model.requests.EmployeesSearchQuery;
import me.jrego.employees.manager.service.EmployeesService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.Date;

@Path("/employees")
@Log4j2
public class EmployeesController {

    @Inject
    private EmployeesService service;

    @GET
    @Produces("application/json")
    public Multi<Employee> find(@QueryParam("firstName") String firstName,
                                @QueryParam("lastName") String lastName,
                                @QueryParam("contractExpirationDate") Date contractExpirationDate) {
        EmployeesSearchQuery employeesSearchQuery = new EmployeesSearchQuery(firstName, lastName, contractExpirationDate);
        log.info("find employees with params: {}", employeesSearchQuery.toString());

        return service.find(employeesSearchQuery);
    }
}