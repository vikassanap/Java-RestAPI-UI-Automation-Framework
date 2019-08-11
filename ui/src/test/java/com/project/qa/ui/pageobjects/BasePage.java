package com.project.qa.ui.pageobjects;


import com.project.qa.core.helpers.*;
import org.openqa.selenium.WebElement;

/**
 * @author : Vikas S.
 * @since : 05-06-2019, Wed
 **/
public class BasePage implements ActionHelper, AlertHelper, AssertionHelper, DropDownHelper, FrameHelper, JavaScriptHelper, VerificationHelper, WaitHelper, WindowHelper, JQueryHelper {
    /**
     * Method to clear input box and set value
     *
     * @param webElement
     * @param value
     */
    void clearAndSet(WebElement webElement, String value) {
        webElement.clear();
        webElement.sendKeys(value);
    }
}
