package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;

public class PowerLevelCapPlaceholder implements Placeholder {
    private McmmoBridge bridge;

    public PowerLevelCapPlaceholder(McmmoBridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public String process(Player player, String params) {
        Integer cap = bridge.getPowerCap(player);
        return (cap == null) ? "" : cap.toString();
    }

    @Override
    public String getName() {
        return "power_level_cap";
    }
}
