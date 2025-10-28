package com.gordey9992.russiancinematic.models;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class Cutscene {
    
    private String name;
    private String displayName;
    private List<Dialogue> dialogues;
    private Location startLocation;
    private boolean skipable;
    private int duration;
    
    public Cutscene(String name, String displayName, List<Dialogue> dialogues, Location startLocation, boolean skipable, int duration) {
        this.name = name;
        this.displayName = displayName;
        this.dialogues = dialogues;
        this.startLocation = startLocation;
        this.skipable = skipable;
        this.duration = duration;
    }
    
    public void play(Player player) {
        // Логика воспроизведения катсцены
        player.teleport(startLocation);
        
        // Воспроизведение диалогов
        for (Dialogue dialogue : dialogues) {
            dialogue.display(player);
        }
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    
    public List<Dialogue> getDialogues() { return dialogues; }
    public void setDialogues(List<Dialogue> dialogues) { this.dialogues = dialogues; }
    
    public Location getStartLocation() { return startLocation; }
    public void setStartLocation(Location startLocation) { this.startLocation = startLocation; }
    
    public boolean isSkipable() { return skipable; }
    public void setSkipable(boolean skipable) { this.skipable = skipable; }
    
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
}
