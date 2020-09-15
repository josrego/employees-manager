package me.jrego.employees.manager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.mutiny.sqlclient.Row;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
public class Contract {
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer numberOfDaysLeft;

    public Contract(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void calculateDaysUntilExpiration() {
        this.numberOfDaysLeft = Period.ofDays(
                        (int) ChronoUnit.DAYS.between(LocalDate.now(), expirationDate)).getDays();
    }

    public static Contract from(Row row) {
        if (row == null) {
            return null;
        }

        LocalDate expirationDate = row.getLocalDate("expiration_date");

        return new Contract(expirationDate);
    }
}
