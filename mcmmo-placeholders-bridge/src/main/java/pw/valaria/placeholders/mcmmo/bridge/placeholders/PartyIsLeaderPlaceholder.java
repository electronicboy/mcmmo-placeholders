package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;

public class PartyIsLeaderPlaceholder implements Placeholder {

    private McmmoBridge bridge;

    public PartyIsLeaderPlaceholder(McmmoBridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public String process(Player player) {
        String leader = bridge.getPartyLeader(player);
        return (leader.equals(player.getName())) ? "true" : "false";
    }
    @Override
    public String getName() {
        return "is_party_leader";
    }
}
