package com.gordey9992.russiancinematic.listeners;

import com.gordey9992.russiancinematic.RussianCinematic;
import com.gordey9992.russiancinematic.managers.TelegramManager;
import com.gordey9992.russiancinematic.managers.CinematicManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    
    private final RussianCinematic plugin;
    private final TelegramManager telegramManager;
    private final CinematicManager cinematicManager;
    
    public PlayerQuitListener(RussianCinematic plugin) {
        this.plugin = plugin;
        this.telegramManager = plugin.getTelegramManager();
        this.cinematicManager = plugin.getCinematicManager();
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Отправляем уведомление в Telegram
        telegramManager.sendQuitMessage(event.getPlayer());
        
        // Останавливаем катсцену если игрок в ней
        cinematicManager.stopCutscene(event.getPlayer());
    }
}
