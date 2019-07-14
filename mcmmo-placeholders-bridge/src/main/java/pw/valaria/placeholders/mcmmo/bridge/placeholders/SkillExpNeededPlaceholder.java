package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;
import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;

public class SkillExpNeededPlaceholder implements Placeholder {

    private McmmoBridge bridge;
    private ISkillType skillType;

    public SkillExpNeededPlaceholder(McmmoBridge bridge, ISkillType skillType) {

        this.bridge = bridge;
        this.skillType = skillType;
    }

    @Override
    public String process(Player player) {
        final Integer expNeeded = bridge.getExpNeeded(skillType, player);
        return (expNeeded == null) ? "" : expNeeded.toString();
    }

    @Override
    public String getName() {
        return "xp_needed_" + skillType.getSkillName();
    }
}
