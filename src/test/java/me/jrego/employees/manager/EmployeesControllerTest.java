package me.jrego.employees.manager;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import me.jrego.employees.manager.controller.EmployeesController;
import me.jrego.employees.manager.model.Contract;
import me.jrego.employees.manager.model.Employee;
import me.jrego.employees.manager.resource.PostgresDatabaseResource;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

@QuarkusTest
@TestHTTPEndpoint(EmployeesController.class)
@QuarkusTestResource(PostgresDatabaseResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeesControllerTest {

    public static final String SAMPLE_FIST_NAME = "Jose";
    public static final String SAMPLE_LAST_NAME = "Rego";
    public static final int SAMPLE_AGE = 30;

    public static Employee EMPLOYEE_1 =
            new Employee("Lebron", "James", 35,
                    new Contract(LocalDate.now().plus(60, ChronoUnit.DAYS)));
    public static Employee EMPLOYEE_2 =
            new Employee("Giannis", "Antetokounmpo", 25,
                    new Contract(LocalDate.now().plus(40, ChronoUnit.DAYS)));
    public static Employee EMPLOYEE_3 =
            new Employee("Kevin", "Durant", 31,
                    new Contract(LocalDate.now().plus(41, ChronoUnit.DAYS)));

    /**
     * CREATE EMPLOYEE
     */
    @Test
    @Order(1)
    public void testCreateEmployeeSuccessful() {
        testCreateEmployee(EMPLOYEE_1);
        testCreateEmployee(EMPLOYEE_2);
        testCreateEmployee(EMPLOYEE_3);
    }

    @Test
    public void testCreateEmployeeBadRequest() {
        Employee sampleEmployee = sampleEmployee();
        sampleEmployee.setFirstName(null);

        createEmployee(sampleEmployee).then()
                .statusCode(400);

        sampleEmployee.setFirstName(SAMPLE_FIST_NAME);
        sampleEmployee.setLastName(null);

        createEmployee(sampleEmployee).then()
                .statusCode(400);

        sampleEmployee.setLastName(SAMPLE_LAST_NAME);
        sampleEmployee.setAge(-1);

        createEmployee(sampleEmployee).then()
                .statusCode(400);

        sampleEmployee.setAge(SAMPLE_AGE);
        sampleEmployee.getContract().setExpirationDate(LocalDate.EPOCH);

        createEmployee(sampleEmployee).then()
                .statusCode(400);
    }

    /**
     * FIND EMPLOYEES
     */
    @Test
    public void testFindEmployeesNoFilter() {
        given().when()
                .get().then().statusCode(200).assertThat()
                .body("firstName",
                        hasItems(EMPLOYEE_1.getFirstName(),
                                EMPLOYEE_2.getFirstName(),
                                EMPLOYEE_3.getFirstName()))
                .body("lastName",
                        hasItems(EMPLOYEE_1.getLastName(),
                                EMPLOYEE_2.getLastName(),
                                EMPLOYEE_3.getLastName()))
                .body("age",
                        hasItems(EMPLOYEE_1.getAge(),
                                EMPLOYEE_2.getAge(),
                                EMPLOYEE_3.getAge()))
                .body("contract.expirationDate",
                        hasItems(EMPLOYEE_1.getContract().getFormattedExpirationDate(),
                                EMPLOYEE_2.getContract().getFormattedExpirationDate(),
                                EMPLOYEE_3.getContract().getFormattedExpirationDate())
                );
    }

    @Test
    public void testFindEmployeesWithFirstNameFilter() {
        Map<Object, Object> responseFirstItem = given().when()
                .queryParam("firstName", EMPLOYEE_1.getFirstName())
                .get().then().statusCode(200).assertThat()
                .body("size()", is(1))
                .extract().jsonPath().getMap("[0]");

        assertFindItemList(EMPLOYEE_1, responseFirstItem);
    }

    @Test
    public void testFindEmployeesWithLastNameFilter() {
        Map<Object, Object> responseFirstItem = given().when()
                .queryParam("lastName", EMPLOYEE_1.getLastName())
                .get().then().statusCode(200).assertThat()
                .body("size()", is(1))
                .extract().jsonPath().getMap("[0]");

        assertFindItemList(EMPLOYEE_1, responseFirstItem);
    }

    @Test
    public void testFindEmployeesWithExpirationDateFilter() {
        Map<Object, Object> responseFirstItem = given().when()
                .queryParam("contractExpirationDate", EMPLOYEE_1.getContract().getFormattedExpirationDate())
                .get().then().statusCode(200).assertThat()
                .body("size()", is(1))
                .extract().jsonPath().getMap("[0]");

        assertFindItemList(EMPLOYEE_1, responseFirstItem);
    }

    /**
     * FIND EMPLOYEES ORDER BY CONTRACT EXPIRATION DATE
     */
    @Test
    public void testFindEmployeesOrderByExpirationDate() {
        assertFindOrderByContractExpirationDateNoFilter();
        assertFindOrderByContractExpirationDateFilterByFirstName();
        assertFindOrderByContractExpirationDateFilterByLastName();
    }

    private void assertFindOrderByContractExpirationDateNoFilter() {
        given().when()
                .get("/orderBy/contractExpirationDate")
                .then().statusCode(200).assertThat()
                .body("firstName",
                        contains(EMPLOYEE_2.getFirstName(),
                                EMPLOYEE_3.getFirstName(),
                                EMPLOYEE_1.getFirstName()))
                .body("lastName",
                        contains(EMPLOYEE_2.getLastName(),
                                EMPLOYEE_3.getLastName(),
                                EMPLOYEE_1.getLastName()))
                .body("age",
                        contains(EMPLOYEE_2.getAge(),
                                EMPLOYEE_3.getAge(),
                                EMPLOYEE_1.getAge()))
                .body("contract.expirationDate",
                        contains(EMPLOYEE_2.getContract().getFormattedExpirationDate(),
                                EMPLOYEE_3.getContract().getFormattedExpirationDate(),
                                EMPLOYEE_1.getContract().getFormattedExpirationDate())
                );
    }

    private void assertFindOrderByContractExpirationDateFilterByFirstName() {
        given().when()
                .queryParam("firstName", EMPLOYEE_1.getFirstName())
                .get("/orderBy/contractExpirationDate")
                .then().statusCode(200).assertThat()
                .body("size()", is(1))
                .body("firstName",
                        hasItems(EMPLOYEE_1.getFirstName()))
                .body("lastName",
                        hasItems(EMPLOYEE_1.getLastName()))
                .body("age",
                        hasItems(EMPLOYEE_1.getAge()))
                .body("contract.expirationDate",
                        hasItems(EMPLOYEE_1.getContract().getFormattedExpirationDate())
                );
    }

    private void assertFindOrderByContractExpirationDateFilterByLastName() {
        given().when()
                .queryParam("lastName", EMPLOYEE_1.getLastName())
                .get("/orderBy/contractExpirationDate")
                .then().statusCode(200).assertThat()
                .body("size()", is(1))
                .body("firstName",
                        hasItems(EMPLOYEE_1.getFirstName()))
                .body("lastName",
                        hasItems(EMPLOYEE_1.getLastName()))
                .body("age",
                        hasItems(EMPLOYEE_1.getAge()))
                .body("contract.expirationDate",
                        hasItems(EMPLOYEE_1.getContract().getFormattedExpirationDate())
                );
    }

    private void testCreateEmployee(Employee employee) {
        createEmployee(employee).then()
                .statusCode(201).assertThat()
                .body("firstName", is(employee.getFirstName()))
                .body("lastName", is(employee.getLastName()))
                .body("age", is(employee.getAge()))
                .body("contract.expirationDate", is(
                        employee.getContract().getFormattedExpirationDate()));
    }

    private Response createEmployee(Employee employee) {
        return given().when().contentType(ContentType.JSON)
                .body(employee)
                .post();
    }

    private void assertFindItemList(Employee employee, Map<Object, Object> responseFirstItem) {
        assert responseFirstItem.get("firstName").equals(employee.getFirstName());
        assert responseFirstItem.get("lastName").equals(employee.getLastName());
        assert ((Map) responseFirstItem.get("contract")).get("expirationDate")
                .equals(employee.getContract().getFormattedExpirationDate());
    }

    private Employee sampleEmployee() {
        return new Employee(SAMPLE_FIST_NAME, SAMPLE_LAST_NAME, SAMPLE_AGE,
                new Contract(LocalDate.now().plus(50, ChronoUnit.DAYS)));
    }
}