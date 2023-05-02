package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {
    private static final String CREATE_COURIER_URI="http://qa-scooter.praktikum-services.ru/api/v1/courier";
    @Step("Create courier")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(getBaseReqSpec())
                .body(courier)
                .when()
                .post(CREATE_COURIER_URI)
                .then();
    }
    @Step("Login with courier credentials")
    public ValidatableResponse loginCourier(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseReqSpec())
                .body(courierCredentials)
                .when()
                .post(CREATE_COURIER_URI+"/login")
                .then();
    }
    @Step("Delete courier")
    public ValidatableResponse deleteCourier(int courierId){
        return given()
                .spec(getBaseReqSpec())
                .when()
                .delete(CREATE_COURIER_URI+"/"+courierId)
                .then();
    }
}
