package pw.valaria.placeholders.mcmmo;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;
import pw.valaria.placeholders.mcmmo.bridge.v2_1.McmmoBridge21;

public class McmmoPlaceholderPlugin extends JavaPlugin implements Listener {
    McmmoBridge bridge = new McmmoBridge21();

    @Override
    public void onEnable() {
        // TODO: Implement actual abstraction handling
        bridge.init();
    }

}
