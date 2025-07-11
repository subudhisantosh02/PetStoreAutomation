package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payloads.User;
import io.restassured.response.Response;

public class UserTests2 {
	

	
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setupData()
	{
		faker=new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		
		logger=LogManager.getLogger(this.getClass());
		
		
		/*
		 * Examples:- logger.info("Starting REST Assured API tests...");
		 * logger.debug("Sending request to endpoint...");
		 * logger.error("Error occurred during API execution.");
		 */
		
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("*********Creating User*********************");
		Response response=UserEndPoints2.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("**********User is created************");
	}
	
	@Test(priority=2)
	public void testGetUserByName()
	{
		logger.info("*********Reading User Info*********************");
		Response response=UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("********* User Info is displayed*********************");
	}
	
	@Test(priority=3)
	public void testUpdateUserByName()
	{
		//update the data using payload
		logger.info("*********Updating User Info*********************");
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		
		Response response=UserEndPoints2.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//check the data after update
		
		Response responseAfterUpdate=UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
		logger.info("*********Updated User Info*********************");
		
	}
	
	//@Test(priority=4)
	public void testDeletUsereByName()
	{
		
		logger.info("*********Deleting User Info*********************");
		Response response=UserEndPoints2.deleteUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//check the data after Delete
		
		
		/*
		 * Response
		 * responseAfterDelete=UserEndPoints2.readUser(this.userPayload.getUsername());
		 * response.then().log().all();
		 * Assert.assertEquals(responseAfterDelete.getStatusCode(),404);
		 */
		 
		  logger.info("*********Deleted User Info*********************");		
		
	}



}
