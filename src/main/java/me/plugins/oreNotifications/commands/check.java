package me.plugins.oreNotifications.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class check implements CommandExecutor {
    private final JavaPlugin plugin;

    public check(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean isPlayer(CommandSender sender) {
        if (sender instanceof Player) {
            return true;
        } else {
            sender.sendMessage("Данная команда доступна только игрокам.");
            return false;
        }
    }

    public boolean hasPerm(CommandSender sender, String permission) {
        if (sender.hasPermission(permission)) {
            return true;
        } else {
            sender.sendMessage("У вас нет прав на использование данной команды.");
            return false;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int argsLen = args.length;
        if (argsLen != 0) {
            if (args[0].equals("check")) {
                if (!hasPerm(sender, "orenotifications.check")) return true;
                if (argsLen == 1) {
                    if (!isPlayer(sender)) return true;
                    String name = sender.getName();
                    ConfigurationSection playerBlocksSection = plugin.getConfig().getConfigurationSection("players." + name);
                    if (playerBlocksSection == null || playerBlocksSection.getKeys(false).isEmpty()) {
                        sender.sendMessage("Нет данных.");
                        return true;
                    }
                    sender.sendMessage("Ваша статистика по блокам: ");
                    for (String block : playerBlocksSection.getKeys(false)) {
                        int count = playerBlocksSection.getInt(block);
                        sender.sendMessage(" ! " + block + ": добыто - " + count);
                    }
                    return true;
                }
            }
        } else {
            // При написании /ore без аргументов
            return false;
        }
        return false;
    }
}
