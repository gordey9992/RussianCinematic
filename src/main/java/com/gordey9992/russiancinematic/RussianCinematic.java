package com.gordey9992.russiancinematic;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

import com.gordey9992.russiancinematic.managers.CinematicManager;
import com.gordey9992.russiancinematic.managers.TelegramManager;
import com.gordey9992.russiancinematic.managers.ConfigManager;
import com.gordey9992.russiancinematic.commands.CinematicCommand;
import com.gordey9992.russiancinematic.commands.TelegramCommand;
import com.gordey9992.russiancinematic.listeners.PlayerJoinListener;
import com.gordey9992.russiancinematic.listeners.PlayerQuitListener;
import com.gordey9992.russiancinematic.listeners.ChatListener;

import java.util.logging.Logger;

public class RussianCinematic extends JavaPlugin {
    
    private static RussianCinematic instance;
    private CinematicManager cinematicManager;
    private TelegramManager telegramManager;
    private ConfigManager configManager;
    private Logger logger;
    
    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        
        // Красивое приветствие в консоль
        printWelcomeMessage();
        
        // Инициализация менеджеров
        configManager = new ConfigManager(this);
        cinematicManager = new CinematicManager(this);
        telegramManager = new TelegramManager(this);
        
        // Загрузка конфигов
        configManager.loadConfigs();
        
        // Регистрация команд
        getCommand("cinematic").setExecutor(new CinematicCommand(this));
        getCommand("telegram").setExecutor(new TelegramCommand(this));
        
        // Регистрация ивентов
        registerEvents();
        
        // Инициализация Telegram бота
        telegramManager.initialize();
        
        logger.info("✅ RussianCinematic успешно включен!");
        logger.info("🎭 Система катсцен: " + (cinematicManager.isEnabled() ? "✅" : "❌"));
        logger.info("🤖 Telegram интеграция: " + (telegramManager.isEnabled() ? "✅" : "❌"));
    }
    
    @Override
    public void onDisable() {
        if (telegramManager != null) {
            telegramManager.shutdown();
        }
        logger.info("🔴 RussianCinematic выключен");
    }
    
    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
    }
    
    private void printWelcomeMessage() {
        logger.info("╔══════════════════════════════════════╗");
        logger.info("║           🎬 RussianCinematic       ║");
        logger.info("║        Мощный плагин для катсцен    ║");
        logger.info("║          и Telegram интеграции      ║");
        logger.info("║                                      ║");
        logger.info("║     Сделано с ❤️ для русского       ║");
        logger.info("║         комьюнити Minecraft         ║");
        logger.info("╚══════════════════════════════════════╝");
        logger.info("🔧 Версия: " + getDescription().getVersion());
        logger.info("👤 Автор: " + getDescription().getAuthors());
    }
    
    public static RussianCinematic getInstance() {
        return instance;
    }
    
    public CinematicManager getCinematicManager() {
        return cinematicManager;
    }
    
    public TelegramManager getTelegramManager() {
        return telegramManager;
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
}
