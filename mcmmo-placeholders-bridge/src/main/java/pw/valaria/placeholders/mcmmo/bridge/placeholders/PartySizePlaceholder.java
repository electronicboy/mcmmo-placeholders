package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;

public class PartySizePlaceholder implements Placeholder {
    private McmmoBridge bridge;

    public PartySizePlaceholder(McmmoBridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public String process(Player player, String params) {
        Integer partySize = bridge.getPartySize(player);
        return (partySize == null) ? "" : partySize.toString();
    }

    @Override
    public String getName() {
        return "party_size";
    }
}
