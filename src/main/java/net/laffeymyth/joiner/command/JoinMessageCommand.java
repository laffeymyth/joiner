package net.laffeymyth.joiner.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.laffeymyth.joiner.model.JoinMessage;
import net.laffeymyth.joiner.model.JoinerUser;
import net.laffeymyth.joiner.service.JoinMessageService;
import net.laffeymyth.joiner.service.JoinerUserService;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Command(name = "joinmessage")
public class JoinMessageCommand {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final JoinerUserService joinerUserService;
    private final JoinMessageService joinMessageService;

    @Async
    @Execute(name = "create")
    void create(@Context CommandSender sender, @Join("текст сообщения") String messageText) {
        JoinMessage joinMessage = new JoinMessage();
        joinMessage.setMessage(messageText);
        JoinMessage newJoinMessage = joinMessageService.save(joinMessage);

        sender.sendMessage(Component.text("Сообщение " + newJoinMessage.getId() + " создано!")
                .color(NamedTextColor.GREEN));
    }

    @Async
    @Execute(name = "list")
    void list(@Context CommandSender sender) {
        List<JoinMessage> joinMessages = joinMessageService.findAll();

        if (joinMessages.isEmpty()) {
            sender.sendMessage(Component.text("Список сообщений для входа пуст!").color(NamedTextColor.RED));
            return;
        }

        sender.sendMessage(Component.text("Список сообщений для входа:").color(NamedTextColor.GRAY));
        joinMessages.forEach(joinMessage -> {
            Component joinMessageComponent = miniMessage.deserialize(joinMessage.getMessage());

            sender.sendMessage(Component.text(" - " + joinMessage.getId() + ": ").color(NamedTextColor.GRAY)
                    .append(joinMessageComponent));
        });
    }

    @Async
    @Execute(name = "delete")
    void delete(@Context CommandSender sender, @Arg long joinMessageId) {
        Optional<JoinMessage> optionalJoinMessage = joinMessageService.findById(joinMessageId);

        if (optionalJoinMessage.isEmpty()) {
            sender.sendMessage(Component.text("Сообщение " + joinMessageId + " не найдено!").color(NamedTextColor.RED));
            return;
        }

        joinMessageService.delete(optionalJoinMessage.get());
        sender.sendMessage(Component.text("Сообщение " + joinMessageId + " удалено!").color(NamedTextColor.GREEN));
    }

    @Async
    @Execute(name = "set")
    void set(@Context CommandSender sender,
             @Arg("имя игрока") String playerName,
             @Arg("id сообщения") long joinMessageId) {
        Optional<JoinerUser> userOptional = joinerUserService.findByName(playerName);

        if (userOptional.isEmpty()){
            sender.sendMessage(Component.text("Пользователь " + playerName + " не найден или не зарегистрирован!").color(NamedTextColor.RED));
            return;
        }

        Optional<JoinMessage> optionalJoinMessage = joinMessageService.findById(joinMessageId);

        if (optionalJoinMessage.isEmpty()){
            sender.sendMessage(Component.text("Сообщение " + joinMessageId + " не найдено!").color(NamedTextColor.RED));
            return;
        }

        JoinerUser joinerUser = userOptional.get();
        joinerUser.setActiveJoinMessage(optionalJoinMessage.get());
        joinerUserService.save(joinerUser);

        sender.sendMessage(Component.text("Пользователю " + playerName + " установлено сообщение " + joinMessageId).color(NamedTextColor.GREEN));
    }
}