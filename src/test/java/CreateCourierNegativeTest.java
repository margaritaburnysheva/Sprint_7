import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.CourierDataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateCourierNegativeTest {
    private CourierClient courierClient;
    private int courierId;

    @BeforeClass
    public static void globalSetUp(){
        RestAssured.filters(new RequestLoggingFilter(),new ResponseLoggingFilter());
    }

    @Before
    public void setUp(){
        courierClient=new CourierClient();
    }

    @After
    public void clearData(){
        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Create duplicate courier")
    @Description("Checking of status code 409 and error message when creating duplicate courier")
    public void createDuplicateCourier(){
        Courier courier= CourierDataGenerator.getRandom();
        courierClient.createCourier(courier);

        courierId = courierClient.loginCourier(CourierCredentials.from(courier))
                .assertThat()
                .body("id",notNullValue())
                .extract().path("id");

        courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .assertThat()
                .body("message",is("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Create courier without login")
    @Description("Checking of status code 400 and error message when creating courier without login")
    public void createCourierWithoutLogin(){
        Courier courier= CourierDataGenerator.getRandomWithoutLogin();

        courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message",is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier without password")
    @Description("Checking of status code 400 and error message when creating courier without password")
    public void createCourierWithoutPassword(){
        Courier courier= CourierDataGenerator.getRandomWithoutPassword();

        courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message",is("Недостаточно данных для создания учетной записи"));
    }
}
