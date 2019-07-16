package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;

public interface Placeholder {

    /**
     * @param player the player to process the placeholder for
     * @return the value of the placeholder
     */
    String process(Player player);

    /**
     * @return the name of the placeholder
     */
    String getName();
}
