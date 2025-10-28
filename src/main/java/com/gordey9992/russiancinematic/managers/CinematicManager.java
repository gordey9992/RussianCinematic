package com.gordey9992.russiancinematic.managers;

import com.gordey9992.russiancinematic.RussianCinematic;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.logging.Logger;

public class CinematicManager {
    
    private final RussianCinematic plugin;
    private final Logger logger;
    private final FileConfiguration config;
    
    private boolean enabled;
    private boolean autoSceneForNewPlayers;
    private String defaultCutscene;
    private Map<UUID, String> activeCutscenes;
    
    public CinematicManager(RussianCinematic plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.config = plugin.getConfig();
        this.activeCutscenes = new HashMap<>();
        
        loadConfig();
    }
    
    private void loadConfig() {
        enabled = config.getBoolean("cinematic.enabled", true);
        autoSceneForNewPlayers = config.getBoolean("cinematic.auto-scene-for-new-players", true);
        defaultCutscene = config.getString("cinematic.default-cutscene", "welcome");
    }
    
    public void startCutscene(Player player, String cutsceneName) {
        if (!enabled) return;
        
        // Блокируем управление игроку
        blockPlayerControl(player);
        
        // Запускаем катсцену (без сообщений в чат)
        activeCutscenes.put(player.getUniqueId(), cutsceneName);
        
        logger.info("🎭 Запущена катсцена '" + cutsceneName + "' для игрока " + player.getName());
    }
    
    public void stopCutscene(Player player) {
        String cutsceneName = activeCutscenes.remove(player.getUniqueId());
        if (cutsceneName != null) {
            // Разблокируем управление
            unblockPlayerControl(player);
            logger.info("🎭 Остановлена катсцена '" + cutsceneName + "' для игрока " + player.getName());
        }
    }
    
    public void startAutoCutscene(Player player) {
        if (autoSceneForNewPlayers && !hasPlayedCutscene(player)) {
            startCutscene(player, defaultCutscene);
            markCutsceneAsPlayed(player);
        }
    }
    
    private void blockPlayerControl(Player player) {
        // Блокируем движение и взаимодействие
        player.setWalkSpeed(0);
        player.setFlySpeed(0);
        player.setInvulnerable(true);
    }
    
    private void unblockPlayerControl(Player player) {
        // Восстанавливаем управление
        player.setWalkSpeed(0.2f);
        player.setFlySpeed(0.1f);
        player.setInvulnerable(false);
    }
    
    private boolean hasPlayedCutscene(Player player) {
        // Проверяем, играл ли игрок уже катсцену
        // Можно сохранять в файл или БД
        return false;
    }
    
    private void markCutsceneAsPlayed(Player player) {
        // Помечаем катсцену как просмотренную
    }
    
    public boolean isInCutscene(Player player) {
        return activeCutscenes.containsKey(player.getUniqueId());
    }
    
    public boolean isEnabled() {
        return enabled;
    }
}
