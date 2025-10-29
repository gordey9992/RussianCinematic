package com.gordey9992.russiancinematic.listeners;

import com.gordey9992.russiancinematic.RussianCinematic;
import com.gordey9992.russiancinematic.managers.NPCManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class NPCInteractListener implements Listener {
    
    private final RussianCinematic plugin;
    private final NPCManager npcManager;
    
    public NPCInteractListener(RussianCinematic plugin) {
        this.plugin = plugin;
        this.npcManager = plugin.getNPCManager();
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        
        if (item == null || item.getType() != Material.PLAYER_HEAD) return;
        
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if (meta == null || !meta.hasLore()) return;
        
        // Проверяем, это голова NPC
        for (String loreLine : meta.getLore()) {
            if (loreLine.contains("NPC: ")) {
                String npcName = loreLine.replace("§7NPC: ", "").trim();
                handleNPCInteract(player, npcName);
                event.setCancelled(true);
                break;
            }
        }
    }
    
    private void handleNPCInteract(Player player, String npcName) {
        if (npcManager.isPlayerInDialogue(player)) {
            player.sendMessage("§cВы уже ведете диалог с другим NPC!");
            return;
        }
        
        npcManager.handlePlayerInteract(player, npcName);
        
        // Воспроизводим звук взаимодействия
        player.playSound(player.getLocation(), 
            org.bukkit.Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
    }
}
