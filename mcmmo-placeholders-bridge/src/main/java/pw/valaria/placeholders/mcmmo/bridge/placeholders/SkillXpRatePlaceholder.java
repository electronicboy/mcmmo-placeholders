package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;
import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;
import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;

public class SkillXpRatePlaceholder implements Placeholder {
    private McmmoBridge bridge;
    private ISkillType skillType;

    public SkillXpRatePlaceholder(McmmoBridge bridge, ISkillType skillType) {
        this.bridge = bridge;
        this.skillType = skillType;
    }

    @Override
    public String process(Player p, String params) {
        final String skillLevel = bridge.getSkillXpRate(skillType, p);
        return (skillLevel == null) ? "" : skillLevel;
    }

    @Override
    public String getName() {
        return "xprate_" + skillType;
    }
}
