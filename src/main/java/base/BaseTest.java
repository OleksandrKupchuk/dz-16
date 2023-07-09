package base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import json.request.AuthBody;
import json.response.AuthResponse;
import json.response.GetBookingIdsResponse;
import org.testng.annotations.BeforeMethod;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class BaseTest {
    private String token;

    public String getToken() {
        return token;
    }

    @BeforeMethod
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .build();
        auth();
    }

    public void auth() {
        AuthBody authBody = AuthBody.builder()
                .username("admin")
                .password("password123")
                .build();


        Response response = RestAssured.given()
                .body(authBody)
                .post("https://restful-booker.herokuapp.com/auth");

        response.prettyPrint();

        token = response.as(AuthResponse.class).getToken();
    }

    public int getRandomBookingIds() {
        Random random = new Random();
        Response response = RestAssured.get();
        GetBookingIdsResponse[] bookingsID = response.as(GetBookingIdsResponse[].class);
        int id = bookingsID[random.nextInt(0, bookingsID.length - 1)].getBookingid();
        return id;
    }

    /**
     * Adds the number of days to today and returns the value in the format YYYY-MM-DD
     */
    public String getDate(int amountDays) {
        LocalDate today = LocalDate.now().plusDays(amountDays);
        String formattedDate = today.format(DateTimeFormatter.ISO_DATE);
        return formattedDate;
    }

    public boolean isExistBookingID(int id) {
        Response response = RestAssured.get();
        GetBookingIdsResponse[] bookingsID = response.as(GetBookingIdsResponse[].class);
        for (var bookingID : bookingsID) {
            if(bookingID.getBookingid() == id){
                return true;
            }
        }

        return false;
    }
}