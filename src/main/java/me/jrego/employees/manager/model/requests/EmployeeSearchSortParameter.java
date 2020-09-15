package me.jrego.employees.manager.model.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.jrego.employees.manager.repository.table.ContractTable;

@Getter
@AllArgsConstructor
public class EmployeeSearchSortParameter {
    private final OrderBy orderBy;
    private final Direction direction;

    @Getter
    public enum OrderBy {
        CONTRACT_EXPIRATION_DATE(ContractTable.Columns.EXPIRATION_DATE);

        private final String columnName;

        OrderBy(String columnName) {
            this.columnName = columnName;
        }
    }

    @Getter
    public enum Direction {
        ASC("ASC"),
        DESC("DESC");

        private final String query;

        Direction(String query) {
            this.query = query;
        }
    }
}
