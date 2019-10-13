package fr.ahkrin.rp.listeners;

public enum TextColors {

    RED("00A7c", "c", "red"), GOLD("00A76", "6", "gold"), YELLOW("00A7e", "e", "yellow"),
    GREEN("00A7a", "a", "green"), AQUA("00A7b", "b", "aqua"), BLUE("00A79", "9", "blue"),
    WHITE("00A7f", "f", "white"), GRAY("00A77", "7", "gray"), BLACK("00A70", "0", "black"),
    DARK_PURPLE("00A75", "5", "dark_purple"), DARK_GRAY("00A78", "8", "dark_gray"), DARK_RED("00A74", "4", "dark_red"),
    DARK_GREEN("00A72", "2", "dark_green"), DARK_AQUA("00A73", "3", "dark_aqua"), DARK_BLUE("00A71", "1", "dark_blue"),
    LIGHT_PURPLE("00A7d", "d", "light_purple"),
    BOLD("00A7l", "1", "bold"), STRIKETHROUGH("00A7m", "m", "strikethrough"), UNDERLINE("00A7n", "n", "underline"),
    ITALIC("00A7o", "o", "italic"), RESET("00A7r", "r", "reset");

    private static final String OPERATOR_HEX = "\\u";
    private static final String OPERATOR_NORMAL = "§";
    private static final String ALTERNATIVE = "&";

    private String hex;
    private String normal;
    private String alternative;

    private String id;

    TextColors(String hex, String normal, String id) {
        this.hex = OPERATOR_HEX + hex;
        this.normal = OPERATOR_NORMAL + normal;
        this.alternative = ALTERNATIVE + normal;
        this.id = id;
    }

    public static TextColors find(String id) {
        for (TextColors colors : values()) {
            if (colors.id.toUpperCase().equals(id)) {
                return colors;
            }
        }
        return RESET;
    }

    public String getHex() { return this.hex; }
    public String getNormal() { return this.normal; }
    public String getAlternative() { return this.alternative; }

}
