package me.jrego.employees.manager;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import me.jrego.employees.manager.controller.EmployeesController;
import me.jrego.employees.manager.model.Contract;
import me.jrego.employees.manager.model.Employee;
import me.jrego.employees.manager.resource.PostgresDatabaseResource;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
@TestHTTPEndpoint(EmployeesController.class)
@QuarkusTestResource(PostgresDatabaseResource.class)
public class EmployeesControllerTest {

    public static final String SAMPLE_FIST_NAME = "Jose";
    public static final String SAMPLE_LAST_NAME = "Rego";
    public static final int SAMPLE_AGE = 30;

    /**
     * CREATE EMPLOYEE
     */
    @Test
    public void testCreateEmployeeSuccessful() {
        testCreateEmployee(sampleEmployee());
    }

    @Test
    public void testCreateEmployeeBadRequest() {
        Employee sampleEmployee = sampleEmployee();
        sampleEmployee.setFirstName(null);

        given().when().contentType(ContentType.JSON)
                .body(sampleEmployee)
                .post().then()
                .statusCode(400);

        sampleEmployee.setFirstName(SAMPLE_FIST_NAME);
        sampleEmployee.setLastName(null);

        given().when().contentType(ContentType.JSON)
                .body(sampleEmployee)
                .post().then()
                .statusCode(400);

        sampleEmployee.setLastName(SAMPLE_LAST_NAME);
        sampleEmployee.setAge(-1);

        given().when().contentType(ContentType.JSON)
                .body(sampleEmployee)
                .post().then()
                .statusCode(400);

        sampleEmployee.setAge(SAMPLE_AGE);
        sampleEmployee.getContract().setExpirationDate(LocalDate.EPOCH);

        given().when().contentType(ContentType.JSON)
                .body(sampleEmployee)
                .post().then()
                .statusCode(400);
    }

    /**
     * FIND EMPLOYEES
     */
    @Test
    public void testFindEmployeesNoFilter() {
        Employee employee_1 =
                new Employee("Lebron", "James", 35,
                        new Contract(LocalDate.now().plus(3, ChronoUnit.DAYS)));
        Employee employee_2 =
                new Employee("Giannis", "Antetokounmpo", 25,
                        new Contract(LocalDate.now().plus(4, ChronoUnit.DAYS)));
        Employee employee_3 =
                new Employee("Kevin", "Durant", 31,
                        new Contract(LocalDate.now().plus(3, ChronoUnit.DAYS)));
        testCreateEmployee(employee_1);
        testCreateEmployee(employee_2);
        testCreateEmployee(employee_3);

        given().when()
                .get().then().statusCode(200).assertThat()
                .body("firstName",
                        hasItems(employee_1.getFirstName(),
                                employee_2.getFirstName(),
                                employee_3.getFirstName()))
                .body("lastName",
                        hasItems(employee_1.getLastName(),
                                employee_2.getLastName(),
                                employee_3.getLastName()))
                .body("age",
                        hasItems(employee_1.getAge(),
                                employee_2.getAge(),
                                employee_3.getAge()))
                .body("contract.expirationDate",
                        hasItems(employee_1.getContract().getFormattedExpirationDate(),
                                employee_2.getContract().getFormattedExpirationDate(),
                                employee_3.getContract().getFormattedExpirationDate())
                );
    }

    @Test
    public void testFindEmployeesWithFirstNameFilter() {
        Employee employee =
                new Employee("James", "Harden", 31,
                        new Contract(LocalDate.now().plus(40, ChronoUnit.DAYS)));
        testCreateEmployee(employee);

        Map<Object, Object> responseFirstItem = given().when()
                .queryParam("firstName", employee.getFirstName())
                .get().then().statusCode(200).assertThat()
                .body("$", hasSize(1))
                .extract().jsonPath().getMap("[0]");

        assertFindItemList(employee, responseFirstItem);
    }

    @Test
    public void testFindEmployeesWithLastNameFilter() {
        Employee employee =
                new Employee("Stephen", " Curry", 32,
                        new Contract(LocalDate.now().plus(40, ChronoUnit.DAYS)));
        testCreateEmployee(employee);

        Map<Object, Object> responseFirstItem = given().when()
                .queryParam("lastName", employee.getLastName())
                .get().then().statusCode(200).assertThat()
                .body("$", hasSize(1))
                .extract().jsonPath().getMap("[0]");

        assertFindItemList(employee, responseFirstItem);
    }

    @Test
    public void testFindEmployeesWithExpirationDateFilter() {
        Employee employee =
                new Employee("Russell", " Westbrook", 31,
                        new Contract(LocalDate.now().plus(40, ChronoUnit.DAYS)));
        testCreateEmployee(employee);

        Map<Object, Object> responseFirstItem = given().when()
                .queryParam("contractExpirationDate", employee.getContract().getExpirationDate())
                .get().then().statusCode(200).assertThat()
                .body("$", hasSize(1))
                .extract().jsonPath().getMap("[0]");

        assertFindItemList(employee, responseFirstItem);
    }

    /**
     * FIND EMPLOYEES ORDER BY CONTRACT EXPIRATION DATE
     */

    private void testCreateEmployee(Employee employee) {
        given().when().contentType(ContentType.JSON)
                .body(employee)
                .post().then()
                .statusCode(201).assertThat()
                .body("firstName", is(employee.getFirstName()))
                .body("lastName", is(employee.getLastName()))
                .body("age", is(employee.getAge()))
                .body("contract.expirationDate", is(
                        employee.getContract().getFormattedExpirationDate()));
    }

    private void assertFindItemList(Employee employee, Map<Object, Object> responseFirstItem) {
        assert responseFirstItem.get("firstName").equals(employee.getFirstName());
        assert responseFirstItem.get("lastName").equals(employee.getLastName());
        assert ((Map) responseFirstItem.get("contract")).get("expirationDate")
                .equals(employee.getContract().getFormattedExpirationDate());
    }

    private Employee sampleEmployee() {
        return new Employee(SAMPLE_FIST_NAME, SAMPLE_LAST_NAME, SAMPLE_AGE,
                new Contract(LocalDate.now().plus(5, ChronoUnit.DAYS)));
    }
}