package me.jrego.employees.manager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.mutiny.sqlclient.Row;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
public class Contract {

    @NotNull(message = "expiration date cannot be null")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer numberOfDaysLeft;

    public Contract(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @JsonIgnore
    public String getFormattedExpirationDate() {
        return expirationDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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

    @JsonIgnore
    @AssertTrue(message = "Cannot create contracts with expiration date in the past")
    public boolean isValidExpirationDate() {
        return expirationDate.isAfter(LocalDate.now());
    }
}
