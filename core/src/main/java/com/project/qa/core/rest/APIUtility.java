package com.project.qa.core.rest;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @author : Vikas S.
 * @since : 05-06-2019, Wed
 **/
public interface APIUtility {
    Logger LOGGER = LoggerFactory.getLogger(APIUtility.class);


    default Response get(String endPoint, Map<String, String> headerFields) {
        LOGGER.info("Get Url: {}", endPoint);
        LOGGER.info("Headers: {}", headerFields);
        Response response = given().headers(headerFields).when().get(endPoint).then().extract().response();
        LOGGER.info("Response body: {}", response.asString());
        return response;
    }

    default Response post(String endPoint, Map<String, String> headerFields, String postBody) {
        LOGGER.info("Post Url: {}", endPoint);
        LOGGER.info("Headers: {}", headerFields);
        Response response = given().headers(headerFields).body(postBody).when().post(endPoint).then().extract().response();
        LOGGER.info("Response body: {}", response.asString());
        return response;
    }

    default Response put(String endPoint, Map<String, String> headerFields, String postBody) {
        LOGGER.info("Put Url: {}", endPoint);
        LOGGER.info("Headers: {}", headerFields);
        Response response = given().headers(headerFields).body(postBody).when().put(endPoint).then().extract().response();
        LOGGER.info("Response body: {}", response.asString());
        return response;
    }

    default Response delete(String endPoint, Map<String, String> headerFields) {
        LOGGER.info("Delete Url: {}", endPoint);
        LOGGER.info("Headers: {}", headerFields);
        Response response = given().headers(headerFields).when().delete(endPoint).then().extract().response();
        LOGGER.info("Response body: {}", response.asString());
        return response;
    }

    default boolean responseEquals(Map<String, String> responseFields, Response response) {
        Iterator iterator = responseFields.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> expected = (Map.Entry) iterator.next();
            LOGGER.info("Expected: {} = {}", expected.getKey(), expected.getValue());
            Object responseValue = response.body().jsonPath().getJsonObject(expected.getKey()) == "" ? null : response.body().jsonPath().getJsonObject(expected.getKey());
            LOGGER.info("Actual: {} = {}", expected.getKey(), responseValue);
            if (responseNullCheck(expected, responseValue)) return false;
        }
        return true;
    }

    default boolean responseArrayListEquals(String jsonString, int index, Map<String, String> values) {
        JSONArray jsonArray = new JSONArray(jsonString);
        JSONObject jsonObject = jsonArray.getJSONObject(index);
        Iterator iterator = values.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Object> expected = (Map.Entry) iterator.next();
            LOGGER.info("Expected: {} = {}", expected.getKey(), expected.getValue());
            Object responseValue = jsonObject.get(expected.getKey()) == "" ? null : jsonObject.get(expected.getKey());
            LOGGER.info("Actual: {} = {}", expected.getKey(), responseValue);

            if (responseNullCheck(expected, responseValue)) return false;
        }
        return true;
    }

    default boolean responseNullCheck(Map.Entry<String, Object> expected, Object responseValue) {
        if ((responseValue != null && expected.getValue() != null) && (!responseValue.equals(expected.getValue()))) {
            LOGGER.error("Expected value: {} Actual value:{}", expected.getValue(), responseValue);
            return true;
        } else if ((responseValue == null && expected.getValue() != null) || (responseValue != null && expected.getValue() == null)) {
            LOGGER.error("Expected value: {} Actual value:{}", expected.getValue(), responseValue);
            return true;
        }
        return false;
    }

    default String getObjectValue(Response response, String jsonPath) {
        try {
            LOGGER.info("Value found on JSON path {} is {}", jsonPath, response.body().jsonPath().getJsonObject(jsonPath).toString());
            return response.body().jsonPath().getJsonObject(jsonPath).toString();
        } catch (Exception e) {
            LOGGER.error("Not able to find given JSON path {}", jsonPath);
            LOGGER.error(e.getMessage());
            throw e;
        }
    }


}

