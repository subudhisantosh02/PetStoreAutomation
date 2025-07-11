package api.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payloads.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTests {
	//here we are taking data from excel sheet , not from faker librarry
	
	@Test(priority=1,dataProvider="Data",dataProviderClass=DataProviders.class)   //@DataProvider(name="Data")//if dataprovider in another class
	public void testPostUser(String userID,String userName,String fName,String lName, String userEmail, String pwd, String Ph)
	{
		User userPayload=new User();
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fName);
		userPayload.setLastName(lName);
		userPayload.setEmail(userEmail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(Ph);
				
		Response response=UserEndPoints.createUser(userPayload);
		//response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	//delete th euser which is created in the above method
	
	@Test(priority=2,dataProvider="UserNames",dataProviderClass=DataProviders.class)  //@DataProvider(name="UserNames")
	public void testDeleteUserByName(String uName)
	{
		Response response=UserEndPoints.deleteUser(uName);//uName
		Assert.assertEquals(response.getStatusCode(), 200);
	}

}
