package fr.ahkrin.rp.utils;

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

    public String getFormat(String name, String message) {
        return format.replace("%player%", name).replace("%prefix%", getPrefix()).replace("%message%", message);
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

    public Text format(String player, String message, Chat prefix){
        return Text.of(this.getFormat(player, (this.isDefault() ? message : message.substring(prefix.getPrefix().length(), message.length()))));
    }

}
