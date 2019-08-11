package com.project.qa.core.rest;


import io.cucumber.datatable.dependency.com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author : Vikas S.
 * @since : 10-08-2019, Sat
 **/
public interface APITestDataReader {
    Logger LOGGER = LoggerFactory.getLogger(APITestDataReader.class);

    default List<Map<String, Object>> getServiceData(String value, Map<String, Object> modules) {
        ObjectMapper objectMapper = new ObjectMapper();
        return (List<Map<String, Object>>) objectMapper.convertValue(modules.get(value), List.class);
    }

    default String getURL(List<Map<String, Object>> service) {
        return (String) service.get(0).get("URL");
    }

    default Map getHeader(List<Map<String, Object>> service) {
        return (Map) service.get(2).get("Headers");
    }

    default String getBody(List<Map<String, Object>> service) {
        return (String) service.get(4).get("PostBody");
    }

    default int getStatusCode(List<Map<String, Object>> service) {
        return (int) service.get(1).get("StatusCode");
    }

    default Map getAssertion(List<Map<String, Object>> service) {
        return (Map) service.get(3).get("Assertions");
    }

    default String updateValueInBody(String body, String key, String value, int index) {
        LOGGER.info("Body before update: {}", body);
        JSONArray jsonArray = new JSONArray(body);
        JSONObject jsonObject = jsonArray.getJSONObject(index);
        jsonObject.put(key, Long.parseLong(value));
        body = jsonObject.toString();
        body = "[" + body + "]";
        LOGGER.info("Update body: {}", body);
        return body;
    }


}