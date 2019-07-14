package pw.valaria.placeholders.mcmmo.bridge.data;

/**
 * @param <S> Native skill class
 */
public interface ISkillType<S> {

    /**
     * @return get the native mcmmo skill
     */
    public S getNativeSkill();

    /**
     * @return skill name
     */
    public String getSkillName();

}
