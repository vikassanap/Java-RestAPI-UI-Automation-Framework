package com.project.qa.core.rest;

import com.project.qa.core.helpers.AssertionHelper;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

/**
 * @author : Vikas S.
 * @since : 10-08-2019, Sat
 **/
public class RestClient implements APITestDataReader, APIUtility, AssertionHelper {

    public String createData(Map<String, String> headerMap, String url, Map<String, String> assertMap, List<Map<String, Object>> clientRecordPost) {
        String body = (String) clientRecordPost.get(3).get("postbody");
        Response response = post(url, headerMap, body);
        assertTrue(responseEquals(assertMap, response), "Response mismatch");
        return response.jsonPath().getString("resourceId");
    }
}
