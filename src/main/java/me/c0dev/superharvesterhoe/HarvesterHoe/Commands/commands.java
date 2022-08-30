package me.c0dev.superharvesterhoe.HarvesterHoe.Commands;

import me.c0dev.superharvesterhoe.HarvesterHoe.Item.items;
import me.c0dev.superharvesterhoe.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class commands implements CommandExecutor
{
    public Main main;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;

            if (player.isOp() || player.hasPermission("giveSHarvesterHoe"))
            {
                ItemStack item = items.CreateHarvesterHoe();
                player.getInventory().addItem(item);
                player.sendMessage(ChatColor.GREEN + "You have been given a Harvester Hoe");
            }
            else{
            player.sendMessage(ChatColor.RED + "You do not have the correct permissions!");
            }
        }
        else{
            main.getLogger().info("You have to be a player to run the command!");
        }
        return true;
    }
}
