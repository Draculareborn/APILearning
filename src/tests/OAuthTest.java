package tests;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OAuthTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		String[] courseTitlesWA = {"Selenium Webdriver Java","Cypress","Protractor"};
		
		
		//Get Code from user credential on login
		
//		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth/identifier?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&flowName=GeneralOAuthFlow");
//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("bhaskarreborn2.0");
//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//		Thread.sleep(3000);
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("dracula@_17");
//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//		Thread.sleep(4000);
//		String url = driver.getCurrentUrl();
//		
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWjv80AQTTYclQfxZplGs_Jqd52ScCs0xBM_JAnLFErz0J78byBvh7pj9yxy2el3cQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none#";
		String partialCode  = url.split("code=")[1];
		String code = partialCode.split("&scope")[0];
		System.out.println(code);
		
		
		
		//Get Access Token using code (exchange code)
		String accessTokenResponse = given().urlEncodingEnabled(false)
		.queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		//System.out.println(accessTokenResponse);
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");
		
		System.out.println(accessToken);
		
		//Actual Request to end point for courses
//		String response = given().log().all().contentType("application/json").
//				queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
//				.when()
//				.get("https://rahulshettyacademy.com/getCourse.php").asString();
//		System.out.println(response);
		
		//POJO DeSerialization example
		GetCourse gc = given().log().all().contentType("application/json").
				queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		//Example1
		List<Api> apiCourses = gc.getCourses().getApi();
		for(int i=0;i<apiCourses.size();i++)
		{
			if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		//Example2 - Get the course name of WebAutomation
		ArrayList<String> al = new ArrayList<String>(); 
		List<WebAutomation> webAutomationCourse = gc.getCourses().getWebAutomation();
		for(int j=0;j<webAutomationCourse.size();j++)
		{
			al.add(webAutomationCourse.get(j).getCourseTitle());
		}
		
		List<String> expectedList = Arrays.asList(courseTitlesWA);
		Assert.assertTrue(al.equals(expectedList));
		
		
	}

}
