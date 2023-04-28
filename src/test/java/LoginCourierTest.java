import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.CourierDataGenerator;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
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

    @Test
    @DisplayName("Courier login return 200 and Id")
    @Description("Checking of status code 200 and courier id when login of a courier with valid data")
    public void courierLoginReturn200AndId(){
        Courier courier= CourierDataGenerator.getRandom();
        courierClient.createCourier(courier);

        courierClient.loginCourier(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(200)
                .and()
                .assertThat()
                .body("id",notNullValue());

        courierId = courierClient.loginCourier(CourierCredentials.from(courier))
                .assertThat()
                .body("id",notNullValue())
                .extract().path("id");

        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Courier non-existent login return 404 and NotFound")
    @Description("Checking of status code 404 and error message when login of a non-existent courier")
    public void courierNonexistentLoginReturn404AndNotFound(){
       Courier courier= CourierDataGenerator.getRandom();

       courierClient.loginCourier(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(404)
                .and()
                .assertThat()
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Courier login without Login return 400 and message")
    @Description("Checking of status code 400 and error message when login without Login")
    public void courierLoginWithoutLoginReturn400AndMessage(){
        Courier courier= new Courier(CourierCredentials.randomLogin, CourierCredentials.randomPassword, CourierCredentials.randomFirstName);
        courierClient.createCourier(courier);

        Courier secondCourier=new Courier("",CourierCredentials.randomPassword,CourierCredentials.randomFirstName);

        courierClient.loginCourier(CourierCredentials.from(secondCourier))
                .assertThat()
                .statusCode(400)
                .and()
                .assertThat()
                .body("message",is("Недостаточно данных для входа"));

        courierId = courierClient.loginCourier(CourierCredentials.from(courier))
                .assertThat()
                .body("id",notNullValue())
                .extract().path("id");

        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Courier login without Password return 400 and message")
    @Description("Checking of status code 400 and error message when login without Password")
    public void courierLoginWithoutPasswordReturn400AndMessage(){
        Courier courier= new Courier(CourierCredentials.randomLogin, CourierCredentials.randomPassword, CourierCredentials.randomFirstName);
        courierClient.createCourier(courier);

        Courier secondCourier=new Courier(CourierCredentials.randomLogin,"",CourierCredentials.randomFirstName);

        courierClient.loginCourier(CourierCredentials.from(secondCourier))
                .assertThat()
                .statusCode(400)
                .and()
                .assertThat()
                .body("message",is("Недостаточно данных для входа"));

        courierId = courierClient.loginCourier(CourierCredentials.from(courier))
                .assertThat()
                .body("id",notNullValue())
                .extract().path("id");

        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Courier login with wrong Login return 404 and message")
    @Description("Checking of status code 404 and error message when login with wrong Login")
    public void courierLoginWithWrongLoginReturn404AndMessage(){
        Courier courier= new Courier(CourierCredentials.randomLogin, CourierCredentials.randomPassword, CourierCredentials.randomFirstName);
        courierClient.createCourier(courier);

        Courier secondCourier=new Courier(CourierCredentials.randomString,CourierCredentials.randomPassword,CourierCredentials.randomFirstName);

        courierClient.loginCourier(CourierCredentials.from(secondCourier))
                .assertThat()
                .statusCode(404)
                .and()
                .assertThat()
                .body("message",is("Учетная запись не найдена"));

        courierId = courierClient.loginCourier(CourierCredentials.from(courier))
                .assertThat()
                .body("id",notNullValue())
                .extract().path("id");

        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Courier login with wrong Password return 404 and message")
    @Description("Checking of status code 404 and error message when login with wrong Password")
    public void courierLoginWithWrongPasswordReturn404AndMessage(){
        Courier courier= new Courier(CourierCredentials.randomLogin, CourierCredentials.randomPassword, CourierCredentials.randomFirstName);
        courierClient.createCourier(courier);

        Courier secondCourier=new Courier(CourierCredentials.randomLogin,CourierCredentials.randomString,CourierCredentials.randomFirstName);

        courierClient.loginCourier(CourierCredentials.from(secondCourier))
                .assertThat()
                .statusCode(404)
                .and()
                .assertThat()
                .body("message",is("Учетная запись не найдена"));

        courierId = courierClient.loginCourier(CourierCredentials.from(courier))
                .assertThat()
                .body("id",notNullValue())
                .extract().path("id");

        courierClient.deleteCourier(courierId);
    }
}
