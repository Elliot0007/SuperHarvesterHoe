package me.c0dev.superharvesterhoe.HarvesterHoe.Events;

import me.c0dev.superharvesterhoe.HarvesterHoe.Files.DataManager;
import me.c0dev.superharvesterhoe.HarvesterHoe.PersistentData.Information;
import me.c0dev.superharvesterhoe.HarvesterHoe.PersistentData.InformationDataType;
import me.c0dev.superharvesterhoe.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class event implements Listener {
    private static Inventory gui;
    public static DataManager data;
    public static int Wheat = 0;
    public static int Carrots = 0;
    public static int Potatoes = 0;
    public static int Sugar_Cane = 0;
    public static int Bamboo = 0;
    public static int Kelp = 0;
    public static int Beetroot = 0;
    public static int Nether_Wart = 0;

    Plugin plugin = Main.getPlugin(Main.class);



    @EventHandler
    public void onRightClickBlock(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null) {
                PersistentDataContainer container = Objects.requireNonNull(event.getItem().getItemMeta()).getPersistentDataContainer();
                NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "harvester_hoe_uuid");
                if (container.has(key, new InformationDataType())) {
                    //Player player = event.getPlayer();
                    openNewGui(event.getPlayer());
                }
            }
        }
    }
    @EventHandler
    public void onRightClickAir(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (event.getItem() != null) {
                PersistentDataContainer container = Objects.requireNonNull(event.getItem().getItemMeta()).getPersistentDataContainer();
                NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "harvester_hoe_uuid");
                if (container.has(key, new InformationDataType())) {
                    openNewGui(event.getPlayer());
                }
            }
        }
    }
    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        Player player = e.getPlayer();
        Material cropBlockType = null;
        data = new DataManager(JavaPlugin.getPlugin(Main.class));
        if (player.getInventory().getItemInMainHand().getItemMeta() == null) return;
        PersistentDataContainer container = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "harvester_hoe_uuid");
        Information info = container.get(key, new InformationDataType());

        Material[] materials = {Material.WHEAT, Material.POTATOES, Material.CARROTS, Material.NETHER_WART};

        Material[] compMaterials = {Material.SUGAR_CANE, Material.BAMBOO, Material.KELP, Material.KELP_PLANT};

        String[] upgrades = {".wheat", ".potatoes" , ".carrots", ".nether_wart"};

        String[] compUpgrades = {".sugar_cane", ".bamboo", ".kelp"};

        if (container.has(key, new InformationDataType())) {
            for (Material m : materials) {
                if (block.getType() == m) {
                    Ageable ageable = (Ageable) block.getBlockData();
                    if (ageable.getAge() == 7) {
                        cropBlockType = m;
                        for (String s : upgrades) {
                            if (m.toString().equals(s.replace(".", "").toUpperCase())) {
                                block.setType(Material.AIR);
                                //player.getInventory().addItem(new ItemStack(Objects.requireNonNull(Material.getMaterial(m.toString().replaceAll("S|ES", ""))), data.getConfig().getInt(info.getUuid() + ".upgrades" + s)));
                                player.getInventory().addItem(new ItemStack(Objects.requireNonNull(Material.getMaterial(m.toString().replace("WHEAT", "WHEAT_SEEDS"))), 2)); // Subtracts one so has to be to two, super wierd

                                if (m == Material.WHEAT) {
                                    assert info != null;
                                    Wheat = Wheat + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".wheat");
                                    data.getConfig().set(info.getUuid() + ".amount_mined" + ".wheat", data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".wheat") + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".wheat"));
                                    data.saveConfig();
                                }

                                if (m == Material.POTATOES) {
                                    assert info != null;
                                    Potatoes = Potatoes + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".potatoes");
                                    if (data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".potatoes") == 0) {
                                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".potatoes", Potatoes);
                                    }
                                    else {
                                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".potatoes", data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".potatoes") + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".potatoes"));
                                    }
                                    if (player.getInventory().containsAtLeast(new ItemStack(Material.POTATO),1)){
                                        //Test
                                    }
                                    else{
                                        player.sendMessage("Potatoes");
                                        player.getInventory().addItem(new ItemStack(Material.POTATO));
                                    }
                                    data.saveConfig();
                                }

                                if (m == Material.CARROTS) {
                                    assert info != null;
                                    Carrots = Carrots + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".carrots");
                                    if (data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".carrots") == 0) {
                                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".carrots", Carrots);
                                    }
                                    else {
                                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".carrots", data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".carrots") + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".carrots"));
                                    }
                                    if (player.getInventory().containsAtLeast(new ItemStack(Material.CARROT),1)){
                                        //Test
                                    }
                                    else{
                                        //player.sendMessage("You need Carrots");
                                        player.getInventory().addItem(new ItemStack(Material.CARROT));
                                    }
                                    data.saveConfig();
                                }
                            }
                        }
                    }
                }
            }
            for (Material cm : compMaterials) {
                if (block.getType() == cm) {
                    cropBlockType = cm;
                    for (String cu : compUpgrades) {
                        if (player.getWorld().getBlockAt(block.getX(), block.getY() - 1, block.getZ()).getType().toString().contains(cm.toString())) {
                            if (cm.toString().replaceAll("KELP_PLANT", "KELP").equals(cu.replace(".", "").toUpperCase())) {
                                assert info != null;
                                player.getInventory().addItem(new ItemStack(Objects.requireNonNull(Material.getMaterial(cm.toString())), data.getConfig().getInt(info.getUuid() + ".upgrades" + cu)));
                                if (cm == Material.KELP) {
                                    block.setType(Material.WATER);
                                    Kelp = Kelp + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".kelp");
                                    if (data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".kelp") == 0) {
                                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".kelp", Kelp);
                                    }
                                    else {
                                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".kelp", data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".kelp") + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".kelp"));
                                    }
                                    data.saveConfig();
                                }
                                if (cm == Material.KELP_PLANT) {
                                    block.setType(Material.WATER);
                                    Kelp = Kelp + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".kelp");
                                    if (data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".kelp") == 0) {
                                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".kelp", Kelp);
                                    }
                                    else {
                                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".kelp", data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".kelp") + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".kelp"));
                                    }
                                    data.saveConfig();
                                }
                                if (cm == Material.SUGAR_CANE) {
                                    block.setType(Material.AIR);
                                    Sugar_Cane = Sugar_Cane + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".sugar_cane");
                                    if (data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".sugar_cane") == 0) {
                                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".sugar_cane", Sugar_Cane);
                                    }
                                    else {
                                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".sugar_cane", data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".sugar_cane") + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".sugar_cane"));
                                    }
                                    data.saveConfig();
                                }

                                if (cm == Material.BAMBOO) {
                                    block.setType(Material.AIR);
                                    Bamboo = Bamboo + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".bamboo");
                                    if (data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".bamboo") == 0) {
                                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".bamboo", Bamboo);
                                    }
                                    else {
                                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".bamboo", data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".bamboo") + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".bamboo"));
                                    }
                                    data.saveConfig();
                                }
                            }
                        }
                    }
                }
            }
            if (block.getType() == Material.BEETROOTS) {
                Ageable ageable = (Ageable) block.getBlockData();
                if (ageable.getAge() == 3) {
                    cropBlockType = Material.BEETROOTS;
                    //player.getInventory().addItem(new ItemStack(Material.BEETROOT, data.getConfig().getInt(info.getUuid() + ".upgrades" + ".beetroot")));
                    player.getInventory().addItem(new ItemStack(Material.BEETROOT_SEEDS));
                    block.setType(Material.AIR);
                    Beetroot = Beetroot + data.getConfig().getInt(info.getUuid() + ".upgrades" +".beetroot");
                    if (data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".beetroot") == 0)
                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".beetroot", Beetroot);
                    else
                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".beetroot", data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".beetroot") + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".beetroot"));
                    data.saveConfig();
                }
            }
            if (block.getType() == Material.NETHER_WART) {
                Ageable ageable = (Ageable) block.getBlockData();
                if (ageable.getAge() == 3) {
                    cropBlockType = Material.NETHER_WART;
                    player.getInventory().addItem(new ItemStack(Material.NETHER_WART));
                    block.setType(Material.AIR);
                    Nether_Wart = Nether_Wart + data.getConfig().getInt(info.getUuid() + ".upgrades" +".nether_wart");
                    if (data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".nether_wart") == 0)
                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".nether_wart", Nether_Wart);
                    else
                        data.getConfig().set(info.getUuid() + ".amount_mined" + ".nether_wart", data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".nether_wart") + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".nether_wart"));
                    data.saveConfig();
                }
            }

            for (Material m : materials) {
                for (String s : upgrades) {
                    if (m.toString().equals(s.replace(".", "").toUpperCase()) && data.getConfig().getInt(info.getUuid() + ".amount_mined" + s) < data.getConfig().getInt("small_bp_total_item_held")) {
                        data.getConfig().set(info.getUuid() + s + ".amount", data.getConfig().getInt(info.getUuid() + ".amount_mined" + s));
                        data.saveConfig();
                    }
                }
            }
            for (Material cm : compMaterials) {
                for (String cu : compUpgrades) {
                    if (cm.toString().replaceAll("KELP_PLANT", "KELP").equals(cu.replace(".", "").toUpperCase()) && data.getConfig().getInt(info.getUuid() + ".amount_mined" + cu) < data.getConfig().getInt("small_bp_total_item_held")) {
                        data.getConfig().set(info.getUuid() + cu + ".amount", data.getConfig().getInt(info.getUuid() + ".amount_mined" + cu));
                        data.saveConfig();
                    }
                }
            }

            data.getConfig().set(info.getUuid() + ".beetroot" + ".amount", data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".beetroot"));
            player.getInventory().remove(Material.BEETROOT);
            data.saveConfig();
        }

        for (ItemStack is : player.getInventory().getContents()) {
            if (is != null) {
                for (Material m : materials) {
                    for (String s : upgrades) {
                        if (m.toString().equals(s.replace(".", "").toUpperCase()) && data.getConfig().getInt(info.getUuid() + ".amount_mined" + s) < data.getConfig().getInt("small_bp_total_item_held")) {
                            if (is.getType().toString().equals(m.toString().replaceAll("S|ES", ""))) {
                                player.getInventory().remove(is);
                                replantCrop(block.getLocation(), m);
                            }
                        }
                    }
                }
                for (Material cm : compMaterials) {
                    for (String cu : compUpgrades) {
                        if (cm.toString().replaceAll("KELP_PLANT", "KELP").equals(cu.replace(".", "").toUpperCase()) && data.getConfig().getInt(info.getUuid() + ".amount_mined" + cu) < data.getConfig().getInt("small_bp_total_item_held")) {
                            if (is.getType().toString().equals(cm.toString().replaceAll("KELP_PLANT", "KELP"))) {
                                player.getInventory().remove(is);
                            }
                        }
                    }
                }
            }

            Material seedType = getSeedMaterial(cropBlockType);
            if (isSeedInInv(player, cropBlockType)) {
                removeSeed(player, seedType);
                replantCrop(block.getLocation(), cropBlockType);
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void CropsClaimed(InventoryClickEvent e) {
        if (!e.getInventory().equals(gui)) return;
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();

        PersistentDataContainer container = p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "harvester_hoe_uuid");
        Information info = container.get(key, new InformationDataType());
        Player player = (Player) e.getWhoClicked();

        data = new DataManager(JavaPlugin.getPlugin(Main.class));
        switch(e.getSlot()) {
            case 22: {
                int WheatTaken = data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".wheat");
                int BeetrootTaken = data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".beetroot");
                int CarrotsTaken = data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".carrots");
                int PotatoesTaken = data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".potatoes");
                int KelpTaken = data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".kelp");
                int BambooTaken = data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".bamboo");
                int SugarCaneTaken = data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".sugar_cane");
                int NetherWartTaken = data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".nether_wart");

                player.getInventory().addItem(new ItemStack(Material.WHEAT, WheatTaken));
                player.getInventory().addItem(new ItemStack(Material.BEETROOT, BeetrootTaken));
                player.getInventory().addItem(new ItemStack(Material.CARROT, CarrotsTaken));
                player.getInventory().addItem(new ItemStack(Material.POTATO, PotatoesTaken));
                player.getInventory().addItem(new ItemStack(Material.KELP, KelpTaken));
                player.getInventory().addItem(new ItemStack(Material.BAMBOO, BambooTaken));
                player.getInventory().addItem(new ItemStack(Material.SUGAR_CANE, SugarCaneTaken));
                player.getInventory().addItem(new ItemStack(Material.NETHER_WART, NetherWartTaken));

                data.getConfig().set(info.getUuid() + ".amount_mined" + ".wheat", 0);
                data.getConfig().set(info.getUuid() + ".amount_mined" + ".beetroot", 0);
                data.getConfig().set(info.getUuid() + ".amount_mined" + ".carrots", 0);
                data.getConfig().set(info.getUuid() + ".amount_mined" + ".potatoes", 0);
                data.getConfig().set(info.getUuid() + ".amount_mined" + ".kelp", 0);
                data.getConfig().set(info.getUuid() + ".amount_mined" + ".bamboo", 0);
                data.getConfig().set(info.getUuid() + ".amount_mined" + ".sugar_cane", 0);
                data.getConfig().set(info.getUuid() + ".amount_mined" + ".nether_wart", 0);
                data.saveConfig();
            }
            //Anvil Show's the upgrades which are selected
            case 31: {
                //
            }
            case 99: {
                //
            }
        }
    }

    @EventHandler
    public void hoeUpgradeEvent(InventoryClickEvent e) {

        if (!e.getInventory().equals(gui)) return;
        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        ItemStack item = p.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>(meta.getLore());
        int lvl = 1;

        data = new DataManager(JavaPlugin.getPlugin(Main.class));

        PersistentDataContainer container = p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "harvester_hoe_uuid");
        Information info = container.get(key, new InformationDataType());

        switch (e.getSlot()) {
            case 10: {
                e.getWhoClicked().sendMessage("The upgrade is currently not available ");
            }
            case 11: {
                e.getWhoClicked().sendMessage("The upgrade is currently not available ");
            }
            case 19: {
                e.getWhoClicked().sendMessage("The upgrade is currently not available ");
            }
            case 20: {
                e.getWhoClicked().sendMessage("The upgrade is currently not available ");
            }
            case 28: {
                e.getWhoClicked().sendMessage("The upgrade is currently not available ");
            }
            case 29: {
                e.getWhoClicked().sendMessage("The upgrade is currently not available ");
            }
            case 37: {
                e.getWhoClicked().sendMessage("The upgrade is currently not available ");
            }
            case 38: {
                e.getWhoClicked().sendMessage("The upgrade is currently not available ");
            }
            case 15: {
                lvl = data.getConfig().getInt(info.getUuid() + ".upgrades" + ".wheat");
                //Place to add a wrong amount items needed for upgrade
                if (p.getInventory().contains(Material.WHEAT, plugin.getConfig().getInt("wheat" + "." + lvl))) {
                    p.getInventory().removeItem(new ItemStack(Material.WHEAT, plugin.getConfig().getInt("wheat" + "." + lvl)));
                    if (data.getConfig().getInt("max_upgrade_lvl") > data.getConfig().getInt(info.getUuid() + ".upgrades" + ".wheat")) {
                        if (data.getConfig().contains(info.getUuid() + ".upgrades" + ".wheat"))
                            lvl = data.getConfig().getInt(info.getUuid() + ".upgrades" + ".wheat");
                        data.getConfig().set(info.getUuid() + ".upgrades" + ".wheat", (lvl + 1));
                        data.saveConfig();
                        lore.set(3, "??eWheat Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".wheat"));
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        p.openInventory(gui);
                        updateInventory(p);
                        break;
                    }
                }
                return;
            }
            case 16: {
                lvl = data.getConfig().getInt(info.getUuid() + ".upgrades" + ".beetroot");
                //Place to add a wrong amount items needed for upgrade
                if (p.getInventory().contains(Material.BEETROOT, plugin.getConfig().getInt("beetroot" + "." + lvl))) {
                    p.getInventory().removeItem(new ItemStack(Material.BEETROOT, plugin.getConfig().getInt("beetroot" + "." + lvl)));
                    if (data.getConfig().getInt("max_upgrade_lvl") > data.getConfig().getInt(info.getUuid() + ".upgrades" + ".beetroot")) {
                        if (data.getConfig().contains(info.getUuid() + ".upgrades" + ".beetroot"))
                            lvl = data.getConfig().getInt(info.getUuid() + ".upgrades" + ".beetroot");
                        data.getConfig().set(info.getUuid() + ".upgrades" + ".beetroot", (lvl + 1));
                        data.saveConfig();
                        lore.set(4, "??eBeetRoot Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".beetroot"));
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        p.openInventory(gui);
                        updateInventory(p);
                        break;
                    }
                }
                return;
            }
            case 24: {
                lvl = data.getConfig().getInt(info.getUuid() + ".upgrades" + ".carrots");
                //Place to add a wrong amount items needed for upgrade
                if (p.getInventory().contains(Material.CARROT, plugin.getConfig().getInt("carrots" + "." + lvl))) {
                    p.getInventory().removeItem(new ItemStack(Material.CARROT, plugin.getConfig().getInt("carrots" + "." + lvl)));
                    if (data.getConfig().getInt("max_upgrade_lvl") > data.getConfig().getInt(info.getUuid() + ".upgrades" + ".carrots")) {
                        if (data.getConfig().contains(info.getUuid() + ".upgrades" + ".carrots"))
                            lvl = data.getConfig().getInt(info.getUuid() + ".upgrades" + ".carrots");
                        data.getConfig().set(info.getUuid() + ".upgrades" + ".carrots", (lvl + 1));
                        data.saveConfig();
                        lore.set(5, "??eCarrot Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".carrots"));
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        p.openInventory(gui);
                        updateInventory(p);
                        break;
                    }
                }
                return;
            }
            case 25: {
                lvl = data.getConfig().getInt(info.getUuid() + ".upgrades" + ".potatoes");
                //Place to add a wrong amount items needed for upgrade
                if (p.getInventory().contains(Material.POTATO, plugin.getConfig().getInt("potatoes" + "." + lvl))) {
                    p.getInventory().removeItem(new ItemStack(Material.POTATO, plugin.getConfig().getInt("potatoes" + "." + lvl)));
                    if (data.getConfig().getInt("max_upgrade_lvl") > data.getConfig().getInt(info.getUuid() + ".upgrades" + ".potatoes")) {
                        if (data.getConfig().contains(info.getUuid() + ".upgrades" + ".potatoes"))
                            lvl = data.getConfig().getInt(info.getUuid() + ".upgrades" + ".potatoes");
                        data.getConfig().set(info.getUuid() + ".upgrades" + ".potatoes", (lvl + 1));
                        data.saveConfig();
                        lore.set(6, "??ePotatoes Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".potatoes"));
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        p.openInventory(gui);
                        updateInventory(p);
                        break;
                    }
                }
                return;
            }
            case 33: {
                lvl = data.getConfig().getInt(Objects.requireNonNull(info).getUuid() + ".upgrades" + ".kelp");
                //Place to add a wrong amount items needed for upgrade
                if (p.getInventory().contains(Material.KELP, plugin.getConfig().getInt("kelp" + "." + lvl))) {
                    p.getInventory().removeItem(new ItemStack(Material.KELP, plugin.getConfig().getInt("kelp" + "." + lvl)));
                    if (data.getConfig().getInt("max_upgrade_lvl") > data.getConfig().getInt(info.getUuid() + ".upgrades" + ".kelp")) {
                        if (data.getConfig().contains(info.getUuid() + ".upgrades" + ".kelp"))
                            lvl = data.getConfig().getInt(info.getUuid() + ".upgrades" + ".kelp");
                        data.getConfig().set(info.getUuid() + ".upgrades" + ".kelp", (lvl + 1));
                        data.saveConfig();
                        lore.set(7, "??eKelp Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".kelp"));
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        p.openInventory(gui);
                        updateInventory(p);
                        break;
                    }
                }
                return;
            }
            case 34: {
                lvl = data.getConfig().getInt(info.getUuid() + ".upgrades" + ".bamboo");
                if (p.getInventory().contains(Material.BAMBOO, plugin.getConfig().getInt("bamboo" + "." + lvl))) {
                    p.getInventory().removeItem(new ItemStack(Material.BAMBOO, plugin.getConfig().getInt("bamboo" + "." + lvl)));
                    if (data.getConfig().getInt("max_upgrade_lvl") > data.getConfig().getInt(info.getUuid() + ".upgrades" + ".bamboo")) {
                        if (data.getConfig().contains(info.getUuid() + ".upgrades" + ".bamboo"))
                            lvl = data.getConfig().getInt(info.getUuid() + ".upgrades" + ".bamboo");
                        data.getConfig().set(info.getUuid() + ".upgrades" + ".bamboo", (lvl + 1));
                        data.saveConfig();
                        lore.set(8, "??eBamboo Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".bamboo"));
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        p.openInventory(gui);
                        updateInventory(p);
                        break;
                    }
                }
                return;
            }
            case 42: {
                lvl = data.getConfig().getInt(info.getUuid() + ".upgrades" + ".sugar_cane");
                if (p.getInventory().contains(Material.SUGAR_CANE, plugin.getConfig().getInt("sugar_cane" + "." + lvl))) {
                    p.getInventory().removeItem(new ItemStack(Material.SUGAR_CANE, plugin.getConfig().getInt("sugar_cane" + "." + lvl)));
                    if (data.getConfig().getInt("max_upgrade_lvl") > data.getConfig().getInt(info.getUuid() + ".upgrades" + ".sugar_cane")) {
                        if (data.getConfig().contains(info.getUuid() + ".upgrades" + ".sugar_cane"))
                            lvl = data.getConfig().getInt(info.getUuid() + ".upgrades" + ".sugar_cane");
                        data.getConfig().set(info.getUuid() + ".upgrades" + ".sugar_cane", (lvl + 1));
                        data.saveConfig();
                        lore.set(9, "??eSugar Cane Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".sugar_cane"));
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        p.openInventory(gui);
                        updateInventory(p);
                        break;
                    }
                }
            }
            case 43: {
                lvl = data.getConfig().getInt(info.getUuid() + ".upgrades" + ".nether_wart");
                if (p.getInventory().contains(Material.NETHER_WART, plugin.getConfig().getInt("nether_wart" + "." + lvl))) {
                    p.getInventory().removeItem(new ItemStack(Material.NETHER_WART, plugin.getConfig().getInt("nether_wart" + "." + lvl)));
                    if (data.getConfig().getInt("max_upgrade_lvl") > data.getConfig().getInt(info.getUuid() + ".upgrades" + ".nether_wart")) {
                        if (data.getConfig().contains(info.getUuid() + ".upgrades" + ".nether_wart"))
                            lvl = data.getConfig().getInt(info.getUuid() + ".upgrades" + ".nether_wart");
                        data.getConfig().set(info.getUuid() + ".upgrades" + ".nether_wart", (lvl + 1));
                        data.saveConfig();
                        lore.set(9, "??eNether Wart Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".nether_wart"));
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        p.openInventory(gui);
                        updateInventory(p);
                        break;
                    }
                }
            }
        }
    }






    public void replantCrop(Location location, Material cropBlockType) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> location.getBlock().setType(cropBlockType), 5l);
    }

    public void removeSeed(Player player, Material seedType) {
        int seedIndexLocation = -1;
        ItemStack currentItems;
        PlayerInventory playerInventory = player.getInventory();

        for (int slotIndex = 0; slotIndex < playerInventory.getSize(); slotIndex++) {
            currentItems = playerInventory.getItem(slotIndex);
            if (currentItems != null) {
                if (currentItems.getType() == seedType) {
                    seedIndexLocation = slotIndex;

                    slotIndex = playerInventory.getSize();
                }
            }
        }
        // removing seed
        if (seedIndexLocation != -1) {
            ItemStack seedItemStack = playerInventory.getItem(seedIndexLocation);
            if (seedItemStack != null) {
                int seedAmount = seedItemStack.getAmount();
                seedItemStack.setAmount(seedAmount - 1);
            }
        }

    }

    // is the seed in the inventory ?
    public boolean isSeedInInv(Player player, Material cropBlockType) {
        PlayerInventory playerInventory = player.getInventory();
        if (cropBlockType == Material.WHEAT) {
            return playerInventory.contains(Material.WHEAT_SEEDS);
        }
        if (cropBlockType == Material.BEETROOTS) {
            return playerInventory.contains(Material.BEETROOT_SEEDS);
        }
        if (cropBlockType == Material.CARROTS) {
            return playerInventory.contains(Material.CARROT);
        }
        if (cropBlockType == Material.POTATOES) {
            return playerInventory.contains(Material.POTATO);
        }

        return false;
    }

    // Getting the seeds material from the crop block
    public Material getSeedMaterial(Material cropBlockType) {
        if (cropBlockType == Material.WHEAT) {
            return Material.WHEAT_SEEDS;
        }
        if (cropBlockType == Material.CARROTS) {
            return Material.CARROTS;
        }
        if (cropBlockType == Material.POTATOES) {
            return Material.POTATOES;
        }
        if (cropBlockType == Material.BEETROOTS) {
            return Material.BEETROOT_SEEDS;
        }
        if (cropBlockType == Material.NETHER_WART) {
            return Material.NETHER_WART;
        }
        return null;
    }
    //Hoe GUI
    public static void openNewGui(Player p) {
        gui = Bukkit.createInventory(null, 54, "MENU");
        data = new DataManager(JavaPlugin.getPlugin(Main.class));
        PersistentDataContainer container = p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "harvester_hoe_uuid");
        Information info = container.get(key, new InformationDataType());
        ItemStack GrayGlasspane = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemMeta meta = GrayGlasspane.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "");
        GrayGlasspane.setItemMeta(meta);
        ItemStack WheatUpgrade = new ItemStack(Material.WHEAT, 1);
        ItemMeta meta1 = WheatUpgrade.getItemMeta();
        assert meta1 != null;
        meta1.setDisplayName(ChatColor.YELLOW + "Wheat Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".wheat"));
        WheatUpgrade.setItemMeta(meta1);
        ItemStack BeetRootUpgrade = new ItemStack(Material.BEETROOT, 1);
        ItemMeta beetRootMeta = BeetRootUpgrade.getItemMeta();
        assert beetRootMeta != null;
        beetRootMeta.setDisplayName(ChatColor.RED + "BeetRoot Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".beetroot"));
        BeetRootUpgrade.setItemMeta(beetRootMeta);
        ItemStack CarrotUpgrade = new ItemStack(Material.CARROT, 1);
        ItemMeta carrotMeta = CarrotUpgrade.getItemMeta();
        assert carrotMeta != null;
        carrotMeta.setDisplayName(ChatColor.GOLD + "Carrot Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".carrots"));
        CarrotUpgrade.setItemMeta(carrotMeta);
        ItemStack PotatoUpgrade = new ItemStack(Material.POTATO, 1);
        ItemMeta potatoMeta = PotatoUpgrade.getItemMeta();
        assert potatoMeta != null;
        potatoMeta.setDisplayName(ChatColor.YELLOW + "Potato Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".potatoes"));
        PotatoUpgrade.setItemMeta(potatoMeta);
        ItemStack KelpUpgrade = new ItemStack(Material.KELP, 1);
        ItemMeta kelpMeta = KelpUpgrade.getItemMeta();
        assert kelpMeta != null;
        kelpMeta.setDisplayName(ChatColor.DARK_GREEN + "Kelp Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".kelp"));
        KelpUpgrade.setItemMeta(kelpMeta);
        ItemStack BambooUpgrade = new ItemStack(Material.BAMBOO, 1);
        ItemMeta bambooMeta = BambooUpgrade.getItemMeta();
        assert bambooMeta != null;
        bambooMeta.setDisplayName(ChatColor.GREEN + "Bamboo Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".bamboo"));
        BambooUpgrade.setItemMeta(bambooMeta);

        ItemStack SugarCaneUpgrade = new ItemStack(Material.SUGAR_CANE, 1);
        ItemMeta sugarCaneMeta = SugarCaneUpgrade.getItemMeta();
        assert sugarCaneMeta != null;
        sugarCaneMeta.setDisplayName(ChatColor.DARK_GREEN + "Sugar Cane Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".sugar_cane"));
        SugarCaneUpgrade.setItemMeta(sugarCaneMeta);

        ItemStack NetherWartUpgrade = new ItemStack(Material.NETHER_WART, 1);
        ItemMeta netherwartMeta = NetherWartUpgrade.getItemMeta();
        assert netherwartMeta != null;
        netherwartMeta.setDisplayName(ChatColor.DARK_GREEN + "Nether Wart Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".nether_wart"));
        NetherWartUpgrade.setItemMeta(netherwartMeta);

        ItemStack up1 = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta up1Meta = up1.getItemMeta();
        assert up1Meta != null;
        up1Meta.setDisplayName(ChatColor.DARK_PURPLE + "Upgrade 1");
        up1.setItemMeta(up1Meta);

        ItemStack up2 = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta up2Meta = up2.getItemMeta();
        assert up2Meta != null;
        up2Meta.setDisplayName(ChatColor.DARK_PURPLE + "Upgrade 2");
        up2.setItemMeta(up2Meta);

        ItemStack up3 = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta up3Meta = up3.getItemMeta();
        assert up3Meta != null;
        up3Meta.setDisplayName(ChatColor.DARK_PURPLE + "Upgrade 3");
        up3.setItemMeta(up3Meta);

        ItemStack up4 = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta up4Meta = up4.getItemMeta();
        assert up4Meta != null;
        up4Meta.setDisplayName(ChatColor.DARK_PURPLE + "Upgrade 4");
        up4.setItemMeta(up4Meta);

        ItemStack up5 = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta up5Meta = up5.getItemMeta();
        assert up5Meta != null;
        up5Meta.setDisplayName(ChatColor.DARK_PURPLE + "Upgrade 5");
        up5.setItemMeta(up5Meta);

        ItemStack up6 = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta up6Meta = up6.getItemMeta();
        assert up6Meta != null;
        up6Meta.setDisplayName(ChatColor.DARK_PURPLE + "Upgrade 6");
        up6.setItemMeta(up6Meta);

        ItemStack up7 = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta up7Meta = up7.getItemMeta();
        assert up7Meta != null;
        up7Meta.setDisplayName(ChatColor.DARK_PURPLE + "Upgrade 7");
        up7.setItemMeta(up7Meta);

        ItemStack up8 = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta up8Meta = up8.getItemMeta();
        assert up8Meta != null;
        up8Meta.setDisplayName(ChatColor.DARK_PURPLE + "Upgrade 8");
        up8.setItemMeta(up8Meta);

        for (int i = 0; i < gui.getContents().length; i++) {
            ItemStack is = gui.getItem(i);
            if (is == null || is.getType() == Material.AIR) {
                gui.setItem(i, GrayGlasspane);
            }
        }
        ItemStack Collecter = new ItemStack(Material.BARREL, 1);
        ItemMeta meta2 = Collecter.getItemMeta();
        meta2.setDisplayName(ChatColor.GOLD + "Crops Collected:");
        List<String> lore1 = new ArrayList<>();
        lore1.add("??9Wheat : " + data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".wheat"));// 0
        lore1.add("??9Carrots : " + data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".carrots")); // 1
        lore1.add("??9Potatoes : " + data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".potatoes")); // 2
        lore1.add("??9Beetroot : " + data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".beetroot")); // 3
        lore1.add("??9Kelp : " + data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".kelp")); // 4
        lore1.add("??9Bamboo : " + data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".bamboo")); // 5
        lore1.add("??9Sugar Cane : " + data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".sugar_cane")); // 7
        lore1.add("??9Nether Wart : " + data.getConfig().getInt(info.getUuid() + ".amount_mined" + ".nether_wart")); // 7
        meta2.setLore(lore1);
        Collecter.setItemMeta(meta2);
        ItemStack Upgrader = new ItemStack(Material.ANVIL, 1);
        ItemMeta HoeUpgrademeta = Upgrader.getItemMeta();
        HoeUpgrademeta.setDisplayName(ChatColor.GOLD + "Harvester Hoe Upgrades:");
        List<String> HoeUpgradelore = new ArrayList<>();
        HoeUpgradelore.add("??9Add upgrades to your Hoe");// 0
        HoeUpgrademeta.setLore(HoeUpgradelore);
        Upgrader.setItemMeta(HoeUpgrademeta);

        //Enchants
        gui.setItem(10, up1);
        gui.setItem(11, up2);
        gui.setItem(19, up3);
        gui.setItem(20, up4);
        gui.setItem(28, up5);
        gui.setItem(29, up6);
        gui.setItem(37, up7);
        gui.setItem(38, up8);



        //Crops
        gui.setItem(15, WheatUpgrade);
        gui.setItem(16, BeetRootUpgrade);
        gui.setItem(24, CarrotUpgrade);
        gui.setItem(25, PotatoUpgrade);
        gui.setItem(33, KelpUpgrade);
        gui.setItem(34, BambooUpgrade);
        gui.setItem(42, SugarCaneUpgrade);
        gui.setItem(43, NetherWartUpgrade);
        gui.setItem(22, Collecter);
        gui.setItem(31, Upgrader);
        p.openInventory(gui);
    }




    private void updateInventory(final Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {

                if (player.getInventory().getItemInMainHand().getItemMeta() == null) return;
                PersistentDataContainer container = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
                NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "harvester_hoe_uuid");
                Information info = container.get(key, new InformationDataType());


                data = new DataManager(JavaPlugin.getPlugin(Main.class));
                if (!player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().getKeys().contains(key))
                    return;
                ItemStack WheatUpgrade = new ItemStack(Material.WHEAT, 1);
                ItemMeta WheatMeta = WheatUpgrade.getItemMeta();
                assert WheatMeta != null;
                WheatMeta.setDisplayName(ChatColor.YELLOW + "Wheat Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".wheat"));
                WheatUpgrade.setItemMeta(WheatMeta);


                ItemStack BeetRootUpgrade = new ItemStack(Material.BEETROOT, 1);
                ItemMeta beetRootMeta = BeetRootUpgrade.getItemMeta();
                assert beetRootMeta != null;
                beetRootMeta.setDisplayName(ChatColor.RED + "BeetRoot Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".beetroot"));
                BeetRootUpgrade.setItemMeta(beetRootMeta);

                ItemStack CarrotUpgrade = new ItemStack(Material.CARROT, 1);
                ItemMeta carrotMeta = CarrotUpgrade.getItemMeta();
                assert carrotMeta != null;
                carrotMeta.setDisplayName(ChatColor.GOLD + "Carrot Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".carrots"));
                CarrotUpgrade.setItemMeta(carrotMeta);

                ItemStack PotatoUpgrade = new ItemStack(Material.POTATO, 1);
                ItemMeta potatoMeta = PotatoUpgrade.getItemMeta();
                assert potatoMeta != null;
                potatoMeta.setDisplayName(ChatColor.YELLOW + "Potato Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".potatoes"));
                PotatoUpgrade.setItemMeta(potatoMeta);

                ItemStack KelpUpgrade = new ItemStack(Material.KELP, 1);
                ItemMeta kelpMeta = KelpUpgrade.getItemMeta();
                assert kelpMeta != null;
                kelpMeta.setDisplayName(ChatColor.DARK_GREEN + "Kelp Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".kelp"));
                KelpUpgrade.setItemMeta(kelpMeta);

                ItemStack BambooUpgrade = new ItemStack(Material.BAMBOO, 1);
                ItemMeta bambooMeta = BambooUpgrade.getItemMeta();
                assert bambooMeta != null;
                bambooMeta.setDisplayName(ChatColor.GREEN + "Bamboo Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".bamboo"));
                BambooUpgrade.setItemMeta(bambooMeta);

                ItemStack SugarCaneUpgrade = new ItemStack(Material.SUGAR_CANE, 1);
                ItemMeta sugarCaneMeta = SugarCaneUpgrade.getItemMeta();
                assert sugarCaneMeta != null;
                sugarCaneMeta.setDisplayName(ChatColor.DARK_GREEN + "Sugar Cane Upgrade: LVL " + data.getConfig().getInt(info.getUuid() + ".upgrades" + ".sugar_cane"));
                SugarCaneUpgrade.setItemMeta(sugarCaneMeta);

                gui.setItem(10, WheatUpgrade);
                gui.setItem(12, BeetRootUpgrade);
                gui.setItem(14, CarrotUpgrade);
                gui.setItem(16, PotatoUpgrade);
                gui.setItem(20, KelpUpgrade);
                gui.setItem(22, BambooUpgrade);
                gui.setItem(24, SugarCaneUpgrade);
            }
        }.runTaskTimer(JavaPlugin.getPlugin(Main.class), 0L, 20L);
    }
}

