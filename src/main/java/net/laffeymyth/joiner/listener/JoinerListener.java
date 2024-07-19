package net.laffeymyth.joiner.listener;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.laffeymyth.joiner.model.JoinMessage;
import net.laffeymyth.joiner.model.JoinerUser;
import net.laffeymyth.joiner.service.JoinerUserService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

        event.joinMessage(Component.empty().append(miniMessage.deserialize(joinMessage.getMessage()
                        .replace("%player%", player.getName()))));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        userMap.remove(event.getPlayer().getName());
    }
}
