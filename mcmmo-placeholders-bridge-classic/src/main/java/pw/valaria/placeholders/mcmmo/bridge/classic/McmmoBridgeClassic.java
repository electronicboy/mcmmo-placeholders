package pw.valaria.placeholders.mcmmo.bridge.classic;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.config.Config;
import com.gmail.nossr50.config.experience.ExperienceConfig;
import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.util.Permissions;
import com.gmail.nossr50.util.player.UserManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;
import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;
import pw.valaria.placeholders.mcmmo.bridge.classic.data.SkillType;

public class McmmoBridgeClassic extends McmmoBridge<SkillType> {
    private static Logger LOGGER = LogManager.getLogManager().getLogger("mcmmo-placeholders");

    private final mcMMO mcMMOPlugin;
    private final Map<String, SkillType> skills = new LinkedHashMap<>();

    public McmmoBridgeClassic() {
        mcMMOPlugin = (mcMMO) Bukkit.getPluginManager().getPlugin("mcMMO");
    }

    protected boolean canHook() {
        try {
            Class.forName("com.gmail.nossr50.datatypes.skills.SkillType");
        } catch (ClassNotFoundException e) {
            return false;
        }

        return true;
    }

    @Override
    protected void init() {
        // Register all skills
        for (com.gmail.nossr50.datatypes.skills.SkillType skillType: com.gmail.nossr50.datatypes.skills.SkillType.values()) {
            skills.put(skillType.getName().toLowerCase(), new SkillType(skillType));
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
        return user.getSkillLevel((com.gmail.nossr50.datatypes.skills.SkillType) skillType.getNativeSkill());
    }

    @Override
    public Integer getExpNeeded(ISkillType skillType, Player player) {
        final McMMOPlayer user = UserManager.getPlayer(player);
        if (user == null) return null;
        return user.getXpToLevel((com.gmail.nossr50.datatypes.skills.SkillType) skillType.getNativeSkill());
    }

    @Override
    public Integer getExp(ISkillType skill, Player player) {
        final McMMOPlayer user = UserManager.getPlayer(player);
        if (user == null) return null;

        return user.getSkillXpLevel((com.gmail.nossr50.datatypes.skills.SkillType) skill.getNativeSkill());
    }


    @Override
    public Integer getExpRemaining(ISkillType skillType, Player player) {
        final McMMOPlayer user = UserManager.getPlayer(player);
        if (user == null) return null;
        int current = user.getSkillXpLevel((com.gmail.nossr50.datatypes.skills.SkillType) skillType.getNativeSkill());
        int needed = user.getXpToLevel((com.gmail.nossr50.datatypes.skills.SkillType) skillType.getNativeSkill());

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

    @Override
    public Float getPartyXp(Player player) {
        final McMMOPlayer user = UserManager.getOfflinePlayer(player);
        if (user == null) return null;
        final Party party = user.getParty();
        return (party == null) ? null : party.getXp();
    }

    @Override
    public Integer getPartyLevel(Player player) {
        final McMMOPlayer user = UserManager.getPlayer(player);
        if (user == null) return null;
        final Party party = user.getParty();
        return (party == null) ? null : party.getLevel();
    }

    @Override
    public String getXpRate(Player player) {
        return String.valueOf(ExperienceConfig.getInstance().getExperienceGainsGlobalMultiplier());
    }

    @Override
    public String getSkillXpRate(ISkillType skillType, Player player) {
        final McMMOPlayer user = UserManager.getPlayer(player);
        if (user == null) return null;

        com.gmail.nossr50.datatypes.skills.SkillType skill = (com.gmail.nossr50.datatypes.skills.SkillType) skillType.getNativeSkill();
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
