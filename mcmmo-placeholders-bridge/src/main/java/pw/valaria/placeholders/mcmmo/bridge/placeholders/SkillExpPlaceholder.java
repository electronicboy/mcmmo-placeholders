package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;
import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;

public class SkillExpPlaceholder implements Placeholder {
    private final McmmoBridge bridge;
    private final ISkillType skill;

    public SkillExpPlaceholder(McmmoBridge bridge, ISkillType skill) {
        this.bridge = bridge;
        this.skill = skill;
    }


    @Override
    public String process(Player player) {
        Integer exp = bridge.getExp(skill, player);
        return (exp == null) ? "" : exp.toString();
    }

    @Override
    public String getName() {
        return "xp_" + skill.getSkillName();
    }
}
