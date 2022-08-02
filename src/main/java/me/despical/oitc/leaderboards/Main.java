package me.despical.oitc.leaderboards;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * @author Despical
 * <p>
 * Created at 30.07.2022
 */
public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		PluginManager manager = getServer().getPluginManager();

		if (!manager.isPluginEnabled("OITC")) {
			log("One in the Chamber could not found, disabling the plugin!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		if (!manager.isPluginEnabled("PlaceholderAPI")) {
			log("PlaceholderAPI could not found, disabling the plugin!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		saveDefaultConfig();
		log("Created config file.");

		new PlaceholderManager(this);
		log("Hooked in to PlaceholderAPI.");
	}

	private void log(String message) {
		getLogger().log(Level.INFO, message);
	}
}