package com.gmail.darastlix.commands;

import com.gmail.darastlix.data.Vector3D;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class VoroBall implements CommandExecutor
{
    private Plugin plugin;
    private Player player;
    private World world;


    public VoroBall(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("[ERROR] Only player can issue voro commands");
            return false;
        }
        if (!command.getName().equalsIgnoreCase("voro")) return false;
        player = (Player) sender;
        world = player.getWorld();

        if (strings.length <= 0) player.sendMessage(ChatColor.RED + "[ERROR] Please provide arguments");

        boolean commandReturnStatus = false;
        switch (strings[0])
        {
            case "help":
                displayHelpString();
                commandReturnStatus = true;
                break;
            case "create":
                if (strings.length <= 2)
                {
                    player.sendMessage(ChatColor.RED + "[ERROR] Insufficient number of args");
                    break;
                }

                createBall(player.getLocation(), Integer.parseInt(strings[1]), Integer.parseInt(strings[2]));
                commandReturnStatus = true;
                break;
            default:
                player.sendMessage(ChatColor.RED + "[ERROR] Wrong command, type HELP!");
        }
        return commandReturnStatus;
    }

    void displayHelpString()
    {
        player.sendMessage(ChatColor.YELLOW + "Command options:");
        player.sendMessage(ChatColor.YELLOW + "/create [R] [P] - creates voroball of radius R using P points");
    }

    void createBall(Location playerLocation, int radius, int numberOfPoints)
    {
        new VoroBallRunner(world, new Vector3D(playerLocation), radius, numberOfPoints).runTask(plugin);
    }
}
