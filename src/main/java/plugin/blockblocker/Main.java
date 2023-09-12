package plugin.blockblocker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.blockblocker.commands.Block;
import plugin.blockblocker.util.Listeners;

public final class Main extends JavaPlugin {

    public static String prefix;
    public static Main instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        saveConfig();

        if (!this.getConfig().contains("prefix")) {
            this.getConfig().set("prefix", "§7[§6Block-Blocker§7]");
            saveConfig();
        }
        prefix = this.getConfig().getString("prefix") + " ";

        getCommand("block").setExecutor(new Block());
        getCommand("block").setTabCompleter(new Block());

        Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveConfig();
    }
}
