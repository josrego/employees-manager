package me.jrego.employees.manager.model.requests;

import lombok.Getter;
import me.jrego.employees.manager.repository.table.ContractTable;
import me.jrego.employees.manager.repository.table.EmployeeTable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Getter
public enum EmployeeSearchParameters {
    FIRST_NAME(EmployeeTable.Columns.FIRST_NAME, null),
    LAST_NAME(EmployeeTable.Columns.LAST_NAME, null),
    CONTRACT_EXPIRATION_DATE(ContractTable.Columns.EXPIRATION_DATE, dateAsString
            -> LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern("dd-MM-yyyy")));

    private final String columnName;
    private final Function<String, ?> parse;

    EmployeeSearchParameters(String columnName, Function<String, ?> parse) {
        this.columnName = columnName;
        this.parse = parse;
    }
}
