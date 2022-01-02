package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	
	@Test(enabled = false)
	public void addBook1()
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String addBookResponse = given().log().all().header("Content-Type","application/json").
		body(Payload.addBook()).
		when()
		.post("/Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		JsonPath js = ReusableMethod.rawToJson(addBookResponse);
		String id = js.get("ID");
		System.out.println(id);
		
	}
	
	@Test(enabled = false)
	public void addBook2()
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String addBookResponse = given().log().all().header("Content-Type","application/json").
		body(Payload.addBookGeneric("bcd","2926bs003")).
		when()
		.post("/Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		JsonPath js = ReusableMethod.rawToJson(addBookResponse);
		String id = js.get("ID");
		System.out.println(id);
		
	}
	
	@Test(enabled = true, dataProvider = "BooksData")
	public void addBook(String isbn, String aisle)
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String addBookResponse = given().log().all().header("Content-Type","application/json").
		body(Payload.addBookGeneric(isbn,aisle)).
		when()
		.post("/Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		JsonPath js = ReusableMethod.rawToJson(addBookResponse);
		String id = js.get("ID");
		System.out.println(id);
		
	}
	
	@DataProvider(name = "BooksData")
	public Object[][] getData()
	{
		// Array = collection of element
		//Multidimensional array = collection of arrays
		Object[][] bookData = new Object[][] {{"apia","0001"},{"apia","0002"},{"apia","0003"}};
		return bookData;
	}

}
