package me.plugins.oreNotifications.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class EditBlock implements Listener {
    // TODO Защита от шёлкового касания
    // TODO Реализовать написание только какого-то по счёту блока
    // TODO Реализовать написание только последнего добытого за какой-то тайм блока
    private JavaPlugin plugin;

    public EditBlock(JavaPlugin p) {
        plugin = p;
    }

    // Запись изменения в счетчике и возврат текущего числа
    public int editConfig(String playerName, String blockName) {
        // Получаем
        int count = plugin.getConfig().getInt("players." + playerName + "." + blockName, 0);

        // Добавляем текущее изменение
        count = count + 1;
        plugin.getConfig().set("players." + playerName + "." + blockName, count);

        // Сохраняем конфиг после изменения
        plugin.saveConfig();

        return count;
    }

    @EventHandler
    public void PlayerEditBlock(BlockBreakEvent event) {
        Block currentBlock = event.getBlock(); // Блок
        Material material = currentBlock.getBlockData().getMaterial(); // Текущий материал
        String currentMaterialName = material.name(); // Название материала

        // Секция блоков
        ConfigurationSection blocksSection = plugin.getConfig().getConfigurationSection("blocks");

        // Проверка на отсутствие элементов
        if (blocksSection == null) {
            return;
        }

        // Перебор блоков
        for (String blockName : blocksSection.getKeys(false)) {
            // Секция информации блока
            ConfigurationSection blockInfo = blocksSection.getConfigurationSection(blockName);

            // Проверка на отсутствие элементов внутри блока
            if (blockInfo == null) {
                return;
            }

            // Перебираем допустимые имена материала соответствующие данному блоку
            List<String> materialNames = blockInfo.getStringList("materialNames");
            for (String materialName : materialNames) {
                // При сходстве с сломанным блоком
                if (materialName.equals(currentMaterialName)) {
                    String playerName = event.getPlayer().getName(); // Получаем никнейм игрока, который вызвал ивент
                    int count = editConfig(playerName, blockName); // Регистрируем сломанный блок в конфиг и записываем текущее значение счетчика
                    String rawNotifyMessage = blockInfo.getString("message"); // Парсим из конфига сообщение о добыче блока

                    // Проверка на отсутствие сообщения
                    if (rawNotifyMessage == null) {
                        return;
                    }

                    // Преобразование сообщения
                    String notifyMessage = rawNotifyMessage
                            .replace("{NAME}", playerName)
                            .replace("{COUNT}", String.valueOf(count));

                    // Вывод сообщения
                    Bukkit.broadcastMessage(notifyMessage);
                }
            }
        }
    }
}