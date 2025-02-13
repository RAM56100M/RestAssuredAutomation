package api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;

public class Basics {

    public static void main(String[] args) {

        // Set Base URI
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        // Send API request
        String response = given()
            .queryParam("key", "qaclick123") // ✅ Fixed: Changed "Key" to "key"
            .header("Content-Type", "application/json")
            .body(Payload.addPlace())
        .when()
            .post("maps/api/place/add/json")
        .then()
            .log().all()
            .assertThat().statusCode(200)
            .header("server", "Apache/2.4.52 (Ubuntu)")
            .extract().response().asString();

        // Print the full response
        System.out.println("Response: " + response);

        // ✅ Check if response is empty before parsing
        if (response.trim().isEmpty()) {
            System.out.println("⚠️ Response body is empty. Skipping JSON parsing.");
        } else {
            JsonPath js = new JsonPath(response);
            String PlaceId = js.getString("place_id"); // ✅ Fixed: Changed "place-id" to "place_id"
            System.out.println("Extracted Place ID: " + PlaceId);
            
            //Update Place
            
            String newAddress= "Pune, India";
            given().log().all().queryParam("key","qaclick123").header("Content-Type", "application/json").body("{\r\n"
            		+ "\"place_id\":\""+PlaceId+"\",\r\n"
            		+ "\"address\":\""+newAddress+"\",\r\n"
            		+ "\"key\":\"qaclick123\"\r\n"
            		+ "}").
            when().put("maps/api/place/update/json").
            then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
            
            //get place
            String getPlaceResponse= given().log().all().queryParam("key","qaclick123")
            .queryParam("place_id",PlaceId)
            .when().get("maps/api/place/get/json")
            .then().assertThat().log().all().statusCode(200).extract().response().asString();
            
            JsonPath jspath= new JsonPath(getPlaceResponse);
            String actualAddress=jspath.getString("address");
            System.out.println(actualAddress);
            Assert.assertEquals(newAddress, actualAddress);
            
        }
    }
}
