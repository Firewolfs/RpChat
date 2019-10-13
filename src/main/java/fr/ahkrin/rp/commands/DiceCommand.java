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

public class DiceCommand extends Command {

    private static final String PERMISSION = "permission.roleplay.dice";

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

        try {
            Integer max = (Integer) evaluate(arguments);

            Collection<Player> players = Sponge.getGame().getServer().getOnlinePlayers();
            if(player.hasPermission(PERMISSION)){
                this.talk(players, player, max, arguments);
            }

            return CommandResult.success();
        } catch (ScriptException exception) {
            player.sendMessage(Text.of("Cannot evaluate your expression."));
            return CommandResult.empty();
        }
    }

    private void talk(Collection<Player> players, Player sender, Integer max, String expression) {
        for (Player receiver : players) {
            if (!ChatUtils.outOfRange(sender, receiver, 8)) {
                Text nickname = ChatUtils.getNickname(sender);
                TextColors color = TextColors.find(nickname.getColor().getId());

                String message = "[" + color.getAlternative() + nickname.toPlain() + TextColors.RESET.getAlternative() + "] " +
                        "a obtenu [" + rand(max, 0) + "/" + max + "] sur le lance: " + expression;

                receiver.sendMessage(
                    TextSerializers.FORMATTING_CODE.deserialize(
                        TextSerializers.FORMATTING_CODE.serialize(Text.of(message))
                    )
                );
            }
        }
    }

    private Object evaluate(String expression) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        return engine.eval(expression);
    }

    private int rand(int max, int min){
        return min + (int)(Math.random() * ((max - min) + 1));
    }

}
