package me.jrego.employees.manager.controllers;

import me.jrego.employees.manager.services.EmployeesService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/employees")
public class EmployeesController {

    @Inject
    private EmployeesService service;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }
}