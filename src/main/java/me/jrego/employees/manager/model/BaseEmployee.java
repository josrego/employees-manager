package me.jrego.employees.manager.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEmployee {

    @NotBlank(message = "First name cannot be blank and it's obligatory")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank and it's obligatory")
    private String lastName;
}
