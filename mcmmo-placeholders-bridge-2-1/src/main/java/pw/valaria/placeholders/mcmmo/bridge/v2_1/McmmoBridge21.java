package pw.valaria.placeholders.mcmmo.bridge.v2_1;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.config.experience.ExperienceConfig;
import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.util.Permissions;
import com.gmail.nossr50.util.player.UserManager;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;
import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;
import pw.valaria.placeholders.mcmmo.bridge.v2_1.data.SkillType;

public class McmmoBridge21 extends McmmoBridge<SkillType> {

    private final Map<String, SkillType> skills = new LinkedHashMap<>();

    protected boolean canHook() {
        try {
            Class.forName("com.gmail.nossr50.datatypes.skills.PrimarySkillType");
        } catch (ClassNotFoundException e) {
            return false;
        }

        return true;
    }

    @Override
    protected void init() {
        // Register all skills
        for (PrimarySkillType skillType : PrimarySkillType.values()) {
            skills.put(mcMMO.p.getSkillTools().getLocalizedSkillName(skillType).toLowerCase(), new SkillType(skillType));
        }


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
        return mcMMO.p.getGeneralConfig().getPowerLevelCap();
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

    @Override
    public String getXpRate(Player player) {
        return String.valueOf(ExperienceConfig.getInstance().getExperienceGainsGlobalMultiplier());
    }

    @Override
    public String getSkillXpRate(ISkillType skillType, Player player) {
        final McMMOPlayer user = UserManager.getPlayer(player);
        if (user == null) return null;

        PrimarySkillType skill = (PrimarySkillType) skillType.getNativeSkill();
        double modifier = 1.0F;

        if (Permissions.customXpBoost(player, skill))
            modifier = ExperienceConfig.getInstance().getCustomXpPerkBoost();
        else if (Permissions.quadrupleXp(player, skill))
            modifier = 4;
        else if (Permissions.tripleXp(player, skill))
            modifier = 3;
        else if (Permissions.doubleAndOneHalfXp(player, skill))
            modifier = 2.5;
        else if (Permissions.doubleXp(player, skill))
            modifier = 2;
        else if (Permissions.oneAndOneHalfXp(player, skill))
            modifier = 1.5;
        else if (Permissions.oneAndOneTenthXp(player, skill))
            modifier = 1.1;

        return String.valueOf(modifier);
    }

    @Override
    public String isExpEventActive(Player player) {
        return mcMMO.p.isXPEventEnabled() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
    }


}
