package me.jrego.employees.manager.model;

import io.vertx.mutiny.sqlclient.Row;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.jrego.employees.manager.repository.table.EmployeeTable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class Employee extends BaseEmployee {

    @NotNull(message = "Age cannot be null")
    @Positive(message = "Age has to be a positive integer")
    private int age;

    @Valid
    private Contract contract;

    public Employee(String firstName, String lastName, int age, Contract contract) {
        super(firstName, lastName);
        this.age = age;
        this.contract = contract;
    }

    public static Employee from(Row row) {
        if (row == null) {
            return null;
        }

        String firstName = row.getString(EmployeeTable.Columns.FIRST_NAME);
        String lastName = row.getString(EmployeeTable.Columns.LAST_NAME);
        Integer age = row.getInteger(EmployeeTable.Columns.AGE);
        Contract contract = Contract.from(row);

        return new Employee(firstName, lastName, age, contract);
    }
}
