package com.project.qa.api.tests;
/**
 * @author : Vikas S.
 * @since : 10-08-2019, Sat
 **/
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.qa.api.base.BaseTest;
import com.project.qa.api.apis.GetUser;
import com.project.qa.api.apis.GetUsers;
import com.project.qa.api.dataproviders.CSVAnnotation;
import com.project.qa.api.dataproviders.GenericDataProvider;
import com.project.qa.api.pojos.Data;
import com.project.qa.api.pojos.User;
import com.project.qa.api.pojos.Users;
import com.project.qa.api.utils.HeaderConstants;
import com.project.qa.api.utils.StatusCodes;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetUserTest extends BaseTest {

    @Test(description = "Get single user information", dataProvider = "dataproviderForTestCase", dataProviderClass = GenericDataProvider.class)
    @CSVAnnotation.CSVFileParameters(path = "src\\test\\resources\\testdata\\getuser.csv", delimiter = ",")
    public void getUser(int rowNo, Map<String, String> inputData) throws Exception {
        // Get user details for given id
        GetUser getUser = new GetUser(appBaseURI);
        getUser.setUserId(Integer.parseInt(inputData.get("id")));
        getUser.setExpectedStatusCode(StatusCodes.OK);
        getUser.setContentType(HeaderConstants.CONTENT_TYPE_JSON);
        getUser.perform();
        User user = getUser.getAPIResponseAsPOJO(User.class);
        Data data = user.getData();

        // Compare expected and actual user details
        Assert.assertEquals(data.getId(), Integer.parseInt(inputData.get("id")));
        Assert.assertEquals(data.getEmail(), inputData.get("email"));
        Assert.assertEquals(data.getFirstName(), inputData.get("first_name"));
        Assert.assertEquals(data.getLastName(), inputData.get("last_name"));
        Assert.assertEquals(data.getAvatar(), inputData.get("avatar"));
    }

    @Test(description = "Get single user information using nonexisting id", enabled = true)
    public void getUserWithInvalidID() throws Exception {
        GetUser getUser = new GetUser(appBaseURI);
        getUser.setUserId(23);
        getUser.setExpectedStatusCode(StatusCodes.NOT_FOUND);
        getUser.setContentType(HeaderConstants.CONTENT_TYPE_JSON);
        getUser.perform();

        User user = getUser.getAPIResponseAsPOJO(User.class);
        Data data = user.getData();

        Assert.assertEquals(data, null);
        Assert.assertTrue(user.getAdditionalProperties().isEmpty());
    }

    @DataProvider(name = "JSONFilesProvider")
    public Object[][] dataProviderMethod() {
        return new Object[][]{{1, "src\\test\\resources\\testdata\\userspage1.json"}, {2, "src\\test\\resources\\testdata\\userspage2.json"}};
    }

    @Test(description = "Get page wise user information list", dataProvider = "JSONFilesProvider",
            enabled = true)
    public void getUsers(long pageNumber, String exepectedJSONFile) throws Exception {
        // Expected JSON response
        Users expectedUsers = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try (FileInputStream fileInputStream = new FileInputStream(exepectedJSONFile)) {
            expectedUsers = objectMapper.readValue(fileInputStream, Users.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Get API response
        GetUsers getUsers = new GetUsers(appBaseURI);
        getUsers.setContentType(HeaderConstants.CONTENT_TYPE_JSON);
        getUsers.setPageNumber(pageNumber);
        getUsers.setExpectedStatusCode(StatusCodes.OK);
        getUsers.perform();

        //Verify API response metadata
        Users users = getUsers.getAPIResponseAsPOJO(Users.class);
        Assert.assertEquals(users.getPage(), expectedUsers.getPage());
        Assert.assertEquals(users.getTotal(), expectedUsers.getTotal());
        Assert.assertEquals(users.getTotalPages(), expectedUsers.getTotalPages());
        Assert.assertEquals(users.getPerPage(), expectedUsers.getPerPage());

        //Verify expected and actual responses
        List<Data> data = users.getData();
        List<Data> expectedData = expectedUsers.getData();
        Assert.assertEquals(data.size(), expectedData.size());
        Assert.assertEquals(users.toString(), expectedUsers.toString());
    }

    @Test(description = "Get users with delayed response", enabled = true)
    public void getDelayedResponse() throws Exception {
        //Read expected JSON from test data file
        Users expectedUsers = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try (FileInputStream fileInputStream = new FileInputStream("src\\test\\resources\\testdata\\delayedresponse.json")) {
            expectedUsers = objectMapper.readValue(fileInputStream, Users.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Get actual JSON response from API call
        GetUsers getUsers = new GetUsers(appBaseURI);
        getUsers.setExpectedStatusCode(StatusCodes.OK);
        getUsers.setContentType(HeaderConstants.CONTENT_TYPE_JSON);
        getUsers.setDelay(2);
        getUsers.perform();

        //Verify API response metadata
        Users users = getUsers.getAPIResponseAsPOJO(Users.class);
        Assert.assertEquals(users.getPage(), expectedUsers.getPage());
        Assert.assertEquals(users.getTotal(), expectedUsers.getTotal());
        Assert.assertEquals(users.getTotalPages(), expectedUsers.getTotalPages());
        Assert.assertEquals(users.getPerPage(), expectedUsers.getPerPage());

        //Verify expected and actual responses
        List<Data> data = users.getData();
        List<Data> expectedData = expectedUsers.getData();
        Assert.assertEquals(data.size(), expectedData.size());
        Assert.assertEquals(users.toString(), expectedUsers.toString());
        System.out.println("Response time taken: " + getUsers.getResponseTime());
    }
}
