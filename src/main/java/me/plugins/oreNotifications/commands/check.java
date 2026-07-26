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
        int argsLen = args.length; // Количество аргументов

        // Обработка команд, которые имеют какие-либо аргументы
        if (argsLen != 0) {

            // Логика /ore check
            if (args[0].equals("check")) {

                // Логика /ore check без дальнейших аргументов
                if (argsLen == 1) {
                    if (!isPlayer(sender)) return true; // проверка на отправителя игрока
                    String name = sender.getName(); // никнейм игрока

                    // Выбираем секцию игрока в конфиге
                    ConfigurationSection playerBlocksSection = plugin.getConfig()
                            .getConfigurationSection("players." + name);

                    // Проверка существования секции и наличия элементов в ней
                    if (playerBlocksSection == null || playerBlocksSection.getKeys(false).isEmpty()) {
                        sender.sendMessage("Нет данных.");
                        return true;
                    }

                    // Вывод игроку
                    sender.sendMessage("Ваша статистика по блокам: ");
                    int i = 0;
                    for (String block : playerBlocksSection.getKeys(false)) {
                        int count = playerBlocksSection.getInt(block);
                        i++;
                        sender.sendMessage(" [№" + i + "] " + block + ": добыто - " + count);
                    }
                    return true;

                // Логика /ore check {NAME}
                } else if (argsLen == 2) {
                    if (!hasPerm(sender, "orenotifications.check")) return true; // Проверка права использования
                    String name = args[1]; // возможный никнейм игрока

                    // Выбираем секцию игрока в конфиге
                    ConfigurationSection playerBlocksSection = plugin.getConfig()
                            .getConfigurationSection("players." + name);

                    // Проверка существования секции и наличия элементов в ней
                    if (playerBlocksSection == null || playerBlocksSection.getKeys(false).isEmpty()) {
                        sender.sendMessage("Нет данных.");
                        return true;
                    }

                    // Вывод игроку
                    sender.sendMessage("Статистика по блокам для игрока " + name + ": ");
                    int i = 0;
                    for (String block : playerBlocksSection.getKeys(false)) {
                        int count = playerBlocksSection.getInt(block);
                        i++;
                        sender.sendMessage(" [" + i + "] " + block + ": добыто - " + count);
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
