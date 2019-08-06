package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;

public class PartyNamePlaceholder implements Placeholder {
    private McmmoBridge bridge;

    public PartyNamePlaceholder(McmmoBridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public String process(Player player, String params) {
        return StringUtils.stripToEmpty(bridge.getPartyName(player));
    }

    @Override
    public String getName() {
        return "party_name";
    }
}
