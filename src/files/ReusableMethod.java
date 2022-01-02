package files;

import io.restassured.path.json.JsonPath;

public class ReusableMethod {
	
	public static JsonPath rawToJson(String response)
	{
		JsonPath jsp = new JsonPath(response);
		return jsp;
	}

}
