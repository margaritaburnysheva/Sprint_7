package order;

import org.apache.commons.lang3.RandomStringUtils;

public class OrderData {
    public static String firstName= RandomStringUtils.randomAlphabetic(10);
    public static  String lastName= RandomStringUtils.randomAlphabetic(10);
    public static  String address= RandomStringUtils.randomAlphabetic(10);
    public static  String metroStation=RandomStringUtils.randomAlphabetic(10);;
    public static  String phone=RandomStringUtils.randomAlphabetic(10);
    public static  int rentTime= (int)(Math.random()*10);
    public static  String deliveryDate = (int)(Math.random()*2023)+"-"+(int)(Math.random()*12)+"-"+(int)(Math.random()*28);
    public static  String comment=RandomStringUtils.randomAlphabetic(10);
}
