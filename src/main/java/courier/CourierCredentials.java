package courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierCredentials {
    private String login;
    private String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public static CourierCredentials from(Courier courier){
        return new CourierCredentials(courier.getLogin(),courier.getPassword());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String randomLogin= RandomStringUtils.randomAlphabetic(10);
    public static String randomPassword=RandomStringUtils.randomAlphabetic(10);
    public static String randomFirstName=RandomStringUtils.randomAlphabetic(10);
    public static String randomString=RandomStringUtils.randomAlphabetic(10);

    @Override
    public String toString() {
        return "CourierCredentials{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
