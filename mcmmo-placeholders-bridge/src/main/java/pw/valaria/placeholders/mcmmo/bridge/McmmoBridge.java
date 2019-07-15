package pw.valaria.placeholders.mcmmo.bridge;

import org.bukkit.entity.Player;

import java.util.Collection;

import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;

public abstract class McmmoBridge<S extends ISkillType> {
    McMMOPlaceholderExpansion expansion;

    /**
     * @return Can this IMPL hook the current version of mcmmo
     */
    protected abstract boolean canHook();

    public void init(McMMOPlaceholderExpansion expansion) {
        this.expansion = expansion;
        this.init();
    }

    protected abstract void init();

    public abstract Collection<S> getSkills();

    public abstract Integer getSkillLevel(ISkillType skillType, Player p);

    public McMMOPlaceholderExpansion getExpansion() {
        return expansion;
    }

    public abstract Integer getExpNeeded(ISkillType skillType, Player player);

    public abstract Integer getExp(ISkillType skill, Player player);

    public abstract Integer getExpRemaining(ISkillType skill, Player player);

    public abstract Integer getRank(ISkillType skill, Player player);

    public abstract Integer getPowerLevel(Player player);

    public abstract Integer getPowerCap(Player player);

    public abstract String getPartyName(Player player);

    public abstract String getPartyLeader(Player player);

    public abstract Integer getPartySize(Player player);
}
