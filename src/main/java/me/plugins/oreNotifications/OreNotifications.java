package me.plugins.oreNotifications;

import me.plugins.oreNotifications.commands.check;
import me.plugins.oreNotifications.config.DefaultConfig;
import me.plugins.oreNotifications.events.EditBlock;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class OreNotifications extends JavaPlugin {
    private final JavaPlugin plugin = this;
    private final DefaultConfig config = new DefaultConfig(plugin);

    @Override
    public void onEnable() {
        // TODO Добавление команды добавления считаемого блока
        // TODO Добавление команды проверки статистики игрока по блокам
        // TODO Добавление команды проверки статистики игрока по блоку
        // TODO Реализация команд топа игроков по блокам
        // TODO Реализация возможности добавления Scoreboard с топом
        // TODO Персональные (каждый + в месте) и глобальные сообщения (изменение топ-1) о изменении положения в топе
        // TODO Привелегии для топ игроков.

        // Настройка отслеживания изменения блоков сломанных при помощи шёлкового касания
        config.addSetting(
                "survival_only",
                true,
                List.of("Отслеживание изменения блоков сломанных только в gamemode survival")
        );

        // Стандартные настройки на отслеживание алмазной руды
        config.addSetting(
                "blocks.diamond.enable",
                true,
                List.of("false чтобы отключить отслеживание данного блока.", "От данной записи нельзя отказаться, поскольку она используется как пример,", "но её можно отключить путем изменения данного параметра")
        );
        config.addSetting(
                "blocks.diamond.message",
                "{NAME} сломал алмазную руду. Счетчик руд: {COUNT}",
                List.of("Сообщение о добыче блока руды.", "{NAME} - никнейм игрока", "{COUNT} - количество добытых блоков")
        );
        config.addSetting(
                "blocks.diamond.silk_touch",
                false,
                List.of("Отслеживание блока сломанного при помощи шёлкового касания")
        );
        config.addSettingList(
                "blocks.diamond.materialNames",
                List.of("DIAMOND_ORE", "DEEPSLATE_DIAMOND_ORE"),
                List.of("Имена отслеживаемых блоков")
        );

        // Стандартные настройки на отслеживание древних осколков
        config.addSetting(
                "blocks.debris.enable",
                true,
                List.of("false чтобы отключить отслеживание данного блока.", "От данной записи нельзя отказаться, поскольку она используется как пример,", "но её можно отключить путем изменения данного параметра")
        );
        config.addSetting(
                "blocks.debris.message",
                "{NAME} сломал древний осколок. Счетчик осколков: {COUNT}"
        );
        config.addSetting(
                "blocks.debris.silk_touch",
                true
        );
        config.addSettingList(
                "blocks.debris.materialNames",
                List.of("ANCIENT_DEBRIS")
        );

        getCommand("ore").setExecutor(new check(this));
        Bukkit.getPluginManager().registerEvents(new EditBlock(plugin), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
