package pw.valaria.placeholders.mcmmo.bridge.v2_1.data;

import com.gmail.nossr50.datatypes.skills.PrimarySkillType;

import pw.valaria.placeholders.mcmmo.bridge.data.ISkillType;

public class SkillType implements ISkillType<PrimarySkillType> {

    PrimarySkillType skillType;

    public SkillType(PrimarySkillType skillType) {
        this.skillType = skillType;
    }

    @Override
    public PrimarySkillType getNativeSkill() {
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
