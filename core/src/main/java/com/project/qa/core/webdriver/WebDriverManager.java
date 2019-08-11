package com.project.qa.core.webdriver;

import com.project.qa.core.helpers.ResourceHelper;
import com.project.qa.core.readers.ConfigFileReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author : Vikas S.
 * @since : 05-06-2019, Wed
 **/
public class WebDriverManager {
    private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverManager.class);
    public static WebDriver webDriver;
    public static EventFiringWebDriver driver;

    /**
     * This method returns web driver instance
     *
     * @return webdriver instance
     */
    public EventFiringWebDriver getDriver() {
        if (driver == null) driver = createDriver();
        return driver;
    }

    /**
     * Method to create web driver instance
     *
     * @return webdriver instance
     */
    private EventFiringWebDriver createDriver() {
        switch (new ConfigFileReader().getEnvironment()) {
            case LOCAL:
                driver = createLocalDriver();
                break;
            case REMOTE:
                driver = createRemoteDriver();
                break;
        }
        return driver;
    }

    /**
     * Method to create remote webdriver
     *
     * @return webdriver instance
     */
    private EventFiringWebDriver createRemoteDriver() {
        throw new RuntimeException("RemoteWebDriver is not yet implemented");
    }

    /**
     * Method to create local webdriver
     *
     * @return webdriver instance
     */
    private EventFiringWebDriver createLocalDriver() {
        ConfigFileReader configFileReader = new ConfigFileReader();
        switch (configFileReader.getBrowser()) {
            case FIREFOX:
                webDriver = new FirefoxDriver();
                break;
            case CHROME:
                System.setProperty(CHROME_DRIVER_PROPERTY, ResourceHelper.getResourcePath(configFileReader.getDriverPath()) + "chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.setProxy(null);
                webDriver = new ChromeDriver(chromeOptions);
                break;
            case INTERNETEXPLORER:
                webDriver = new InternetExplorerDriver();
                break;
        }

        driver = new EventFiringWebDriver(webDriver);
        driver.register(new EventListeners());

        if (configFileReader.getBrowserWindowSize())
            driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(configFileReader.getImplicitlyWait(), TimeUnit.SECONDS);
        return driver;
    }

    /**
     * Method to close webdriver instance
     */
    public void closeDriver() {
        driver.unregister(new EventListeners());
        LOGGER.info("closing web driver instance");
        driver.close();
        driver.quit();
        LOGGER.info("web driver instances closed successfully");
    }

    /**
     * Method to capture screenshot
     *
     * @param fileName
     * @throws IOException
     */
    public void captureScreen(String fileName) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filePath = ResourceHelper.getResourcePath(new ConfigFileReader().getScreenshotPath() + fileName + ".png");
        FileUtils.copyFile(scrFile, new File(filePath));
        LOGGER.info("screenshot has been saved at: {}", filePath);
    }

}
