package pw.valaria.placeholders.mcmmo.bridge;

import org.bukkit.entity.Player;

import java.util.Collection;

import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;
import pw.valaria.placeholders.mcmmo.bridge.placeholders.*;

public abstract class McmmoBridge<S extends ISkillType> {
    McMMOPlaceholderExpansion expansion;
    /**
     * @return Can this IMPL hook the current version of mcmmo
     */
    protected abstract boolean canHook();

    public void init(McMMOPlaceholderExpansion expansion) {
        this.expansion = expansion;
        this.init();
        // Use postInit to create placeholders, etc
        this.postInit();
    }

    protected void postInit() {

        getSkills().forEach((skill) -> {
            // %mcmmo_level_<skillname>%
            getExpansion().registerPlaceholder(new SkillLevelPlaceholder(this, skill));

            //%mcmmo_xp_needed_<skillname>%
            getExpansion().registerPlaceholder(new SkillExpNeededPlaceholder(this, skill));

            //%mcmmo_xp_<skillname>%
            getExpansion().registerPlaceholder(new SkillExpPlaceholder(this, skill));

            //%mcmmo_xp_remaining_<skillname>%
            getExpansion().registerPlaceholder(new SkillExpRemainingPlaceholder(this, skill));

            //%mcmmo_rank_<skillname>%
            getExpansion().registerPlaceholder(new SkillRankPlaceholder(this, skill));

            //%mcmmo_xprate_<skillname>%
            getExpansion().registerPlaceholder(new SkillXpRatePlaceholder(this, skill));
        });


        //%mcmmo_power_level%
        getExpansion().registerPlaceholder(new PowerLevelPlaceholder(this));

        // %mcmmo_power_level_cap%
        getExpansion().registerPlaceholder(new PowerLevelCapPlaceholder(this));

        // %mcmmo_in_party%
        getExpansion().registerPlaceholder(new PartyIsMemberPlaceholder(this));

        /// %mcmmo_party_name%
        getExpansion().registerPlaceholder(new PartyNamePlaceholder(this));

        // %mcmmo_is_party_leader%
        getExpansion().registerPlaceholder(new PartyIsLeaderPlaceholder(this));

        // %mcmmo_party_leader%
        getExpansion().registerPlaceholder(new PartyLeaderPlaceholder(this));

        // %mcmmo_party_size%
        getExpansion().registerPlaceholder(new PartySizePlaceholder(this));

        // %mcmmo_party_level%
        getExpansion().registerPlaceholder(new PartyLevelPlaceholder(this));

        // %mcmmo_party_xp%
        getExpansion().registerPlaceholder(new PartyXpPlaceholder(this));

        // %mcmmo_is_xp_event_active%
        getExpansion().registerPlaceholder(new XpEventActivePlaceholder(this));
        // %mcmmo_xprate%
        getExpansion().registerPlaceholder(new XpRatePlaceholder(this));
    };

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

    public abstract Float getPartyXp(Player player);

    public abstract Integer getPartyLevel(Player player);

    public abstract String getXpRate(Player player);

    public abstract String getSkillXpRate(ISkillType skill, Player player);

    public abstract String isExpEventActive(Player player);
}
