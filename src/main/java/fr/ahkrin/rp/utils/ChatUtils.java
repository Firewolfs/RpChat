package fr.ahkrin.rp.utils;

import io.github.nucleuspowered.nucleus.api.NucleusAPI;
import io.github.nucleuspowered.nucleus.api.service.NucleusNicknameService;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class ChatUtils {

    public static Boolean outOfRange(Entity speaker, Entity receiver, int range) {
        return !(speaker instanceof Player) ||
                !(receiver instanceof Player) ||
                !speaker.getLocation().equals(receiver.getLocation()) && (speaker.getWorld() != receiver.getWorld()
                        || speaker.getLocation().getPosition().distance(receiver.getLocation().getPosition()) > range);
    }

    public static Text getNickname(Player user){
        Optional<NucleusNicknameService> service = NucleusAPI.getNicknameService();
        if (!service.isPresent() || !service.get().getNickname(user).isPresent()) {
            return Text.of(user.getName());
        }
        return service.get().getNickname(user).get().toText();
    }


}
