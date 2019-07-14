package pw.valaria.placeholders.mcmmo.bridge.v2_1;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.config.Config;
import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.util.player.UserManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;
import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;
import pw.valaria.placeholders.mcmmo.bridge.placeholders.PartyIsLeaderPlaceholder;
import pw.valaria.placeholders.mcmmo.bridge.placeholders.PartyIsMemberPlaceholder;
import pw.valaria.placeholders.mcmmo.bridge.placeholders.PartyLeaderPlaceholder;
import pw.valaria.placeholders.mcmmo.bridge.placeholders.PartyNamePlaceholder;
import pw.valaria.placeholders.mcmmo.bridge.placeholders.PartySizePlaceholder;
import pw.valaria.placeholders.mcmmo.bridge.placeholders.PowerLevelCapPlaceholder;
import pw.valaria.placeholders.mcmmo.bridge.placeholders.PowerLevelPlaceholder;
import pw.valaria.placeholders.mcmmo.bridge.placeholders.SkillExpNeededPlaceholder;
import pw.valaria.placeholders.mcmmo.bridge.placeholders.SkillExpPlaceholder;
import pw.valaria.placeholders.mcmmo.bridge.placeholders.SkillExpRemainingPlaceholder;
import pw.valaria.placeholders.mcmmo.bridge.placeholders.SkillLevelPlaceholder;
import pw.valaria.placeholders.mcmmo.bridge.placeholders.SkillRankPlaceholder;
import pw.valaria.placeholders.mcmmo.bridge.v2_1.data.SkillType;

public class McmmoBridge21 extends McmmoBridge<SkillType> {
    private static Logger LOGGER = LogManager.getLogManager().getLogger("mcmmo-placeholders");

    private final mcMMO mcMMOPlugin;
    private Map<String, SkillType> skills = new LinkedHashMap<>();

    public McmmoBridge21() {
        mcMMOPlugin = (mcMMO) Bukkit.getPluginManager().getPlugin("mcMMO");
    }

    protected boolean canHook() {
        try {
            Class.forName("com.gmail.nossr50.datatypes.skills.PrimarySkillType");
        } catch (ClassNotFoundException e) {
            return false;
        }

        return true;
    }

    @Override
    public void init() {
        super.init();
        // Register all skills
        for (PrimarySkillType skillType : PrimarySkillType.values()) {
            skills.put(skillType.getName().toLowerCase(), new SkillType(skillType));
        }


        for (Map.Entry<String, SkillType> stringSkillTypeEntry : skills.entrySet()) {
            LOGGER.info(stringSkillTypeEntry.toString());
        }


        skills.values().forEach((skill) -> {
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
    }

    @Override
    public Collection<SkillType> getSkills() {
        return skills.values();
    }





    @Override
    public Integer getSkillLevel(ISkillType skillType, Player player) {
        final McMMOPlayer user = UserManager.getPlayer(player);
        if (user == null) return null;
        return user.getSkillLevel((PrimarySkillType) skillType.getNativeSkill());
    }

    @Override
    public Integer getExpNeeded(ISkillType skillType, Player player) {
        final McMMOPlayer user = UserManager.getPlayer(player);
        if (user == null) return null;
        return user.getXpToLevel((PrimarySkillType) skillType.getNativeSkill());
    }

    @Override
    public Integer getExp(ISkillType skill, Player player) {
        final McMMOPlayer user = UserManager.getPlayer(player);
        if (user == null) return null;

        return user.getSkillXpLevel((PrimarySkillType) skill.getNativeSkill());
    }


    @Override
    public Integer getExpRemaining(ISkillType skillType, Player player) {
        final McMMOPlayer user = UserManager.getPlayer(player);
        if (user == null) return null;
        int current = user.getSkillXpLevel((PrimarySkillType) skillType.getNativeSkill());
        int needed = user.getXpToLevel((PrimarySkillType) skillType.getNativeSkill());

        return needed - current;
    }

    @Override
    public Integer getRank(ISkillType skill, Player player) {
        try {
            return ExperienceAPI.getPlayerRankSkill(player.getUniqueId(), skill.getSkillName());
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Integer getPowerLevel(Player player) {
        final McMMOPlayer user = UserManager.getPlayer(player);
        if (user == null) return null;
        return user.getPowerLevel();
    }

    @Override
    public Integer getPowerCap(Player player) {
        return Config.getInstance().getPowerLevelCap();
    }

    @Override
    public String getPartyName(Player player) {
        final McMMOPlayer user = UserManager.getPlayer(player);
        if (user == null) return null;
        final Party party = user.getParty();

        return (party == null) ? null : party.getName();
    }

    @Override
    public String getPartyLeader(Player player) {
        final McMMOPlayer user = UserManager.getPlayer(player);
        if (user == null) return null;
        final Party party = user.getParty();
        return (party == null) ? null : party.getLeader().getPlayerName();
    }

    @Override
    public Integer getPartySize(Player player) {
        final McMMOPlayer user = UserManager.getPlayer(player);
        if (user == null) return null;
        final Party party = user.getParty();
        return (party == null) ? null : party.getMembers().size();
    }


}
