package me.jrego.employees.manager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.vertx.mutiny.sqlclient.Row;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Contract {
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;

    public Contract(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public static Contract from(Row row) {
        if (row == null) {
            return null;
        }

        LocalDate expirationDate = row.getLocalDate("expiration_date");

        return new Contract(expirationDate);
    }
}
