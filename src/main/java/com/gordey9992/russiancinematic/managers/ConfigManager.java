package com.gordey9992.russiancinematic.managers;

import com.gordey9992.russiancinematic.RussianCinematic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class ConfigManager {
    
    private final RussianCinematic plugin;
    private final Logger logger;
    
    private FileConfiguration config;
    private FileConfiguration messages;
    private File configFile;
    private File messagesFile;
    
    public ConfigManager(RussianCinematic plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }
    
    public void loadConfigs() {
        // Создаем папку для плагина если не существует
        plugin.getDataFolder().mkdirs();
        
        loadMainConfig();
        loadMessagesConfig();
        
        logger.info("✅ Конфигурации загружены");
    }
    
    private void loadMainConfig() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
            logger.info("📄 Создан новый config.yml");
        }
        
        config = YamlConfiguration.loadConfiguration(configFile);
        
        // Обновляем конфиг если нужно
        updateConfig();
    }
    
    private void loadMessagesConfig() {
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
            logger.info("📄 Создан новый messages.yml");
        }
        
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }
    
    private void updateConfig() {
        // Здесь можно добавить логику обновления конфига
        int configVersion = config.getInt("config-version", 1);
        
        if (configVersion < 2) {
            // Миграция на новую версию
            config.set("config-version", 2);
            saveConfig();
            logger.info("🔄 Конфиг обновлен до версии 2");
        }
    }
    
    public void reloadConfigs() {
        loadConfigs();
        logger.info("🔄 Конфигурации перезагружены");
    }
    
    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (Exception e) {
            logger.severe("❌ Ошибка сохранения config.yml: " + e.getMessage());
        }
    }
    
    public String getMessage(String path, String defaultValue) {
        String message = messages.getString(path, defaultValue);
        return message.replace("&", "§");
    }
    
    public FileConfiguration getConfig() {
        return config;
    }
    
    public FileConfiguration getMessages() {
        return messages;
    }
}
