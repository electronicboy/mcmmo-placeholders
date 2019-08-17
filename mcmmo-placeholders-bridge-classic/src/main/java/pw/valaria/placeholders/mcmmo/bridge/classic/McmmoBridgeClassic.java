package pw.valaria.placeholders.mcmmo.bridge.classic;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.config.Config;
import com.gmail.nossr50.config.experience.ExperienceConfig;
import com.gmail.nossr50.datatypes.database.PlayerStat;
import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.util.player.UserManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;
import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;
import pw.valaria.placeholders.mcmmo.bridge.classic.data.SkillType;
import pw.valaria.placeholders.mcmmo.bridge.data.LeaderboardStat;

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
    public String getXpRate(Player player) {
        return String.valueOf(ExperienceConfig.getInstance().getExperienceGainsGlobalMultiplier());
    }

    @Override
    public String isExpEventActive(Player player) {
        return mcMMO.p.isXPEventEnabled() ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
    }

    @Override
    public LeaderboardStat getLeaderboardStat(ISkillType type, int rank) {
        final List<PlayerStat> playerStats = mcMMO.getDatabaseManager().readLeaderboard((com.gmail.nossr50.datatypes.skills.SkillType) type.getNativeSkill(), rank, 1);
        if (playerStats.size() == 0) return null;
        PlayerStat stat = playerStats.get(0);
        return new LeaderboardStat(stat.name, stat.statVal);
    }


}
