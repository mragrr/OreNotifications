package me.plugins.oreNotifications.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DefaultEvent implements Listener {
    private JavaPlugin plugin;

    public DefaultEvent(JavaPlugin p) {
        plugin = p;
    }

    public int editConfig(String playerName, String blockName) {
        int count = plugin.getConfig().getInt("players." + playerName + "." + blockName, 0);
        count = count + 1;
        plugin.getConfig().set("players." + playerName + "." + blockName, count);
        plugin.saveConfig();
        return count;
    }

    @EventHandler
    public void PlayerEditBlock(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material material = block.getBlockData().getMaterial();
        String materialName = material.name();
        // TODO 1.1 Реализовать добавление отслеживаемых блоков и их lang-индивидуальностей в конфиге
        // TODO 1.1 Защита от шёлкового касания
        if (materialName.equals("DIAMOND_ORE") || materialName.equals("DEEPSLATE_DIAMOND_ORE")) {
            String playerName = event.getPlayer().getName();
            int count = editConfig(playerName, "diamond");
            // TODO 1.1 Реализовать написание только какого то по счёту блока
            // TODO 1.2 Реализовать написание только последнего добытого за какой-то тайм блока
            Bukkit.broadcastMessage("Игрок " + playerName + " сломал алмазную руду. Счётчик руд: " + count);
        }
        if (materialName.equals("ANCIENT_DEBRIS")) {
            String playerName = event.getPlayer().getName();
            int count = editConfig(playerName, "derbis");
            Bukkit.broadcastMessage("Игрок " + playerName + " сломал древние осколки. Счётчик осколков: " + count);
        }
    }
}