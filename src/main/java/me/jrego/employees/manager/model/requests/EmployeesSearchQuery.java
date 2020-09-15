package me.jrego.employees.manager.model.requests;

import io.vertx.mutiny.sqlclient.Tuple;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class EmployeesSearchQuery {

    Map<EmployeeSearchParameters, String> searchParameters;

    public EmployeesSearchQuery(String firstName, String lastName, String expirationDate) {
        searchParameters = new LinkedHashMap<>();

        searchParameters.put(EmployeeSearchParameters.FIRST_NAME, firstName);
        searchParameters.put(EmployeeSearchParameters.LAST_NAME, lastName);
        searchParameters.put(EmployeeSearchParameters.CONTRACT_EXPIRATION_DATE, expirationDate);
    }

    public String getConstrains() {
        AtomicInteger i = new AtomicInteger(1);

        return searchParameters.entrySet()
                .stream()
                .filter(entry -> StringUtils.isNotEmpty(entry.getValue()))
                .map(entry -> getConstraint(entry.getKey(), i.getAndIncrement()))
                .collect(Collectors.joining(" AND "));
    }

    public boolean isEmpty() {
        return searchParameters.values().stream().allMatch(StringUtils::isEmpty);
    }

    public Tuple getArgumentValues() {
        if (isEmpty()) {
            return Tuple.tuple();
        }

        return Tuple.tuple(
                this.searchParameters.entrySet()
                        .stream()
                        .filter(entry -> StringUtils.isNotEmpty(entry.getValue()))
                        .map(entry -> entry.getKey().getParse() == null ? entry.getValue()
                                : entry.getKey().getParse().apply(entry.getValue()))
                        .collect(Collectors.toList())
        );
    }

    private String getConstraint(EmployeeSearchParameters parameter, int index) {
        return parameter.getColumnName() + " = $" + index;
    }
}
