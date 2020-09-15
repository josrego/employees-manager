package me.jrego.employees.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import me.jrego.employees.manager.resource.PostgresDatabaseResource;
import me.jrego.employees.manager.controller.EmployeeController;
import me.jrego.employees.manager.model.Contract;
import me.jrego.employees.manager.model.Employee;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@TestHTTPEndpoint(EmployeeController.class)
@QuarkusTestResource(PostgresDatabaseResource.class)
public class EmployeesControllerTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCreateEmployeeSuccessful() throws JsonProcessingException {
        Employee sampleEmployee = new Employee("José", "Rego", 30,
                new Contract(LocalDate.now().plus(5, ChronoUnit.DAYS)));

        given().when().contentType(ContentType.JSON)
                .body(sampleEmployee)
                .post()
                .then()
                .statusCode(200)
                .assertThat()
                .body("firstName", is(sampleEmployee.getFirstName()))
                .body("lastName", is(sampleEmployee.getLastName()))
                .body("age", is(sampleEmployee.getAge()))
                .body("contract.expirationDate", is(
                        sampleEmployee.getContract().getExpirationDate()
                                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

    }

}