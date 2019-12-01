package fr.ahkrin.rp.commands;

import fr.ahkrin.rp.listeners.TextColors;
import fr.ahkrin.rp.utils.ChatUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiceCommand extends Command {

    private static final String PERMISSION = "permission.roleplay.dice";

    private static final Pattern PATTERN = Pattern.compile("([0-9]{1,4}) ?\\+ ?([0-9]{1,4}) ?- ?([0-9]{1,4})");

    public DiceCommand() {
        super(
                PERMISSION,
                Text.of("Allow you to roll a dice with a expression"),
                Text.of("/[roll | r] expression"),
                Arrays.asList("/roll 15+4", "/r 15+4")
        );
    }

    @Override
    public CommandResult process(CommandSource source, String arguments) throws CommandException {
        if (!(source instanceof Player)) return CommandResult.empty();
        Player player = (Player) source;

        Matcher matcher = PATTERN.matcher(arguments);
        if (matcher.find()) {
            Integer max = Integer.decode(matcher.group(1));
            Integer bonus = Integer.decode(matcher.group(2));
            Integer malus = Integer.decode(matcher.group(3));

            Collection<Player> players = Sponge.getGame().getServer().getOnlinePlayers();
            if(player.hasPermission(PERMISSION)){
                this.talk(players, player, max, bonus, malus);
            }

            return CommandResult.success();
        }

        return CommandResult.empty();
    }

    private void talk(Collection<Player> players, Player sender, Integer max, Integer bonus, Integer malus) {
        for (Player receiver : players) {
            if (!ChatUtils.outOfRange(sender, receiver, 8)) {
                Text nickname = ChatUtils.getNickname(sender);
                TextColors color = TextColors.find(nickname.getColor().getId());

                String message = String.format(
                    "[%s] a obtenu [%s]/[%s] avec un bonus de [%s] et un malus de [%s]",
                    color.getAlternative() + nickname.toPlain() + TextColors.RESET.getAlternative(),
                    rand(max, 0),
                    max, bonus, malus
                );

                receiver.sendMessage(
                    TextSerializers.FORMATTING_CODE.deserialize(
                        TextSerializers.FORMATTING_CODE.serialize(Text.of(message))
                    )
                );
            }
        }
    }

    private int rand(int max, int min){
        return min + (int)(Math.random() * ((max - min) + 1));
    }

}
