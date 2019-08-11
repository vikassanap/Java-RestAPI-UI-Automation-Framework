package com.project.qa.ui.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * @author : Vikas S.
 * @since : 05-06-2019, Wed
 **/
public class GoogleSearchPage extends BasePage {
    EventFiringWebDriver driver;

    public GoogleSearchPage(EventFiringWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID_OR_NAME, using = "search_form_input_homepage")
    private WebElement queryInput;

    @FindBy(how = How.ID_OR_NAME, using = "search_button_homepage")
    private WebElement googleSearchButton;

    public void setQueryInput(String value) {
        clearAndSet(queryInput, value);
    }

    public void clickGoogleSearchButton() {
        googleSearchButton.click();
    }

}
