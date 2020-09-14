package me.jrego.employees.manager.model.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.jrego.employees.manager.model.BaseEmployee;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class EmployeesSearchQuery extends BaseEmployee {

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate contractExpirationDate;

    public EmployeesSearchQuery(String firstName, String lastName, LocalDate contractExpirationDate) {
        super(firstName, lastName);
        this.contractExpirationDate = contractExpirationDate;
    }
}
