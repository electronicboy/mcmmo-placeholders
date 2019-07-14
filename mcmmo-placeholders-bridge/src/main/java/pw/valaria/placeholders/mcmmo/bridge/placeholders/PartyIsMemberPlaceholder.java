package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;

public class PartyIsMemberPlaceholder implements Placeholder {
    private McmmoBridge bridge;

    public PartyIsMemberPlaceholder(McmmoBridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public String process(Player player) {
        return (bridge.getPartyName(player) == null) ? "false" : "true";
    }

    @Override
    public String getName() {
        return "in_party";
    }
}
