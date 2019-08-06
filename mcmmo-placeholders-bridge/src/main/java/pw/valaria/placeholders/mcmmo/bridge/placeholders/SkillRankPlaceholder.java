package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;
import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;

public class SkillRankPlaceholder implements Placeholder {
    private final McmmoBridge bridge;
    private final ISkillType skill;

    public SkillRankPlaceholder(McmmoBridge bridge, ISkillType skill) {
        this.bridge = bridge;
        this.skill = skill;
    }

    @Override
    public String process(Player player, String params) {
        Integer rank = bridge.getRank(skill, player);
        return (rank == null) ? "" : rank.toString();
    }

    @Override
    public String getName() {
        return "rank_" + skill.getSkillName();
    }
}
