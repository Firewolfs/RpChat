package fr.ahkrin.rp;

import fr.ahkrin.rp.commands.DiceCommand;
import fr.ahkrin.rp.listeners.ChatListener;
import fr.ahkrin.rp.models.Chat;
import fr.litarvan.commons.config.ConfigProvider;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import javax.inject.Inject;

import static fr.litarvan.commons.io.IOSource.at;

@Plugin(id = RpChat.ID, name = "@name@", version = "@version@", description = "Plugin to give a better RP experience")
public class RpChat {

    protected static final String ID = "@id@";

    @Inject private Logger logger;
    @Inject private ConfigProvider config;

    private Chat[] chats;

    @Listener
    public void onPreInit(GamePreInitializationEvent event){
        config = new ConfigProvider();
        config.from("config/" + getId() + "/chat.json").defaultIn(at("/chat.default.json"));
        chats = config.at("chat.chat", Chat[].class);

        getLogger().info(ID + " was loaded with " + chats.length + " prefix.");
    }

    @Listener
    public void onServerInit(GameInitializationEvent event){
        Sponge.getEventManager().registerListeners(this, new ChatListener(this));
        Sponge.getCommandManager().register(this, new DiceCommand(), "roll", "r");
    }

    @Listener
    public void onReload(GameReloadEvent event){
        getLogger().info("Server plugin reloaded !");
    }

    public String getId(){
        return ID;
    }
    public Logger getLogger(){
        return logger;
    }
    public Chat[] getChats(){
        return chats;
    }

}
