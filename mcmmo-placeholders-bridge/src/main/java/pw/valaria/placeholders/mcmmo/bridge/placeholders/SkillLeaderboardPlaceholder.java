package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import pw.valaria.placeholders.mcmmo.bridge.McmmoBridge;
import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;
import pw.valaria.placeholders.mcmmo.bridge.data.LeaderboardStat;

public class SkillLeaderboardPlaceholder implements Placeholder {
    private McmmoBridge bridge;
    private ISkillType skillType;

    public SkillLeaderboardPlaceholder(McmmoBridge bridge, ISkillType skillType) {
        this.bridge = bridge;
        this.skillType = skillType;
    }

    @Override
    public String process(Player p, String params) {
        int position = 1;
        DisplayType displayType = DisplayType.NAME;


        int seperatorSplit = params.indexOf("-");

        if (seperatorSplit != -1) {
            position = Integer.valueOf(params.substring(0, seperatorSplit));
            displayType = DisplayType.valueOf(params.substring(seperatorSplit + 1));
        } else {
            position = Integer.valueOf(params);
        }


        final LeaderboardStat leaderboardStat = bridge.getLeaderboardStat(skillType, position);

        if (leaderboardStat != null) {
            switch (displayType) {
                case UUID:
                    // God dangit mcmmo....
                    // Check null, forwards compat...
                    if (leaderboardStat.getName() != null) {
                        Bukkit.getOfflinePlayer(leaderboardStat.getName()).getUniqueId();
                    }
                case NAME:
                    return leaderboardStat.getName();
                case RANK:
                    return String.valueOf(leaderboardStat.getLevel());
            }
        }

        return "";
    }

    @Override
    public String getName() {
        return "top_rank_" + skillType.getSkillName();
    }

    enum DisplayType {
        UUID,
        NAME,
        RANK
    }
}
