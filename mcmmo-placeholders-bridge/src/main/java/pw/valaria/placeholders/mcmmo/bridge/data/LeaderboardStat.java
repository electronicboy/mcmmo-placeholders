package pw.valaria.placeholders.mcmmo.bridge.data;

public class LeaderboardStat {
    private String name;
    private int level;

    public LeaderboardStat(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
}
