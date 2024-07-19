package net.laffeymyth.joiner.factory;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import dev.rollczi.litecommands.schematic.SchematicFormat;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;


@RequiredArgsConstructor
public class LiteCommandFactory {
    @SuppressWarnings("all")
    public LiteCommands<CommandSender> create(Object... commands) {
        return LiteBukkitFactory.builder()
                .settings(settings -> settings
                        .fallbackPrefix("joiner")
                        .nativePermissions(true)
                )
                .commands(commands)
                .message(LiteBukkitMessages.INVALID_USAGE, "§cОшибка, неверное кол-во аргументов!")
                .message(LiteBukkitMessages.PLAYER_ONLY, "§cЭта команда только для игроков!")
                .schematicGenerator(SchematicFormat.angleBrackets())
                .build();
    }

}
