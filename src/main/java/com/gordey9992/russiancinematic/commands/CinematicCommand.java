package com.gordey9992.russiancinematic.commands;

import com.gordey9992.russiancinematic.RussianCinematic;
import com.gordey9992.russiancinematic.managers.CinematicManager;
import com.gordey9992.russiancinematic.managers.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class CinematicCommand implements CommandExecutor, TabCompleter {
    
    private final RussianCinematic plugin;
    private final CinematicManager cinematicManager;
    private final ConfigManager configManager;
    
    public CinematicCommand(RussianCinematic plugin) {
        this.plugin = plugin;
        this.cinematicManager = plugin.getCinematicManager();
        this.configManager = plugin.getConfigManager();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "start":
                handleStart(sender, args);
                break;
            case "stop":
                handleStop(sender, args);
                break;
            case "skip":
                handleSkip(sender);
                break;
            case "reload":
                handleReload(sender);
                break;
            default:
                sendHelp(sender);
                break;
        }
        
        return true;
    }
    
    private void handleStart(CommandSender sender, String[] args) {
        if (!sender.hasPermission("russiancinematic.cinematic.start")) {
            sender.sendMessage(configManager.getMessage("general.no-permission", "&cНет прав"));
            return;
        }
        
        Player target;
        String cutsceneName;
        
        if (args.length >= 2 && sender.hasPermission("russiancinematic.cinematic.start.others")) {
            target = plugin.getServer().getPlayer(args[1]);
            cutsceneName = args.length >= 3 ? args[2] : cinematicManager.getDefaultCutscene();
        } else if (sender instanceof Player) {
            target = (Player) sender;
            cutsceneName = args.length >= 2 ? args[1] : cinematicManager.getDefaultCutscene();
        } else {
            sender.sendMessage(configManager.getMessage("general.player-only", "&cТолько для игроков"));
            return;
        }
        
        if (target == null) {
            sender.sendMessage(configManager.getMessage("general.player-offline", "&cИгрок не в сети"));
            return;
        }
        
        if (cinematicManager.isInCutscene(target)) {
            sender.sendMessage(configManager.getMessage("cinematic.already-in-cutscene", "&cУже в катсцене"));
            return;
        }
        
        cinematicManager.startCutscene(target, cutsceneName);
        // Без сообщения о начале катсцены
    }
    
    private void handleStop(CommandSender sender, String[] args) {
        if (!sender.hasPermission("russiancinematic.cinematic.stop")) {
            sender.sendMessage(configManager.getMessage("general.no-permission", "&cНет прав"));
            return;
        }
        
        Player target;
        
        if (args.length >= 2 && sender.hasPermission("russiancinematic.cinematic.stop.others")) {
            target = plugin.getServer().getPlayer(args[1]);
        } else if (sender instanceof Player) {
            target = (Player) sender;
        } else {
            sender.sendMessage(configManager.getMessage("general.player-only", "&cТолько для игроков"));
            return;
        }
        
        if (target == null) {
            sender.sendMessage(configManager.getMessage("general.player-offline", "&cИгрок не в сети"));
            return;
        }
        
        if (!cinematicManager.isInCutscene(target)) {
            sender.sendMessage(configManager.getMessage("cinematic.not-in-cutscene", "&cНе в катсцене"));
            return;
        }
        
        cinematicManager.stopCutscene(target);
        // Без сообщения об остановке катсцены
    }
    
    private void handleSkip(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(configManager.getMessage("general.player-only", "&cТолько для игроков"));
            return;
        }
        
        Player player = (Player) sender;
        
        if (!cinematicManager.isInCutscene(player)) {
            player.sendMessage(configManager.getMessage("cinematic.not-in-cutscene", "&cНе в катсцене"));
            return;
        }
        
        cinematicManager.stopCutscene(player);
        player.sendMessage(configManager.getMessage("cinematic.cutscene-skipped", "&eКатсцена пропущена"));
    }
    
    private void handleReload(CommandSender sender) {
        if (!sender.hasPermission("russiancinematic.admin")) {
            sender.sendMessage(configManager.getMessage("general.no-permission", "&cНет прав"));
            return;
        }
        
        configManager.reloadConfigs();
        sender.sendMessage(configManager.getMessage("admin.config-reloaded", "&aКонфиги перезагружены"));
    }
    
    private void sendHelp(CommandSender sender) {
        if (!sender.hasPermission("russiancinematic.cinematic.use")) {
            sender.sendMessage(configManager.getMessage("general.no-permission", "&cНет прав"));
            return;
        }
        
        List<String> helpLines = new ArrayList<>();
        helpLines.add(configManager.getMessage("player.help.title", "&6=== &eRussianCinematic - Помощь &6==="));
        
        if (sender.hasPermission("russiancinematic.cinematic.start")) {
            helpLines.add(configManager.getMessage("player.help.cinematic-start", "&7/cinematic start [катсцена] &f- Начать катсцену"));
        }
        
        if (sender.hasPermission("russiancinematic.cinematic.stop")) {
            helpLines.add(configManager.getMessage("player.help.cinematic-stop", "&7/cinematic stop &f- Остановить катсцену"));
        }
        
        if (sender.hasPermission("russiancinematic.cinematic.skip")) {
            helpLines.add(configManager.getMessage("player.help.cinematic-skip", "&7/cinematic skip &f- Пропустить катсцену"));
        }
        
        helpLines.forEach(sender::sendMessage);
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            completions.addAll(Arrays.asList("start", "stop", "skip", "reload"));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("start")) {
            completions.addAll(Arrays.asList("welcome", "tutorial", "quest1"));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("stop")) {
            completions.addAll(getOnlinePlayerNames());
        }
        
        return completions;
    }
    
    private List<String> getOnlinePlayerNames() {
        List<String> names = new ArrayList<>();
        plugin.getServer().getOnlinePlayers().forEach(player -> names.add(player.getName()));
        return names;
    }
}
