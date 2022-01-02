package tests;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;

import files.ReusableMethod;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {
	
	public static void main(String[] args) {
		
		RestAssured.baseURI = "http://localhost:8040";
		
		//Login Scenario
		SessionFilter session = new SessionFilter();
		String sessionId = given().relaxedHTTPSValidation().header("Content-Type","application/json").body("{ \r\n" + 
				"    \"username\": \"bhaskarreborn\",\r\n" + 
				"     \"password\": \"password\" \r\n" + 
				" }").log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
		
		//Add Comment
		String expectedComment = "Hi I am comment";
		String addCommentResponse = given().pathParam("id", "10003").log().all().header("Content-Type","application/json").body("{\r\n" + 
				"    \"body\": \""+expectedComment+"\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}").log().all().filter(session).when().post("/rest/api/2/issue/{id}/comment").then().log().all()
		.assertThat().statusCode(201).extract().response().asString();
		JsonPath js = ReusableMethod.rawToJson(addCommentResponse);
		String commentId = js.getString("id");
	
		//Add Attachment
		given().header("X-Atlassian-Token","no-check").filter(session).pathParam("id", "10003").
		header("Content-Type","multipart/form-data").
		multiPart("file",new File("jira.txt")).when().
		post("/rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);
		
		//Get Issue
		String issueDetails = given().filter(session).pathParam("id", "10003")
				.queryParam("fields", "comment")
				.log().all().when().get("/rest/api/2/issue/{id}").then()
		.log().all().extract().response().asString();
		System.out.println(issueDetails);
		
		JsonPath js1 = ReusableMethod.rawToJson(issueDetails);
		int commentsCount = js1.getInt("fields.comment.comments.size()");
		for(int i=0;i<commentsCount;i++)
		{
			String commentIdIssue = js1.get("fields.comment.comments["+i+"].id").toString();
		//	System.out.println(commentIdIssue);
			if(commentIdIssue.equalsIgnoreCase(commentId))
			{
				String actualComment = js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(actualComment);
				Assert.assertEquals(actualComment, expectedComment);
			}
		}
		
	}

}
