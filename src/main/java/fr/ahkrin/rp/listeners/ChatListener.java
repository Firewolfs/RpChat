package fr.ahkrin.rp.listeners;

import fr.ahkrin.rp.RpChat;
import fr.ahkrin.rp.models.Chat;
import fr.ahkrin.rp.utils.ChatUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Collection;

public class ChatListener {
    private Chat[] chats;

    public ChatListener(RpChat main){
        chats = main.getChats();
    }

    @Listener
    public void onPlayerChat(MessageChannelEvent.Chat event){
        event.setMessageCancelled(true);
        event.setMessage(Text.EMPTY);

        if(!(event.getSource() instanceof Player)) return;

        Collection<Player> players = Sponge.getGame().getServer().getOnlinePlayers();
        Player p = (Player) event.getSource();
        String prefix = event.getRawMessage().toPlain();

        Chat chat = getChatByPrefix(prefix);
        if(p.hasPermission(chat.getPermission())){
            for(Player player : players){
                if(!ChatUtils.outOfRange(p, player, chat.getRange())){
                    this.talk(p, player, chat, event.getRawMessage());
                }
            }
        }
        playSound(p, chat);

    }

    private void talk(Player source, Player receiver, Chat chat, Text message){
        receiver.sendMessage(
            TextSerializers.FORMATTING_CODE.deserialize(
                TextSerializers.FORMATTING_CODE.serialize(
                    chat.format(ChatUtils.getNickname(source), message.toPlain(), chat)
                )
            )
        );

        int food = source.get(FoodData.class).get().foodLevel().getDirect().get() - chat.getFood();
        if(food < source.get(FoodData.class).get().foodLevel().getMaxValue()){
            source.offer(Keys.FOOD_LEVEL, food);
        }else{
            source.offer(Keys.FOOD_LEVEL, source.getFoodData().foodLevel().getMinValue());
        }

    }

    private void playSound(Player player, Chat message){
        player.getWorld().playSound(SoundType.of(Integer.toString(message.getSound().getId())), player.getLocation().getPosition(),
            message.getSound().getVolume(), message.getSound().getPitch());
    }

    private Chat getChatByPrefix(String message){
        for(Chat chat : getChats()){
            if(message.startsWith(chat.getPrefix())){
                return chat;
            }
        }
        return getDefaultChat();
    }

    private Chat getDefaultChat(){
        for(Chat prefix : chats){
            if(prefix.isDefault()){
                return prefix;
            }
        }
        return null;
    }

    private Chat[] getChats(){
        return chats;
    }

}
