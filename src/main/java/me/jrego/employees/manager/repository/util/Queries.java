package me.jrego.employees.manager.repository.util;

import lombok.experimental.UtilityClass;
import me.jrego.employees.manager.repository.table.ContractTable;
import me.jrego.employees.manager.repository.table.EmployeeTable;

@UtilityClass
public class Queries {
    public final String INSERT_INTO = "INSERT INTO ";
    public final String SELECT_ALL_FROM = "SELECT * FROM ";
    public final String SELECT_ALL_EMPLOYEES_WITH_CONTRACT = SELECT_ALL_FROM + EmployeeTable.TABLE_NAME
            + " e INNER JOIN " + ContractTable.TABLE_NAME + " c "
            + "ON e.id = c.employee_id ";
    public String WHERE = " WHERE ";

    @UtilityClass
    public class EmployeesQueries {
        public String INSERT = INSERT_INTO + EmployeeTable.TABLE_NAME
                + " (" + EmployeeTable.Columns.getColumns() + ") "
                + "VALUES ($1, $2, $3) RETURNING (" + EmployeeTable.Columns.ID + ")";

        public String SELECT_ONE_FULL = SELECT_ALL_EMPLOYEES_WITH_CONTRACT
                + WHERE + "e." + EmployeeTable.Columns.ID + " = $1";
    }

    @UtilityClass
    public class ContractQueries {
        public String INSERT = INSERT_INTO + ContractTable.TABLE_NAME
                + " (" + ContractTable.Columns.getColumns() + ") "
                + "VALUES ($1, $2)";
    }
}
