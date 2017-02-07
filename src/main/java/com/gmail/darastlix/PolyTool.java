package com.gmail.darastlix;

import com.gmail.darastlix.commands.VoroBall;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PolyTool extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();

        registerCommands();
        //registerEvents();
        //registerConfig();

        logger.info(pdfFile.getName() + " has been enabled!");
    }

    @Override
    public void onDisable()
    {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();
        logger.info(pdfFile.getName() + " has been disabled!");
    }

    public void registerCommands()
    {
        getCommand("voro").setExecutor(new VoroBall(this));
    }

    public void registerEvents()
    {
        //no events
    }

    public void registerConfig()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
