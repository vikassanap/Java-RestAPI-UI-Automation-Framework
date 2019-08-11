package com.project.qa.core.helpers;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.project.qa.core.webdriver.WebDriverManager.driver;

/**
 * @author : Vikas S.
 * @since : 05-06-2019, Wed
 **/

public interface AlertHelper {

    Logger LOGGER = LoggerFactory.getLogger(AlertHelper.class);

    default Alert getAlert(WebDriver driver) {
        LOGGER.info("alert test: " + driver.switchTo().alert().getText());
        return driver.switchTo().alert();
    }

    default void acceptAlert(WebDriver driver) {
        getAlert(driver).accept();
        LOGGER.info("accept Alert is done...");
    }

    default void dismissAlert(WebDriver driver) {
        getAlert(driver).dismiss();
        LOGGER.info("dismiss Alert is done...");
    }

    default String getAlertText(WebDriver driver) {
        String text = getAlert(driver).getText();
        LOGGER.info("alert text: " + text);
        return text;
    }

    default boolean isAlertPresent(WebDriver driver) {
        try {
            driver.switchTo().alert();
            LOGGER.info("alert is present");
            return true;
        } catch (NoAlertPresentException e) {
            LOGGER.info(String.valueOf(e.getCause()));
            return false;
        }
    }

    default void acceptAlertIfPresent(WebDriver driver) {
        if (isAlertPresent(driver)) {
            acceptAlert(driver);
        } else {
            LOGGER.info("Alert is not present..");
        }
    }

    default void dismissAlertIfPresent(WebDriver driver) {
        if (isAlertPresent(driver)) {
            dismissAlert(driver);
        } else {
            LOGGER.info("Alert is not present..");
        }
    }

    default void acceptPrompt(String text) {
        if (isAlertPresent(driver)) {
            Alert alert = getAlert(driver);
            alert.sendKeys(text);
            alert.accept();
            LOGGER.info("alert text: " + text);
        }
    }
}
