import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {
    private static final String ORDER_LIST_URI="http://qa-scooter.praktikum-services.ru/api/v1/orders";

    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("Get order list body")
    @Description("Checking of successful viewing of the order list")
    public void getOrderListBody() {
         given()
                .get(ORDER_LIST_URI)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .assertThat()
                .body("orders",notNullValue());
    }
}