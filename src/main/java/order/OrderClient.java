package order;

import courier.Constants;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient extends Constants {
    private static final String CREATE_ORDER_URI="http://qa-scooter.praktikum-services.ru/api/v1/orders";
    private static final String CANCEL_ORDER_URI="http://qa-scooter.praktikum-services.ru/api/v1/orders/cancel";
    @Step("Create order")
    public static Response createOrder(Order order) {
        return given()
                .body(order)
                .when()
                .post(CREATE_ORDER_URI);
    }
    @Step("Delete order")
    public static Response deleteOrder(Track track){
        return given()
                .body(track)
                .when()
                .put(CANCEL_ORDER_URI);
    }
}
