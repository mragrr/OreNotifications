package me.plugins.oreNotifications;

import me.plugins.oreNotifications.events.DefaultEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class OreNotifications extends JavaPlugin {
    private final JavaPlugin plugin = this;

    @Override
    public void onEnable() {
        // TODO 1.2 Реализация команд топа игроков по блокам
        // TODO 1.3 Реализация возможности добавления Scoreboard с топом
        // TODO 1.3 Персональные (каждый + в месте) и глобальные сообщения (изменение топ-1) о изменении положения в топе
        // TODO 1.4 Привелегии для топ игроков.
        Bukkit.getPluginManager().registerEvents(new DefaultEvent(plugin), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
