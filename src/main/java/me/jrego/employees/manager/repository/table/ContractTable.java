package me.jrego.employees.manager.repository.table;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ContractTable {
    public final String TABLE_NAME = "contract";

    @UtilityClass
    public class Columns {
        public final String EMPLOYEE_ID = "employee_id";
        public final String EXPIRATION_DATE = "expiration_date";

        public String getColumns() {
            return String.join(", ",
                    EMPLOYEE_ID,
                    EXPIRATION_DATE
            );
        }
    }
}
