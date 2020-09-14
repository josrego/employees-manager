package me.jrego.employees.manager.model;

import io.vertx.mutiny.sqlclient.Row;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class Employee extends BaseEmployee {
    private int age;
    private Contract contract;

    public Employee(String firstName, String lastName, int age, LocalDate contractExpirationDate) {
        super(firstName, lastName);
        this.age = age;
        this.contract = new Contract(contractExpirationDate);
    }

    public Employee(String firstName, String lastName, int age, Contract contract) {
        super(firstName, lastName);
        this.age = age;
        this.contract = contract;
    }

    public static Employee from(Row row) {
        if(row == null) {
            return null;
        }

        String firstName = row.getString("firstName");
        String lastName = row.getString("lastName");
        Integer age = row.getInteger("age");
        Contract contract = Contract.from(row);

        return new Employee(firstName, lastName, age, contract);
    }
}
