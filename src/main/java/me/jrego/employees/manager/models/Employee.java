package me.jrego.employees.manager.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Employee extends BaseEmployee {
    private int age;
    private Contract contractExpirationDate;

    public Employee(String firstName, String lastName, int age, Contract contractExpirationDate) {
        super(firstName, lastName);
        this.age = age;
        this.contractExpirationDate = contractExpirationDate;
    }
}
