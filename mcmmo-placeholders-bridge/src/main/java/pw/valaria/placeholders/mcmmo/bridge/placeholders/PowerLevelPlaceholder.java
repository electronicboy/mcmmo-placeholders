package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;

public class PowerLevelPlaceholder implements Placeholder {
    private McmmoBridge bridge;

    public PowerLevelPlaceholder(McmmoBridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public String process(Player player, String params) {
        Integer powerLevel = bridge.getPowerLevel(player);
        return (powerLevel == null) ? "" : powerLevel.toString();
    }

    @Override
    public String getName() {
        return "power_level";
    }
}
