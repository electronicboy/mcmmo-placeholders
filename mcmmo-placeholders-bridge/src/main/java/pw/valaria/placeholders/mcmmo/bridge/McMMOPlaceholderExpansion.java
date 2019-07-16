package pw.valaria.placeholders.mcmmo.bridge;

import com.google.common.collect.ImmutableList;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.TreeMap;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import pw.valaria.placeholders.mcmmo.bridge.placeholders.Placeholder;

public class McMMOPlaceholderExpansion extends PlaceholderExpansion {

    private Map<String, Placeholder> placeholders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public McMMOPlaceholderExpansion() {
        register();

        // TODO: Classpath scanner?
        final ImmutableList<String> knownClasses = ImmutableList.<String>builder()
                                                           .add("pw.valaria.placeholders.mcmmo.bridge.classic.McmmoBridgeClassic")
                                                           .add("pw.valaria.placeholders.mcmmo.bridge.v2_1.McmmoBridge21")
                                                           .build();

        for (String knownClass : knownClasses) {
            try {
                final Class<? extends McmmoBridge> aClass = (Class<? extends McmmoBridge>) Class.forName(knownClass);
                final McmmoBridge mcmmoBridge = aClass.newInstance();
                if (mcmmoBridge.canHook()) {
                    mcmmoBridge.init(this);
                    return;
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ignored) {
            }
        }

        throw new IllegalStateException("Did not find a valid bridge! Are you using a supported version of mcmmo?");
    }

    @Override
    public String getIdentifier() {
        return "mcmmo";
    }

    @Override
    public String getAuthor() {
        return "electronicboy";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getRequiredPlugin() {
        return "mcMMO";
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        Placeholder placeholder = placeholders.get(params);

        if (placeholder != null) {
            return placeholder.process(p);
        } else {
            return null;
        }
    }

    public void registerPlaceholder(Placeholder placeholder) {
        final Placeholder registered = placeholders.get(placeholder.getName());
        if (registered != null)
            throw new IllegalStateException("Placeholder " + placeholder.getName() + " is already registered!");

        placeholders.put(placeholder.getName(), placeholder);
    }
}
