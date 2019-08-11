package com.project.qa.core.helpers;

import com.project.qa.core.webdriver.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author : Vikas S.
 * @since : 05-06-2019, Wed
 **/
public interface JQueryHelper {

    default String jsGetValueByName(String name) {
        new JSWaiter().waitAllRequest();
        String value = (String) ((JavascriptExecutor) WebDriverManager.driver).executeScript("return $(\"*[name="+name+"]\").val();");
        return (value == null) ? null : value.trim();
    }

    default String jsGetValueByName(WebElement webElement){
        String name = webElement.getAttribute("name");
        new JSWaiter().waitAllRequest();
        String value = (String) ((JavascriptExecutor) WebDriverManager.driver).executeScript("return $(\"*[name="+name+"]\").val();");
        return (value == null) ? null : value.trim();
    }

    default void jsSetValueByName(String name, String value){
        new JSWaiter().waitAllRequest();
        ((JavascriptExecutor) WebDriverManager.driver).executeScript("$(\"*[name="+name+"]\").val(" + value + ");" );
    }

    default String jsGetValueBy(String attributeName, String attributeValue){
        new JSWaiter().waitAllRequest();
        String value = (String) ((JavascriptExecutor) WebDriverManager.driver).executeScript("return $(\"*["+attributeName+"="+attributeValue+"]\").val();", new Object[0]);
        return (value == null) ? null : value.trim();
    }



}
