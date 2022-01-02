package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethod;

public class Basics {

	public static void main(String[] args) {
	
	// Validate if Add Place API is working as expected //
		//Add place-> Update Place with New Address -> Get Place to validate if New address is present in response
		
		//Given - All input details 
		//When - Submit the API -resource,http method
		//Then - Validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//Add Place
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.addPlace()).when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println("Respose for add place : "+response);
		JsonPath jsp = new JsonPath(response); //for parsing JSON
		String placeId = jsp.getString("place_id");
		System.out.println(placeId);
		
		//Update Place
		String newAddress = "Summer Walk, Africa";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeId+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}").when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get Place
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jsp1 = ReusableMethod.rawToJson(getPlaceResponse);
		String actualAddress = jsp1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
	}

}
