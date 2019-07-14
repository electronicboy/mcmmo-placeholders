package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;

public class PartyLeaderPlaceholder implements Placeholder {
    private McmmoBridge bridge;

    public PartyLeaderPlaceholder(McmmoBridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public String process(Player player) {
        return StringUtils.stripToEmpty(bridge.getPartyLeader(player));
    }

    @Override
    public String getName() {
        return "party_leader";
    }
}
