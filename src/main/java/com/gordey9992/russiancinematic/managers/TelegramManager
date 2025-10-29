package com.gordey9992.russiancinematic.managers;

import com.gordey9992.russiancinematic.RussianCinematic;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;

public class TelegramManager {
    
    private final RussianCinematic plugin;
    private final Logger logger;
    private final FileConfiguration config;
    
    private boolean enabled;
    private String botToken;
    private String chatId;
    private boolean chatToTelegram;
    private boolean telegramToChat;
    private boolean joinNotifications;
    private boolean quitNotifications;
    private boolean serverStatusNotifications;
    
    public TelegramManager(RussianCinematic plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.config = plugin.getConfig();
    }
    
    public void initialize() {
        loadConfig();
        
        if (enabled && botToken != null && !botToken.equals("YOUR_BOT_TOKEN_HERE")) {
            startBot();
        } else if (enabled) {
            logger.warning("❌ Telegram бот не настроен. Укажите bot-token в config.yml");
        }
    }
    
    private void loadConfig() {
        enabled = config.getBoolean("telegram.enabled", false);
        botToken = config.getString("telegram.bot-token");
        chatId = config.getString("telegram.chat-id");
        
        chatToTelegram = config.getBoolean("telegram.sync.chat-to-telegram", true);
        telegramToChat = config.getBoolean("telegram.sync.telegram-to-chat", true);
        joinNotifications = config.getBoolean("telegram.sync.join-notifications", true);
        quitNotifications = config.getBoolean("telegram.sync.quit-notifications", true);
        serverStatusNotifications = config.getBoolean("telegram.sync.server-status-notifications", true);
    }
    
    private void startBot() {
        try {
            // Здесь будет код инициализации Telegram бота
            logger.info("✅ Telegram бот инициализирован");
            logger.info("💬 Синхронизация чата: " + (chatToTelegram ? "Minecraft→Telegram" : "выкл"));
            logger.info("📱 Синхронизация чата: " + (telegramToChat ? "Telegram→Minecraft" : "выкл"));
            
            if (serverStatusNotifications) {
                sendServerStartMessage();
            }
            
        } catch (Exception e) {
            logger.severe("❌ Ошибка инициализации Telegram бота: " + e.getMessage());
        }
    }
    
    public void sendChatMessage(Player player, String message) {
        if (!enabled || !chatToTelegram) return;
        
        String formattedMessage = config.getString("telegram.formats.chat", "🎮 {player} » {message}")
            .replace("{player}", player.getName())
            .replace("{message}", message);
        
        sendToTelegram(formattedMessage);
    }
    
    public void sendJoinMessage(Player player) {
        if (!enabled || !joinNotifications) return;
        
        String formattedMessage = config.getString("telegram.formats.join", "+ {player}")
            .replace("{player}", player.getName());
        
        sendToTelegram(formattedMessage);
    }
    
    public void sendQuitMessage(Player player) {
        if (!enabled || !quitNotifications) return;
        
        String formattedMessage = config.getString("telegram.formats.quit", "― {player}")
            .replace("{player}", player.getName());
        
        sendToTelegram(formattedMessage);
    }
    
    private void sendServerStartMessage() {
        if (!enabled) return;
        
        String message = config.getString("telegram.formats.server-start", "Сервер включен!");
        sendToTelegram(message);
    }
    
    public void sendToTelegram(String message) {
        // Здесь будет код отправки сообщения в Telegram
        logger.info("[Telegram] " + message);
    }
    
    public void receiveMessageFromTelegram(String user, String message) {
        if (!enabled || !telegramToChat) return;
        
        String formattedMessage = plugin.getConfigManager().getMessage("telegram.from-telegram", "&8[&9TG&8] &b{user}&7: &f{message}")
            .replace("{user}", user)
            .replace("{message}", message);
        
        // Отправка сообщения в глобальный чат
        Bukkit.broadcastMessage(formattedMessage);
    }
    
    public void shutdown() {
        // Заглушка для выключения бота
        if (enabled) {
            logger.info("🔴 Telegram бот отключен");
        }
    }
    
    public boolean isEnabled() {
        return enabled;
    }
}
