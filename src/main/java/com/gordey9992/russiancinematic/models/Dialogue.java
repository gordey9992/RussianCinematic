package com.gordey9992.russiancinematic.models;

import org.bukkit.entity.Player;
import org.bukkit.Sound;

import java.util.List;

public class Dialogue {
    
    private String npcName;
    private String message;
    private int duration;
    private Sound sound;
    private List<String> choices;
    private String nextDialogue;
    
    public Dialogue(String npcName, String message, int duration, Sound sound, List<String> choices, String nextDialogue) {
        this.npcName = npcName;
        this.message = message;
        this.duration = duration;
        this.sound = sound;
        this.choices = choices;
        this.nextDialogue = nextDialogue;
    }
    
    public void display(Player player) {
        // Отображение диалога игроку
        String formattedMessage = formatMessage();
        player.sendMessage(formattedMessage);
        
        if (sound != null) {
            player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
        }
        
        if (choices != null && !choices.isEmpty()) {
            displayChoices(player);
        }
    }
    
    private String formatMessage() {
        return "§7" + npcName + ": §f" + message;
    }
    
    private void displayChoices(Player player) {
        player.sendMessage("§6Выберите вариант:");
        for (int i = 0; i < choices.size(); i++) {
            player.sendMessage("§7" + (i + 1) + ". §f" + choices.get(i));
        }
    }
    
    // Getters and Setters
    public String getNpcName() { return npcName; }
    public void setNpcName(String npcName) { this.npcName = npcName; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    
    public Sound getSound() { return sound; }
    public void setSound(Sound sound) { this.sound = sound; }
    
    public List<String> getChoices() { return choices; }
    public void setChoices(List<String> choices) { this.choices = choices; }
    
    public String getNextDialogue() { return nextDialogue; }
    public void setNextDialogue(String nextDialogue) { this.nextDialogue = nextDialogue; }
}
