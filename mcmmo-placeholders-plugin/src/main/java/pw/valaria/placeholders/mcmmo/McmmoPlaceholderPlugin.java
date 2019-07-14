package pw.valaria.placeholders.mcmmo;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;
import pw.valaria.placeholders.mcmmo.bridge.v2_1.McmmoBridge21;

public class McmmoPlaceholderPlugin extends JavaPlugin implements Listener {
    McmmoBridge bridge = new McmmoBridge21();

    @Override
    public void onEnable() {
        // TODO: Implement actual abstraction handling
        bridge.init();
        Bukkit.getPluginManager().registerEvents(this, this);

    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(this, (task) -> {
            Bukkit.getPlayer(event.getPlayer().getUniqueId()).sendMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), "excav: %mCmmo_level_excavatioN% : %mcmmo_xp_remaining_excavation% : %mcmmo_xp_needed_excavation%"));
        }, 5 * 20);


    }

}
