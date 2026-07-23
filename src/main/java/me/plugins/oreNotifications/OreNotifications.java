package me.plugins.oreNotifications;

import me.plugins.oreNotifications.events.DefaultEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class OreNotifications extends JavaPlugin {
    private final JavaPlugin plugin = this;

    @Override
    public void onEnable() {
        // TODO Добавление команды добавления считаемого блока
        // TODO Добавление команды проверки статистики игрока по блокам
        // TODO Добавление команды проверки статистики игрока по блоку
        // TODO Реализация команд топа игроков по блокам
        // TODO Реализация возможности добавления Scoreboard с топом
        // TODO Персональные (каждый + в месте) и глобальные сообщения (изменение топ-1) о изменении положения в топе
        // TODO Привелегии для топ игроков.
        Bukkit.getPluginManager().registerEvents(new DefaultEvent(plugin), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
