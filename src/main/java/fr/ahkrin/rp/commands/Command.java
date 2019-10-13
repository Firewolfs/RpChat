package fr.ahkrin.rp.commands;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class Command implements CommandCallable {

    private String permission;
    private List<String> suggestions;
    private Text description;
    private Text usage;

    public Command(String permission, Text description, Text usage, List<String> suggestions) {
        this.permission = permission;
        this.description = description;
        this.usage = usage;
        this.suggestions = suggestions;
    }

    public abstract CommandResult process(CommandSource source, String arguments) throws CommandException;

    @Override
    public List<String> getSuggestions(CommandSource source, String arguments, @Nullable Location<World> targetPosition) throws CommandException {
        return suggestions;
    }

    @Override
    public boolean testPermission(CommandSource source) {
        if(!source.hasPermission(permission)){
            source.sendMessage(Text.of("You don't have this permission."));
        }
        return source.hasPermission(permission);
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(description);
    }

    @Override
    public Optional<Text> getHelp(CommandSource source) {
        return getShortDescription(source);
    }

    @Override
    public Text getUsage(CommandSource source) {
        return usage;
    }

}
