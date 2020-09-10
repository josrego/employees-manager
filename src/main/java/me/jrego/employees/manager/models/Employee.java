package me.jrego.employees.manager.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Employee {
    private String firstName;
    private String lastName;
    private int age;
    private LocalDate expirationDate;
}
