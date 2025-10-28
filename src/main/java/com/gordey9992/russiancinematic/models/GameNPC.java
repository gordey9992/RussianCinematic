package com.gordey9992.russiancinematic.models;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.logging.Logger;

public class GameNPC {
    
    private final String id;
    private final String name;
    private final Location location;
    private final SkinType skinType;
    private final String displayName;
    private final List<String> dialogues;
    private final Map<String, String> commands;
    private final Set<UUID> visibleTo;
    
    private boolean isSpawned;
    
    public GameNPC(String id, String name, Location location, SkinType skinType) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.skinType = skinType;
        this.displayName = "§e§l" + name;
        this.dialogues = new ArrayList<>();
        this.commands = new HashMap<>();
        this.visibleTo = new HashSet<>();
        this.isSpawned = false;
        
        // Добавляем стандартные диалоги
        addDefaultDialogues();
    }
    
    public void spawnForPlayer(Player player) {
        if (isSpawned && visibleTo.contains(player.getUniqueId())) {
            return;
        }
        
        // Здесь будет код создания NPC через пакеты
        // Пока эмулируем создание NPC
        
        player.sendMessage("§7[Система] §fNPC §e" + name + "§f появился рядом с вами");
        
        // Показываем частицы для визуального эффекта
        spawnParticles(player);
        
        visibleTo.add(player.getUniqueId());
        isSpawned = true;
        
        // Даем голову с кожей NPC
        giveNPCSkull(player);
    }
    
    public void hideFromPlayer(Player player) {
        if (visibleTo.remove(player.getUniqueId())) {
            player.sendMessage("§7[Система] §fNPC §e" + name + "§f исчез");
        }
    }
    
    public void startDialogue(Player player) {
        if (dialogues.isEmpty()) {
            player.sendMessage("§7[Система] §f" + displayName + "§7: Привет, путник!");
            return;
        }
        
        // Показываем первый диалог
        showDialogue(player, 0);
    }
    
    public void showDialogue(Player player, int dialogueIndex) {
        if (dialogueIndex >= dialogues.size()) {
            endDialogue(player);
            return;
        }
        
        String dialogue = dialogues.get(dialogueIndex);
        player.sendMessage(displayName + "§7: " + dialogue);
        
        // Воспроизводим звук
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1.0f, 1.0f);
        
        // Планируем следующий диалог
        new BukkitRunnable() {
            @Override
            public void run() {
                showDialogue(player, dialogueIndex + 1);
            }
        }.runTaskLater(com.gordey9992.russiancinematic.RussianCinematic.getInstance(), 60L); // 3 секунды
    }
    
    public void endDialogue(Player player) {
        player.sendMessage("§7[Система] §fДиалог с §e" + name + "§f завершен");
        
        // Выполняем команду если есть
        executeCommands(player);
    }
    
    private void addDefaultDialogues() {
        dialogues.add("Приветствую тебя, путник!");
        dialogues.add("Я могу рассказать тебе много интересного...");
        dialogues.add("Возвращайся, если захочешь поговорить снова!");
    }
    
    public void addDialogue(String dialogue) {
        dialogues.add(dialogue);
    }
    
    public void addCommand(String command, String description) {
        commands.put(command, description);
    }
    
    private void executeCommands(Player player) {
        for (Map.Entry<String, String> entry : commands.entrySet()) {
            String command = entry.getKey().replace("{player}", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }
    
    private void spawnParticles(Player player) {
        Location particleLoc = location.clone().add(0, 2, 0);
        player.spawnParticle(Particle.VILLAGER_HAPPY, particleLoc, 10, 0.5, 1, 0.5);
    }
    
    private void giveNPCSkull(Player player) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        
        if (meta != null) {
            meta.setDisplayName("§6Голова NPC: §e" + name);
            meta.setLore(Arrays.asList(
                "§7NPC: " + name,
                "§7ID: " + id,
                "§7Нажми ПКМ для диалога"
            ));
            
            // Устанавливаем кожу в зависимости от типа
            switch (skinType) {
                case KNIGHT:
                    meta.setOwner("MHF_Steve");
                    break;
                case WIZARD:
                    meta.setOwner("MHF_Alex");
                    break;
                case MERCHANT:
                    meta.setOwner("MHF_Villager");
                    break;
                default:
                    meta.setOwner("MHF_Question");
            }
            
            skull.setItemMeta(meta);
        }
        
        player.getInventory().addItem(skull);
    }
    
    public void destroy() {
        // Убираем NPC у всех игроков
        for (UUID playerId : visibleTo) {
            Player player = Bukkit.getPlayer(playerId);
            if (player != null) {
                hideFromPlayer(player);
            }
        }
        visibleTo.clear();
        isSpawned = false;
    }
    
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDisplayName() { return displayName; }
    public Location getLocation() { return location; }
    public boolean isSpawned() { return isSpawned; }
}

enum SkinType {
    KNIGHT,
    WIZARD, 
    MERCHANT,
    VILLAGER,
    CUSTOM
}
