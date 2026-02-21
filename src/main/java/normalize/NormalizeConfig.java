package normalize;


public record NormalizeConfig(
    boolean trim,
    boolean collapseSpaces,
    boolean lowerCase,
    boolean upperCase,
    boolean nullIfBlank,
    boolean unicode,
    boolean stripAccents,
    String pattern
) {

    public static NormalizeConfig from(Normalize n) {
        return new NormalizeConfig(
            n.trim(),
            n.collapseSpaces(),
            n.lowerCase(),
            n.upperCase(),
            n.nullIfBlank(),
            n.unicode(),
            n.stripAccents(),
            n.pattern()
        );
    }
}
