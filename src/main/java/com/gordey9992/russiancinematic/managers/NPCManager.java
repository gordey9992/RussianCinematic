package com.gordey9992.russiancinematic.managers;

import com.gordey9992.russiancinematic.RussianCinematic;
import com.gordey9992.russiancinematic.models.GameNPC;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.logging.Logger;

public class NPCManager {
    
    private final RussianCinematic plugin;
    private final Logger logger;
    private final Map<String, GameNPC> npcs;
    private final Map<UUID, GameNPC> playerInteractions;
    
    public NPCManager(RussianCinematic plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.npcs = new HashMap<>();
        this.playerInteractions = new HashMap<>();
    }
    
    public void createNPC(String id, String name, Location location, SkinType skinType) {
        GameNPC npc = new GameNPC(id, name, location, skinType);
        npcs.put(id, npc);
        
        // Показываем NPC всем онлайн игрокам
        for (Player player : Bukkit.getOnlinePlayers()) {
            npc.spawnForPlayer(player);
        }
        
        logger.info("✅ Создан NPC: " + name + " (" + id + ")");
    }
    
    public void removeNPC(String id) {
        GameNPC npc = npcs.remove(id);
        if (npc != null) {
            npc.destroy();
            logger.info("🗑️ Удален NPC: " + id);
        }
    }
    
    public void showNPCToPlayer(String npcId, Player player) {
        GameNPC npc = npcs.get(npcId);
        if (npc != null) {
            npc.spawnForPlayer(player);
        }
    }
    
    public void hideNPCFromPlayer(String npcId, Player player) {
        GameNPC npc = npcs.get(npcId);
        if (npc != null) {
            npc.hideFromPlayer(player);
        }
    }
    
    public void startDialogue(Player player, String npcId) {
        GameNPC npc = npcs.get(npcId);
        if (npc != null) {
            playerInteractions.put(player.getUniqueId(), npc);
            npc.startDialogue(player);
        }
    }
    
    public void handlePlayerInteract(Player player, String npcName) {
        for (GameNPC npc : npcs.values()) {
            if (npc.getDisplayName().equals(npcName)) {
                startDialogue(player, npc.getId());
                break;
            }
        }
    }
    
    public boolean isPlayerInDialogue(Player player) {
        return playerInteractions.containsKey(player.getUniqueId());
    }
    
    public void endDialogue(Player player) {
        GameNPC npc = playerInteractions.remove(player.getUniqueId());
        if (npc != null) {
            npc.endDialogue(player);
        }
    }
    
    public void onPlayerJoin(Player player) {
        // Показываем все NPC новому игроку
        for (GameNPC npc : npcs.values()) {
            npc.spawnForPlayer(player);
        }
    }
    
    public void onPlayerQuit(Player player) {
        // Завершаем диалог если игрок вышел
        endDialogue(player);
    }
    
    public void cleanup() {
        // Удаляем все NPC при выключении
        for (GameNPC npc : npcs.values()) {
            npc.destroy();
        }
        npcs.clear();
        playerInteractions.clear();
    }
}
