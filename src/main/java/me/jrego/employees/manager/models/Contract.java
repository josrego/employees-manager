package me.jrego.employees.manager.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Contract {
    private Type type;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;

    public enum Type {
        FULL_TIME_PERMANENT,
        FULL_TIME_FIXED_TERM,
        PART_TIME_FIXED_TERM
    }
}
