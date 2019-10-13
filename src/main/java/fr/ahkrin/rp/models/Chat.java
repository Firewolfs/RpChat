package fr.ahkrin.rp.models;

import fr.ahkrin.rp.listeners.TextColors;
import org.spongepowered.api.text.Text;

public class Chat {

    private String label;
    private String prefix;
    private int range;
    private String format;
    private String permission;
    private int food;
    private boolean isDefault;
    private Sound sound;

    public Chat(String label, String prefix, int range, String format, String permission, int food, boolean isDefault, Sound sound) {
        this.label = label;
        this.prefix = prefix;
        this.range = range;
        this.format = format;
        this.permission = permission;
        this.food = food;
        this.isDefault = isDefault;
        this.sound = sound;
    }

    public String getLabel() {
        return label;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getRange() {
        return range;
    }

    public String getFormat(Text name, String message) {
        TextColors colors = TextColors.find(name.getColor().getId());
        return format.replace("%player%", colors.getAlternative() + name.toPlain() + TextColors.RESET.getAlternative()).replace("%prefix%", getPrefix()).replace("%message%", message);
    }

    public String getPermission() {
        return permission;
    }

    public int getFood() {
        return food;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public Sound getSound() {
        return sound;
    }

    public Text format(Text player, String message, Chat prefix){
        String msg = isDefault ? message : message.substring(prefix.getPrefix().length());
        return Text.builder(getFormat(player, msg)).build();
    }

}
