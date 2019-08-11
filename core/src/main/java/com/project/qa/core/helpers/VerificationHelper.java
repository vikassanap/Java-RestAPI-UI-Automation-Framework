package com.project.qa.core.helpers;
/**
 * @author : Vikas S.
 * @since : 05-06-2019, Wed
 **/

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface VerificationHelper {

    Logger LOGGER = LoggerFactory.getLogger(VerificationHelper.class);

    default boolean isDisplayed(WebElement element) {
        try {
            element.isDisplayed();
            LOGGER.info("element is Displayed.." + element.getText());
            return true;
        } catch (Exception e) {
            LOGGER.error("element is not Displayed..", e.getCause());
            return false;
        }
    }

    default boolean isNotDisplayed(WebElement element) {
        try {
            element.isDisplayed();
            LOGGER.info("element is present.." + element.getText());
            return false;
        } catch (Exception e) {
            LOGGER.error("element is not present..");
            return true;
        }
    }

    default String readValueFromElement(WebElement element) {
        if (null == element) {
            LOGGER.info("WebElement is null..");
            return null;
        }
        boolean status = isDisplayed(element);
        if (status) {
            LOGGER.info("element text is .." + element.getText());
            return element.getText();
        } else {
            return null;
        }
    }

    default String getText(WebElement element) {
        if (null == element) {
            LOGGER.info("WebElement is null..");
            return null;
        }
        boolean status = isDisplayed(element);
        if (status) {
            LOGGER.info("element text is .." + element.getText());
            return element.getText();
        } else {
            return null;
        }
    }

    default String getTitle(WebDriver driver) {
        LOGGER.info("page title is: " + driver.getTitle());
        return driver.getTitle();
    }


}
