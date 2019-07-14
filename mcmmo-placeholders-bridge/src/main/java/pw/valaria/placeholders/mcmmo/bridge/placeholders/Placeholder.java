package pw.valaria.placeholders.mcmmo.bridge.placeholders;

import org.bukkit.entity.Player;

public interface Placeholder {

    String process(Player player);

    String getName();
}
