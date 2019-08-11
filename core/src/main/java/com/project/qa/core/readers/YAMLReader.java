package com.project.qa.core.readers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

/**
 * @author : Vikas S.
 * @since : 05-06-2019, Wed
 **/
public class YAMLReader {
    static final Logger LOGGER = LoggerFactory.getLogger(YAMLReader.class);
    private String yamlFilePath;

    public YAMLReader() {
        String yamlFileName = new ConfigFileReader().getDefaultYAMLFilePath();
        yamlFilePath = this.getClass().getClassLoader().getResource(yamlFileName).getPath();
    }

    public YAMLReader(String yamlFileName) {
        yamlFilePath = this.getClass().getClassLoader().getResource(yamlFileName).getPath();
    }

    /**
     * Method reads YAML file and casts it into Map
     *
     * @return Map
     */
    public Map<String, Object> readYamlIntoMap() {
        Yaml yaml = new Yaml();
        Map<String, Object> yamlMap = null;
        try (Reader reader = new FileReader(yamlFilePath)) {
            yamlMap = yaml.load(reader);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return yamlMap;
    }

    public Map<String, Object> getYamlMaps() {
        Yaml yaml = new Yaml();
        Map<String, Object> yamlMaps = null;
        try (Reader yamlFile = new FileReader(yamlFilePath)) {
            yamlMaps = (Map<String, Object>) yaml.load(yamlFile);
        } catch (FileNotFoundException e) {
            LOGGER.error("Exception while reading YAML file: {}", e.getMessage());
        } catch (IOException e) {
            LOGGER.error("Exception while reading YAML file: {}", e.getMessage());
        }
        return yamlMaps;
    }
}
