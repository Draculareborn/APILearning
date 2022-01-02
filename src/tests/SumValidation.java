package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	//Verify if Sum of all Course prices matches with Purchase Amount
	
	@Test
	public void sumOfCourses()
	{
		int sum = 0;
		JsonPath jsp = new JsonPath(Payload.coursePrice());
		int countCourses = jsp.getInt("courses.size()");
		for(int i=0;i<countCourses;i++)
		{
			int price = jsp.getInt("courses["+i+"].price");
			int copies = jsp.getInt("courses["+i+"].copies");
			int amount = price * copies;
			System.out.println(amount);
			sum = sum + amount;
			
			
		}
		System.out.println("Sum of all courses price: " +sum);
		int purchaseAmount = jsp.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchaseAmount);
	}

}
