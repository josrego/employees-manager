package me.jrego.employees.manager.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeSearchParameters {
    private String firstName;
    private String lastName;
    private LocalDate expirationDate;
}
