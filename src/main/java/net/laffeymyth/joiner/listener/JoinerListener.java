package net.laffeymyth.joiner.listener;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.TagPattern;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.laffeymyth.joiner.model.JoinMessage;
import net.laffeymyth.joiner.model.JoinerUser;
import net.laffeymyth.joiner.service.JoinerUserService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JoinerListener implements Listener {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final Map<String, JoinerUser> userMap = new HashMap<>();

    private final JoinerUserService joinerUserService;

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        JoinerUser user = joinerUserService.findByName(event.getName()).orElseGet(() -> {
            JoinerUser joinerUser = new JoinerUser();
            joinerUser.setLowerName(event.getName());
            return joinerUserService.save(joinerUser);
        });

        userMap.put(event.getName(), user);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        JoinerUser user = userMap.remove(player.getName());

        if (user == null) {
            return;
        }

        JoinMessage joinMessage = user.getActiveJoinMessage();

        if (joinMessage == null) {
            event.joinMessage(null);
            return;
        }


        Component joinMessageComponent = miniMessage.deserialize(joinMessage.getMessage(),
                TagResolver.resolver("player", (argumentQueue, context) -> Tag.inserting(player.displayName())));

        event.joinMessage(Component.empty().append(joinMessageComponent));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        userMap.remove(event.getPlayer().getName());
    }
}
