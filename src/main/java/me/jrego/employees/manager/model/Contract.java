package me.jrego.employees.manager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.vertx.mutiny.sqlclient.Row;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Contract {
    private Type type;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;

    public Contract(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        this.type = Type.FULL_TIME_FIXED_TERM;
    }

    public Contract(Type type, LocalDate expirationDate) {
        this.type = type;
        this.expirationDate = expirationDate;
    }

    public static Contract from(Row row) {
        if(row == null) {
            return null;
        }

        Type type = Type.valueOf(row.getString("type").toUpperCase());
        LocalDate expirationDate = row.getLocalDate("expiration_date");

        return new Contract(type, expirationDate);
    }

    public enum Type {
        FULL_TIME_PERMANENT,
        FULL_TIME_FIXED_TERM,
        PART_TIME_FIXED_TERM
    }
}
