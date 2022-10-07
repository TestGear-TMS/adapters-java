package io.test_gear.services;

import io.test_gear.clients.ClientConfiguration;
import io.test_gear.properties.AdapterConfig;

import java.util.Properties;

public class ConfigManager {
    private final Properties properties;

    public ConfigManager(Properties properties){
        this.properties = properties;
    }

    public AdapterConfig getAdapterConfig(){
        return new AdapterConfig(properties);
    }

    public ClientConfiguration getClientConfiguration(){
        return new ClientConfiguration(properties);
    }
}
