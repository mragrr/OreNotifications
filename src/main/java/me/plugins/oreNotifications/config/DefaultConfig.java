package me.plugins.oreNotifications.config;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Класс для добавления параметров по умолчанию стандартного конфига config.yml
 */
public class DefaultConfig {
    private JavaPlugin plugin;

    /**
     * Конструктор класса для добавления параметров по умолчанию стандартного конфига config.yml.
     * @param plugin основной класс плагина
     */
    public DefaultConfig(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
    * <b> Добавление параметра в конфиг </b>
     * @param name адрес параметра включая его наименование. Пример: "server.spawn"
     * @param def значение по умолчанию для данного параметра
     * @param comment List содержащий построчные комментарии для данного параметра. Пример: List.of("1 строка", "2 строка")
     */
    public void addSetting(String name, Object def, List<String> comment) {
        if (plugin.getConfig().getString(name, "").isEmpty()) {
            plugin.getConfig().set(name, def);
            plugin.getConfig().setComments(name, comment);
            plugin.saveConfig();
        }
    }

    /**
     * <b> Добавление параметра в конфиг </b>
     * @param name адрес параметра включая его наименование. Пример: "server.spawn"
     * @param def значение по умолчанию для данного параметра
     */
    public void addSetting(String name, Object def) {
        if (plugin.getConfig().getString(name, "").isEmpty()) {
            plugin.getConfig().set(name, def);
            plugin.saveConfig();
        }
    }

    /**
     * <b> Добавление параметра типа List в конфиг </b>
     * @param name адрес параметра включая его наименование. Пример: "server.spawn"
     * @param def значение по умолчанию для данного параметра типа List. Пример: List.of("1", "2")
     */
    public void addSettingList(String name, List<String> def) {
        if (plugin.getConfig().getString(name, "").isEmpty()) {
            plugin.getConfig().set(name, def);
            plugin.saveConfig();
        }
    }

    /**
     * <b> Добавление параметра типа List в конфиг </b>
     * @param name адрес параметра включая его наименование. Пример: "server.spawn"
     * @param def значение по умолчанию для данного параметра типа List. Пример: List.of("1", "2")
     * @param comment List содержащий построчные комментарии для данного параметра. Пример: List.of("1 строка", "2 строка")
     */
    public void addSettingList(String name, List<String> def, List<String> comment) {
        if (plugin.getConfig().getString(name, "").isEmpty()) {
            plugin.getConfig().set(name, def);
            plugin.getConfig().setComments(name, comment);
            plugin.saveConfig();
        }
    }
}
