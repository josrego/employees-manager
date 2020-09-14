package me.jrego.employees.manager.repository.table;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeTable {
    public final String TABLE_NAME = "employee";

    @UtilityClass
    public class Columns {
        public final String ID = "id";
        public final String FIRST_NAME = "first_name";
        public final String LAST_NAME = "last_name";
        public final String AGE = "age";

        public String getColumns() {
            return String.join(", ",
                    FIRST_NAME,
                    LAST_NAME,
                    AGE
            );
        }
    }
}
