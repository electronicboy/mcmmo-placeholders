package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;
import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;

public class PartyLevelPlaceholder implements Placeholder {
    private McmmoBridge bridge;

    public PartyLevelPlaceholder(McmmoBridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public String process(Player player, String params) {
        Integer partyLevel = bridge.getPartyLevel(player);
        return (partyLevel == null) ? "" : partyLevel.toString();
    }

    @Override
    public String getName() {
        return "party_level";
    }
}
