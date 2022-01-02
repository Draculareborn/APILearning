package tests;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import files.ReusableMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class StaticJsonExternal {
	
	// Convert content of the JSON file to String for body method ->
	// Content of file can be converted into Byte -> Byte data to String
	
	@Test(enabled = false)
	public void addBook1() throws IOException
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String addBookResponse = given().log().all().header("Content-Type","application/json").
		body(new String(Files.readAllBytes(Paths.get("D:\\API Test Study\\Rahul Sir Course\\AddBookDetails.json")))).
		when()
		.post("/Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		JsonPath js = ReusableMethod.rawToJson(addBookResponse);
		String id = js.get("ID");
		System.out.println(id);
		
	}
	
	@Test(enabled = true)
	public void addBook2() throws IOException
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String addBookResponse = given().log().all().header("Content-Type","application/json").
		body(generateStringFromResource("D:\\API Test Study\\Rahul Sir Course\\AddBookDetails.json")).
		when()
		.post("/Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		JsonPath js = ReusableMethod.rawToJson(addBookResponse);
		String id = js.get("ID");
		System.out.println(id);
		
	}
	
	/**
	 * This method is used to convert JSON to byte and the byte to string
	 * @param jsonPath
	 * @return String bodyData
	 * @throws IOException
	 */
	public static String generateStringFromResource(String jsonPath) throws IOException
	{
		String bodyData = new String(Files.readAllBytes(Paths.get(jsonPath)));
		return bodyData;
	}

}
