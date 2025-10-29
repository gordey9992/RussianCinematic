package com.gordey9992.russiancinematic.commands;

import com.gordey9992.russiancinematic.RussianCinematic;
import com.gordey9992.russiancinematic.managers.TelegramManager;
import com.gordey9992.russiancinematic.managers.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TelegramCommand implements CommandExecutor {
    
    private final RussianCinematic plugin;
    private final TelegramManager telegramManager;
    private final ConfigManager configManager;
    
    public TelegramCommand(RussianCinematic plugin) {
        this.plugin = plugin;
        this.telegramManager = plugin.getTelegramManager();
        this.configManager = plugin.getConfigManager();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "reload":
                handleReload(sender);
                break;
            case "status":
                handleStatus(sender);
                break;
            case "test":
                handleTest(sender);
                break;
            default:
                sendHelp(sender);
                break;
        }
        
        return true;
    }
    
    private void handleReload(CommandSender sender) {
        if (!sender.hasPermission("russiancinematic.telegram.reload")) {
            sender.sendMessage(configManager.getMessage("general.no-permission", "&cНет прав"));
            return;
        }
        
        configManager.reloadConfigs();
        telegramManager.initialize();
        sender.sendMessage(configManager.getMessage("general.reload", "&aКонфигурация перезагружена"));
    }
    
    private void handleStatus(CommandSender sender) {
        if (!sender.hasPermission("russiancinematic.telegram.status")) {
            sender.sendMessage(configManager.getMessage("general.no-permission", "&cНет прав"));
            return;
        }
        
        boolean isEnabled = telegramManager.isEnabled();
        
        if (isEnabled) {
            sender.sendMessage(configManager.getMessage("telegram.bot-connected", "&aTelegram бот подключен"));
        } else {
            sender.sendMessage(configManager.getMessage("telegram.not-configured", "&cTelegram не настроен"));
        }
    }
    
    private void handleTest(CommandSender sender) {
        if (!sender.hasPermission("russiancinematic.telegram.reload")) {
            sender.sendMessage(configManager.getMessage("general.no-permission", "&cНет прав"));
            return;
        }
        
        if (telegramManager.isEnabled()) {
            telegramManager.sendToTelegram("🔧 Тестовое сообщение от " + sender.getName());
            sender.sendMessage("&aТестовое сообщение отправлено в Telegram");
        } else {
            sender.sendMessage("&cTelegram бот не активирован");
        }
    }
    
    private void sendHelp(CommandSender sender) {
        if (!sender.hasPermission("russiancinematic.telegram.use")) {
            sender.sendMessage(configManager.getMessage("general.no-permission", "&cНет прав"));
            return;
        }
        
        sender.sendMessage(configManager.getMessage("player.help.title", "&6=== &eTelegram - Помощь &6==="));
        
        if (sender.hasPermission("russiancinematic.telegram.reload")) {
            sender.sendMessage("&7/telegram reload &f- Перезагрузить Telegram");
        }
        
        if (sender.hasPermission("russiancinematic.telegram.status")) {
            sender.sendMessage("&7/telegram status &f- Статус бота");
        }
        
        if (sender.hasPermission("russiancinematic.telegram.reload")) {
            sender.sendMessage("&7/telegram test &f- Тестовое сообщение");
        }
    }
}
