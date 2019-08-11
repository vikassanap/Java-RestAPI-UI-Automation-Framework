package com.project.qa.core.readers;

import com.project.qa.core.enums.DriverType;
import com.project.qa.core.enums.EnvironmentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author : Vikas S.
 * @since : 05-06-2019, Wed
 **/
public class ConfigFileReader {
    static final Logger LOGGER = LoggerFactory.getLogger(ConfigFileReader.class);
    private Properties properties;

    public ConfigFileReader() {
        String filePath = ConfigFileReader.class.getClassLoader().getResource("config.properties").getPath();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("config.properties not found at " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get driver path
     *
     * @return driver executable path
     */
    public String getDriverPath() {
        String driverPath = properties.getProperty("driver.path");
        if (driverPath != null) return driverPath;
        else throw new RuntimeException("driver.path not specified in the config.properties file.");
    }

    /**
     * Method to get implicit wait time value
     *
     * @return implicit wait time value
     */
    public long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("driver.implicit.wait.timeout");
        if (implicitlyWait != null) return Long.parseLong(implicitlyWait);
        else
            throw new RuntimeException("driver.implicit.wait.timeout not specified in the config.properties file.");
    }

    /**
     * Method to get application url value
     *
     * @return application url value
     */
    public String getApplicationUrl() {
        String url = properties.getProperty("application.base.url");
        if (url != null) return url;
        else throw new RuntimeException("application.base.url not specified in the config.properties file.");
    }

    /**
     * Method to get browser type value
     *
     * @return browser type value
     */
    public DriverType getBrowser() {
        String browserName = properties.getProperty("browser");
        if (browserName == null || browserName.equals("chrome")) return DriverType.CHROME;
        else if (browserName.equalsIgnoreCase("firefox")) return DriverType.FIREFOX;
        else if (browserName.equals("iexplorer")) return DriverType.INTERNETEXPLORER;
        else
            throw new RuntimeException("Browser Name Key value in Configuration.properties is not matched : " + browserName);
    }

    /**
     * Method to get environment type value
     *
     * @return environment type value
     */
    public EnvironmentType getEnvironment() {
        String environmentName = properties.getProperty("environment");
        if (environmentName == null || environmentName.equalsIgnoreCase("local")) return EnvironmentType.LOCAL;
        else if (environmentName.equals("remote")) return EnvironmentType.REMOTE;
        else
            throw new RuntimeException("Environment Type Key value in Configuration.properties is not matched : " + environmentName);
    }

    /**
     * Method to get screenshot path value
     *
     * @return screenshot path value
     */
    public String getScreenshotPath() {
        String screenshotPath = properties.getProperty("driver.screenshot.path");
        if (screenshotPath != null) return screenshotPath;
        else throw new RuntimeException("driver.screenshot.path not specified in the config.properties file");
    }

    /**
     * Method to get browser window size value
     *
     * @return browser window size
     */
    public Boolean getBrowserWindowSize() {
        String windowSize = properties.getProperty("driver.window.maximize");
        if (windowSize != null) return Boolean.valueOf(windowSize);
        return true;
    }

    /**
     * Method to get default yaml file path
     *
     * @return yaml file path
     */
    public String getDefaultYAMLFilePath() {
        String yamlFilePath = properties.getProperty("default.yaml.file.path");
        if (yamlFilePath != null) return yamlFilePath;
        return yamlFilePath;
    }

    /**
     * Method to get default JSON file path
     *
     * @return JSON file path
     */
    public String getDefaultJSONFilePath() {
        String jsonFilePath = properties.getProperty("default.json.file.path");
        if (jsonFilePath != null) return jsonFilePath;
        return jsonFilePath;
    }

    /**
     * Method to get default excel file path
     *
     * @return Excel file path
     */
    public String getDefaultExcelFilePath() {
        String excelFilePath = properties.getProperty("default.excel.file.path");
        if (excelFilePath != null) return excelFilePath;
        return excelFilePath;
    }

}
