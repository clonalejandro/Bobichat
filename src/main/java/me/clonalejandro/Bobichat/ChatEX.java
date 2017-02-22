package me.clonalejandro.Bobichat;

import me.clonalejandro.Bobichat.Command.CommandManager;
import me.clonalejandro.Bobichat.Plugins.PermissionsPlugin;
import me.clonalejandro.Bobichat.Plugins.PluginManager;
import me.clonalejandro.Bobichat.Utils.Config;
import me.clonalejandro.Bobichat.Utils.Utils;
import de.thejeterlp.bukkit.updater.Updater; 
import java.io.File;

import me.clonalejandro.Bobichat.Utils.Locales;
import org.bukkit.plugin.java.JavaPlugin;
 
/**
 * @author TheJeterLP
 */
public class ChatEX extends JavaPlugin {
    
    private static ChatEX INSTANCE;
    private static PluginManager manager;
    private CommandManager cmanager;
    
    @Override
    public void onEnable() {
        try {
            INSTANCE = this;
            Config.load();
            debug("Loaded Config!");
            if (!Config.ENABLE.getBoolean()) {
                getServer().getPluginManager().disablePlugin(this);
                getLogger().info("disabled, check config!");
                return;
            }
            Locales.load();
            if (!new File(getDataFolder(), Config.LOCALE.getString() + "_readme.txt").exists()) {
                debug("Saving readme to " + getDataFolder().getAbsolutePath());
                saveResource("locales" + File.separator + Config.LOCALE.getString() + "_readme.txt", true);
            }
            manager = new PluginManager();
            debug("Starting Metrics/MCStats...");
            new Metrics(this).start();
            debug("Starting updater...");
            Updater u = new Updater(this, 65863, "chatex");
            u.search();
            getLogger().info("Successfully hooked into: " + PluginManager.getInstance().getName());
            debug("registering Listener...");
            if (!Utils.registerListener()) {
                getLogger().severe("No valid Listener could be found. Please see the reamde.txt for more information.");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
            cmanager = new CommandManager(this);
            cmanager.registerClass(ChatExCommandHandler.class);
            getLogger().info("is now enabled!");
        } catch (Exception e) {
            getServer().getPluginManager().disablePlugin(this);
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        getLogger().info("is now disabled!");
    }
    
    public static ChatEX getInstance() {
        return INSTANCE;
    }
    
    public static PermissionsPlugin getManager() {
        return manager;
    }
    
    public static void debug(String message) {
        if (!Config.DEBUG.getBoolean()) return;
        String output = "[DEBUG] " + message;
        getInstance().getLogger().info(output);
    }
}
