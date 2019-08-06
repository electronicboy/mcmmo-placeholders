package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;
import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;

public class XpRatePlaceholder implements Placeholder {
    private McmmoBridge bridge;

    public <S extends ISkillType> XpRatePlaceholder(McmmoBridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public String process(Player player, String params) {
        return bridge.getXpRate(player);
    }

    @Override
    public String getName() {
        return "xprate";
    }
}
