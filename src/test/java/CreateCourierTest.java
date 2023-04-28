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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateCourierTest {
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
    @DisplayName("Create courier with valid data")
    @Description("Checking of successful creation of a courier with valid data")
    public void createCourierWithValidData(){
        Courier courier= CourierDataGenerator.getRandom();

        courierClient.createCourier(courier)
                .assertThat()
                .statusCode(201)
                .and()
                .assertThat()
                .body("ok",is(true));

        courierId = courierClient.loginCourier(CourierCredentials.from(courier))
                .assertThat()
                .body("id",notNullValue())
                .extract().path("id");
    }
}
