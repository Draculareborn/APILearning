package tests;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonPrase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		JsonPath jsp = new JsonPath(Payload.coursePrice());
		
		//Print No of courses returned by API
		int countCourses = jsp.getInt("courses.size()");
		System.out.println("Number of Courses: "+ countCourses);
		System.out.println("------------------------------");
		
		//Print Purchase Amount
		int totalAmount = jsp.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount: "+ totalAmount);
		System.out.println("------------------------------");
		
		//Print Title of the first course
		String titleFirstCourse = jsp.get("courses[0].title");
		System.out.println("Title of the first course: "+ titleFirstCourse);
		System.out.println("------------------------------");
		
		//Print All course titles and their respective Prices
		for(int i=0;i<countCourses;i++)
		{
			String courseTitles = jsp.get("courses["+i+"].title");
			String coursesPrices = jsp.get("courses["+i+"].price").toString();
			System.out.println(courseTitles + " -> " + coursesPrices);
		}
		System.out.println("------------------------------");
		
		//Print no of copies sold by RPA Course
		for(int i=0;i<countCourses;i++)
		{
			String courseTitles = jsp.get("courses["+i+"].title");
			if(courseTitles.equalsIgnoreCase("RPA")) 
			{
				int copies = jsp.get("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
			
		}
		System.out.println("------------------------------");
		
		//Verify if Sum of all Course prices matches with Purchase Amount - Done in Sum Validation class
		
	}

}
