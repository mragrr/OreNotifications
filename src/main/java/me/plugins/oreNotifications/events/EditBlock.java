package me.plugins.oreNotifications.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class EditBlock implements Listener {
    private final JavaPlugin plugin;

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

            // Проверка на ручное отключение отслеживания данного блока
            if (!blockInfo.getBoolean("enable", true)) {
                return;
            }

            // Перебираем допустимые имена материала соответствующие данному блоку
            List<String> materialNames = blockInfo.getStringList("materialNames");
            for (String materialName : materialNames) {
                // При сходстве со сломанным блоком
                if (materialName.equals(currentMaterialName)) {
                    Player player = event.getPlayer(); // Получаем игрока
                    String playerName = player.getName(); // Получаем никнейм игрока, который вызвал ивент
                    String rawNotifyMessage = blockInfo.getString("message"); // Парсим из конфига сообщение о добыче блока
                    String gameModeName = player.getGameMode().name(); // Получаем имя режима игры
                    ItemStack item = player.getInventory().getItemInMainHand();

                    // Проверка на отсутствие сообщения
                    if (rawNotifyMessage == null) {
                        return;
                    }

                    // Проверка на наличие шелкового касания
                    if (item.containsEnchantment(Enchantment.SILK_TOUCH) &&
                            !blockInfo.getBoolean("silk_touch", false)) {
                        return;
                    }

                    // Проверка на игру в режиме выживания
                    if (!gameModeName.equals("SURVIVAL") &&
                            plugin.getConfig().getBoolean("survival_only", true)) {
                        return;
                    }

                    int count = editConfig(playerName, blockName); // Регистрируем сломанный блок в конфиг и записываем текущее значение счетчика

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