package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;
import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;

public class SkillExpRemainingPlaceholder implements Placeholder {
    private final McmmoBridge bridge;
    private final ISkillType skill;

    public SkillExpRemainingPlaceholder(McmmoBridge bridge, ISkillType skill) {
        this.bridge = bridge;
        this.skill = skill;
    }

    @Override
    public String process(Player player) {
        final Integer expRemaining = bridge.getExpRemaining(skill, player);
        return (expRemaining == null) ? "" : expRemaining.toString();
    }

    @Override
    public String getName() {
        return "xp_remaining_" + skill.getSkillName();
    }
}
