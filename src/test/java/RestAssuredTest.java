import base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import json.request.Bookingdates;
import json.request.CreateBookingBody;
import json.response.CreateBookingResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RestAssuredTest extends BaseTest {

    @Test
    public void createBooking() {
        Bookingdates bookingdates = Bookingdates.builder()
                .checkin(getDate(0))
                .checkout(getDate(1))
                .build();

        CreateBookingBody createBookingBody = CreateBookingBody.builder()
                .firstname("Bob")
                .lastname("Unin")
                .totalprice(122)
                .depositpaid(false)
                .bookingdates(bookingdates)
                .additionalneeds("dsgfdgdfg")
                .build();

        Response response = RestAssured.given()
                .body(createBookingBody)
                .post();

        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.as(CreateBookingResponse.class).getBooking().getFirstname(), createBookingBody.getFirstname());
        Assert.assertEquals(response.as(CreateBookingResponse.class).getBooking().getLastname(), createBookingBody.getLastname());
    }

    @Test
    public void getAllBookingIds() {
        Response response = RestAssured.get();
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void updateBookingPatch() {
        Bookingdates bookingdates = Bookingdates.builder()
                .checkin(getDate(0))
                .checkout(getDate(1))
                .build();

        CreateBookingBody createBookingBody = CreateBookingBody.builder()
                .totalprice(900)
                .bookingdates(bookingdates)
                .build();

        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .header("Cookie", "token=" + getToken())
                .body(createBookingBody)
                .patch("/{id}", getRandomBookingIds());

        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.as(CreateBookingBody.class).getTotalprice(), createBookingBody.getTotalprice());
    }

    @Test
    public void updateBookingPut() {
        Bookingdates bookingdates = Bookingdates.builder()
                .checkin(getDate(0))
                .checkout(getDate(1))
                .build();

        CreateBookingBody createBookingBody = CreateBookingBody.builder()
                .firstname("James")
                .lastname("Brown")
                .totalprice(588)
                .depositpaid(false)
                .bookingdates(bookingdates)
                .additionalneeds("dsgfdgdfg")
                .build();

        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .header("Cookie", "token=" + getToken())
                .body(createBookingBody)
                .put("/" + getRandomBookingIds());

        response.prettyPrint();
        CreateBookingBody createBookingResponse = response.as(CreateBookingBody.class);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(createBookingResponse.getFirstname(), createBookingBody.getFirstname());
        Assert.assertEquals(createBookingResponse.getLastname(), createBookingBody.getLastname());
    }

    @Test
    public void deleteBooking() {
        int bookingID = getRandomBookingIds();
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .header("Cookie", "token=" + getToken())
                .delete("/" + bookingID);

        response.prettyPrint();

        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertFalse(isExistBookingID(bookingID));
    }
}