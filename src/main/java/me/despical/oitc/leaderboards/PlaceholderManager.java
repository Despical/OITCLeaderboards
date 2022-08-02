package me.despical.oitc.leaderboards;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.despical.commons.number.NumberUtils;
import me.despical.oitc.api.StatsStorage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

/**
 * @author Despical
 * <p>
 * Created at 30.07.2022
 */
public class PlaceholderManager extends PlaceholderExpansion {

	private final Main plugin;
	private final String emptyMessage, unknownPlayerMessage;

	public PlaceholderManager(Main plugin) {
		this.plugin = plugin;
		this.emptyMessage = plugin.getConfig().getString("Empty-Value");
		this.unknownPlayerMessage= plugin.getConfig().getString("Unknown-Player");

		this.register();
	}

	@Override
	public boolean persist() {
		return true;
	}

	@NotNull
	@Override
	public String getIdentifier() {
		return "oitclb";
	}

	@NotNull
	@Override
	public String getAuthor() {
		return "Despical";
	}

	@NotNull
	@Override
	public String getVersion() {
		return plugin.getDescription().getVersion();
	}

	@Override
	public String onPlaceholderRequest(Player player, @NotNull String id) {
		id = id.toLowerCase();

		final int subId = NumberUtils.getInt(id.substring(id.lastIndexOf('_') + 1));

		if (id.contains("killer")) {
			final String request = handlePlaceholderRequest(id, "top_killer_name_", "top_killer_value_", subId, StatsStorage.StatisticType.KILLS);

			if (request != null) {
				return request;
			}
		}

		if (id.contains("deaths")) {
			final String request = handlePlaceholderRequest(id, "top_deaths_name_", "top_deaths_value_", subId, StatsStorage.StatisticType.DEATHS);

			if (request != null) {
				return request;
			}
		}

		if (id.contains("wins")) {
			final String request = handlePlaceholderRequest(id, "top_wins_name_", "top_wins_value_", subId, StatsStorage.StatisticType.WINS);

			if (request != null) {
				return request;
			}
		}

		if (id.contains("games_played")) {
			final String request = handlePlaceholderRequest(id, "top_games_played_name_", "top_games_played_value_", subId, StatsStorage.StatisticType.GAMES_PLAYED);

			if (request != null) {
				return request;
			}
		}

		if (id.contains("highest_score")) {
			final String request = handlePlaceholderRequest(id, "top_highest_score_name_", "top_highest_score_value_", subId, StatsStorage.StatisticType.HIGHEST_SCORE);

			if (request != null) {
				return request;
			}
		}

		if (id.contains("loses")) {
			return handlePlaceholderRequest(id, "top_loses_name_", "top_loses_value_", subId, StatsStorage.StatisticType.LOSES);
		}

		return null;
	}

	private String handlePlaceholderRequest(String id, String name, String value, int subId, StatsStorage.StatisticType statisticType) {
		final Map<UUID, Integer> stats = StatsStorage.getStats(statisticType);

		try {
			if (id.equals(name + subId)) {
				UUID current = (UUID) stats.keySet().toArray()[stats.keySet().toArray().length - subId];
				stats.remove(current);

				return plugin.getServer().getOfflinePlayer(current).getName();
			} else if (id.equals(value + subId)) {
				UUID current = (UUID) stats.keySet().toArray()[stats.keySet().toArray().length - subId];

				return Integer.toString(stats.get(current));
			}

		} catch (IndexOutOfBoundsException exception) {
			return emptyMessage;
		} catch (NullPointerException exception) {
			return unknownPlayerMessage;
		}

		return null;
	}
}