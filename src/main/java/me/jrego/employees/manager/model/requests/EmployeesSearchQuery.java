package me.jrego.employees.manager.model.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.jrego.employees.manager.model.BaseEmployee;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@ToString
public class EmployeesSearchQuery extends BaseEmployee {

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate contractExpirationDate;

    public EmployeesSearchQuery(String firstName, String lastName, Date expirationDate) {
        super(firstName, lastName);
        this.contractExpirationDate = expirationDate == null ?
                null :
                LocalDate.from(expirationDate.toInstant());
    }

    public String getContractExpirationDateAsString() {
        return contractExpirationDate == null ?
                null :
                contractExpirationDate.toString();
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(this.getFirstName())
                && StringUtils.isEmpty(this.getLastName())
                && contractExpirationDate == null;
    }

    public String getConstraints() {
        if (isEmpty()) {
            return null;
        }

        int constraintCount = 0;
        Stream.of(
                this.getFirstName(),
                this.getLastName(),
                this.getContractExpirationDateAsString()
        ).filter(StringUtils::isNotEmpty)
                .map(constraintQuery())
    }

    public Tuple getArgumentValues() {
        if (isEmpty()) {
            return Tuple.tuple();
        }

        return Tuple.tuple(
                Stream.of(
                        this.getFirstName(),
                        this.getLastName(),
                        this.getContractExpirationDateAsString()
                )
                        .filter(StringUtils::isNotEmpty)
                        .collect(Collectors.toList())
        );
    }
}
