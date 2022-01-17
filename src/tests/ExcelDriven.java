package tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import files.DataDriven;
import files.Payload;
import files.ReusableMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class ExcelDriven {
	
	@Test(enabled = false)
	public void addBook()
	{
		
		HashMap<String, Object> jsonAsMap = new HashMap<>();
		jsonAsMap.put("name", "RestAssured by Rahul");
		jsonAsMap.put("isbn", "rsrs");
		jsonAsMap.put("aisle", "0001");
		jsonAsMap.put("author", "Rahul");
		
		RestAssured.baseURI = "http://216.10.245.166";
		String addBookResponse = given().log().all().header("Content-Type","application/json").
		body(jsonAsMap).
		when()
		.post("/Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		JsonPath js = ReusableMethod.rawToJson(addBookResponse);
		String id = js.get("ID");
		System.out.println(id);
		
	}
	
	@Test(enabled = true)
	public void addBookN() throws IOException
	{
		DataDriven d = new DataDriven();
		ArrayList data = d.getData("RestAddBook", "BookAPI");
		
		HashMap<String, Object> jsonAsMap = new HashMap<>();
		jsonAsMap.put("name", data.get(1));
		jsonAsMap.put("isbn", data.get(2));
		jsonAsMap.put("aisle", data.get(3));
		jsonAsMap.put("author", data.get(4));
		
		RestAssured.baseURI = "http://216.10.245.166";
		String addBookResponse = given().log().all().header("Content-Type","application/json").
		body(jsonAsMap).
		when()
		.post("/Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		JsonPath js = ReusableMethod.rawToJson(addBookResponse);
		String id = js.get("ID");
		System.out.println(id);
		
	}

}
