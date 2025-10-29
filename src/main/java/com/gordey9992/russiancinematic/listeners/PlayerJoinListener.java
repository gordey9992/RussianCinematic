package com.gordey9992.russiancinematic.listeners;

import com.gordey9992.russiancinematic.RussianCinematic;
import com.gordey9992.russiancinematic.managers.TelegramManager;
import com.gordey9992.russiancinematic.managers.CinematicManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    
    private final RussianCinematic plugin;
    private final TelegramManager telegramManager;
    private final CinematicManager cinematicManager;
    
    public PlayerJoinListener(RussianCinematic plugin) {
        this.plugin = plugin;
        this.telegramManager = plugin.getTelegramManager();
        this.cinematicManager = plugin.getCinematicManager();
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Отправляем уведомление в Telegram
        telegramManager.sendJoinMessage(event.getPlayer());
        
        // Запускаем автоматическую катсцену для новых игроков
        cinematicManager.startAutoCutscene(event.getPlayer());
    }
}
