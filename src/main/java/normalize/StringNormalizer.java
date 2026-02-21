package normalize;


import java.text.Normalizer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.*;

public class StringNormalizer {

    private static final Pattern MULTI_SPACE = Pattern.compile("\\s+");
    private static final Pattern DIACRITICS = Pattern.compile("\\p{M}");
    private static final Map<String, Pattern> CACHE = new ConcurrentHashMap<>();

    public static String normalize(String value, NormalizeType type, NormalizeConfig config) {

        if (value == null) {
            return null;
        }

        NormalizeConfig effective =
            type != NormalizeType.CUSTOM
                ? preset(type)
                : config;

        return apply(value, effective);
    }

    private static NormalizeConfig preset(NormalizeType type) {
        return switch (type) {
            case EMAIL -> new NormalizeConfig(
                true, false, true, false,
                false, true, true,
                "[^a-z0-9@._-]"
            );
            case PHONE -> new NormalizeConfig(
                true, false, false, false,
                false, false, false,
                "[^0-9+]"
            );
            case NAME -> new NormalizeConfig(
                true, true, false, false,
                false, true, true,
                "[^\\p{L} ]"
            );
            default -> null;
        };
    }

    private static String apply(String value, NormalizeConfig config) {

        String result = value;

        if (config.unicode()) {
            result = Normalizer.normalize(result, Normalizer.Form.NFKC);
        }

        if (config.stripAccents()) {
            result = Normalizer.normalize(result, Normalizer.Form.NFD);
            result = DIACRITICS.matcher(result).replaceAll("");
        }

        if (config.trim()) {
            result = result.trim();
        }

        if (config.collapseSpaces()) {
            result = MULTI_SPACE.matcher(result).replaceAll(" ");
        }

        if (config.lowerCase()) {
            result = result.toLowerCase();
        }

        if (config.upperCase()) {
            result = result.toUpperCase();
        }

        if (!config.pattern().isEmpty()) {
            Pattern p = CACHE.computeIfAbsent(config.pattern(), Pattern::compile);
            result = p.matcher(result).replaceAll("");
        }

        if (config.nullIfBlank() && result.isBlank()) {
            return null;
        }

        return result;
    }
}
