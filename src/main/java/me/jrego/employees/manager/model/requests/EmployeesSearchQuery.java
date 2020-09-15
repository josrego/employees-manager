package me.jrego.employees.manager.model.requests;

import io.vertx.mutiny.sqlclient.Tuple;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class EmployeesSearchQuery {

    Map<EmployeeSearchParameter, String> searchParameters;
    EmployeeSearchSortParameter sortParameter;


    public EmployeesSearchQuery(String firstName, String lastName) {
        searchParameters = new LinkedHashMap<>();

        searchParameters.put(EmployeeSearchParameter.FIRST_NAME, firstName);
        searchParameters.put(EmployeeSearchParameter.LAST_NAME, lastName);
    }

    public EmployeesSearchQuery(String firstName, String lastName, String expirationDate) {
        this(firstName, lastName);
        searchParameters.put(EmployeeSearchParameter.CONTRACT_EXPIRATION_DATE, expirationDate);
    }

    public void setSortParameter(EmployeeSearchSortParameter.OrderBy orderBy,
                                 EmployeeSearchSortParameter.Direction direction) {
        this.sortParameter = new EmployeeSearchSortParameter(orderBy, direction);
    }

    public String getConstrains() {
        AtomicInteger i = new AtomicInteger(1);

        return searchParameters.entrySet()
                .stream()
                .filter(entry -> StringUtils.isNotEmpty(entry.getValue()))
                .map(entry -> getConstraint(entry.getKey(), i.getAndIncrement()))
                .collect(Collectors.joining(" AND "));
    }

    public boolean hasNoParameters() {
        return searchParameters.values().stream().allMatch(StringUtils::isEmpty);
    }

    public Tuple getArgumentValues() {
        if (hasNoParameters()) {
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

    private String getConstraint(EmployeeSearchParameter parameter, int index) {
        return parameter.getColumnName() + " = $" + index;
    }
}