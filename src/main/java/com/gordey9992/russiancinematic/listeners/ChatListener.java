package com.gordey9992.russiancinematic.listeners;

import com.gordey9992.russiancinematic.RussianCinematic;
import com.gordey9992.russiancinematic.managers.TelegramManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    
    private final RussianCinematic plugin;
    private final TelegramManager telegramManager;
    
    public ChatListener(RussianCinematic plugin) {
        this.plugin = plugin;
        this.telegramManager = plugin.getTelegramManager();
    }
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // Отправляем сообщение в Telegram
        telegramManager.sendChatMessage(event.getPlayer(), event.getMessage());
        
        // Можно добавить фильтрацию или модификацию сообщений
        if (shouldFilterMessage(event.getMessage())) {
            // Логика фильтрации
        }
    }
    
    private boolean shouldFilterMessage(String message) {
        // Простая проверка на спам
        return message.length() < 2 || message.length() > 256;
    }
}
