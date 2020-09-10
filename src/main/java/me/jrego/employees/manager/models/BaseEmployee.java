package me.jrego.employees.manager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public abstract class BaseEmployee {
    private String firstName;
    private String lastName;
}
