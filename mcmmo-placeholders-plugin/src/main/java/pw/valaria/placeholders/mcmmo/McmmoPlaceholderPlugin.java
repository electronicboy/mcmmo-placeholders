package pw.valaria.placeholders.mcmmo;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import pw.valaria.placeholders.mcmmo.bridge.McMMOPlaceholderExpansion;

public class McmmoPlaceholderPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // TODO: Implement actual abstraction handling
        new McMMOPlaceholderExpansion();
    }

}
