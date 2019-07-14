package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;
import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;

public class SkillLevelPlaceholder implements Placeholder {
    private McmmoBridge bridge;
    private ISkillType skillType;

    public SkillLevelPlaceholder(McmmoBridge bridge, ISkillType skillType) {
        this.bridge = bridge;
        this.skillType = skillType;
    }

    @Override
    public String process(Player p) {
        final Integer skillLevel = bridge.getSkillLevel(skillType, p);
        return (skillLevel == null) ? "" : skillLevel.toString();
    }

    @Override
    public String getName() {
        return "level_" + skillType.getSkillName();
    }
}
