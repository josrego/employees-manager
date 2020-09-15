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
    public final String ORDER_BY = " ORDER BY ";
    public final String WHERE = " WHERE ";

    @UtilityClass
    public class EmployeesQueries {
        public String CREATE_TABLE = "CREATE TABLE employee (" +
                "    id SERIAL primary KEY," +
                "    first_name VARCHAR(200) NOT NULL," +
                "    last_name VARCHAR(200) NOT NULL," +
                "    age INTEGER NOT NULL)";

        public String INSERT = INSERT_INTO + EmployeeTable.TABLE_NAME
                + " (" + EmployeeTable.Columns.getColumns() + ") "
                + "VALUES ($1, $2, $3) RETURNING (" + EmployeeTable.Columns.ID + ")";

        public String SELECT_ONE_FULL = SELECT_ALL_EMPLOYEES_WITH_CONTRACT
                + WHERE + "e." + EmployeeTable.Columns.ID + " = $1";
    }

    @UtilityClass
    public class ContractQueries {
        public String CREATE_TABLE = "CREATE TABLE contract (" +
                "    employee_id INTEGER PRIMARY KEY REFERENCES employee(id)," +
                "    expiration_date DATE NOT NULL)";

        public String INSERT = INSERT_INTO + ContractTable.TABLE_NAME
                + " (" + ContractTable.Columns.getColumns() + ") "
                + "VALUES ($1, $2)";
    }
}
