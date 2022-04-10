package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;
import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;

public class PartyXpPlaceholder implements Placeholder {
    private McmmoBridge bridge;

    public PartyXpPlaceholder(McmmoBridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public String process(Player player, String params) {
        Float partyXp = bridge.getPartyXp(player);
        return (partyXp == null) ? "" : partyXp.toString();
    }

    @Override
    public String getName() {
        return "party_xp";
    }
}
