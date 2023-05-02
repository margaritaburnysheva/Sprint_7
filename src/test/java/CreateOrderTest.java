import order.Order;
import order.OrderClient;
import order.OrderData;
import order.Track;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private List<String> color;
    private Track track;
    private OrderClient orderClient;

    public CreateOrderTest(List<String> color) {
       this.color = color;
    }
//
    @Parameterized.Parameters(name="Тестовые данные: {0}")
    public static Object[][] getOrderData(){
        return new Object[][] {
                new List[]{List.of("BLACK")},
                new List[]{List.of("GREY")},
                new List[]{List.of("BLACK","GREY")},
                new List[]{List.of()},
        };
    }

    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Before
    public void setUp(){
       orderClient=new OrderClient();
    }

    @After
    public void clearData(){
       OrderClient.deleteOrder(track);
   }

    @Test
    @DisplayName("Check create order with or without color")
    @Description("Checking of successful creation of the order with/without scooter color")
    public void checkCreateOrderWithOrWithoutColor(){
        Order order = new Order(OrderData.firstName,
                                OrderData.lastName,
                                OrderData.address,
                                OrderData.metroStation,
                                OrderData.phone,
                                OrderData.rentTime,
                                OrderData.deliveryDate,
                                OrderData.comment,
                                color);

       Response response= OrderClient.createOrder(order);
       response
                .then()
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("track",notNullValue());

       track=response
                  .body()
                  .as(Track.class);
   }
}
