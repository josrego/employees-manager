package me.jrego.employees.manager.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEmployee {
    private String firstName;
    private String lastName;
}
