package me.jrego.employees.manager.repository;

import io.smallrye.mutiny.Uni;
import me.jrego.employees.manager.model.Contract;

public interface ContactRepository {
    Uni<Long> create(Contract contract, Long employeeId);
}
