package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;
import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;
import pw.valaria.placeholders.mcmmo.bridge.placeholders.Placeholder;

public class XpEventActivePlaceholder implements Placeholder {
    private McmmoBridge bridge;

    public <S extends ISkillType> XpEventActivePlaceholder(McmmoBridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public String process(Player player) {
        return bridge.isExpEventActive(player);
    }

    @Override
    public String getName() {
        return "is_xp_event_active";
    }
}
