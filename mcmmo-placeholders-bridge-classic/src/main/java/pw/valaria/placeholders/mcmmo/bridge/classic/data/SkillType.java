package pw.valaria.placeholders.mcmmo.bridge.classic.data;


import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;

public class SkillType implements ISkillType<com.gmail.nossr50.datatypes.skills.SkillType> {

    com.gmail.nossr50.datatypes.skills.SkillType skillType;

    public SkillType(com.gmail.nossr50.datatypes.skills.SkillType skillType) {
        this.skillType = skillType;
    }

    @Override
    public com.gmail.nossr50.datatypes.skills.SkillType getNativeSkill() {
        return skillType;
    }

    @Override
    public String getSkillName() {
        return skillType.getName();
    }

    @Override
    public String toString() {
        return getSkillName();
    }
}
