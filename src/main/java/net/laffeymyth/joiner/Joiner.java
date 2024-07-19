package net.laffeymyth.joiner;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.Getter;
import net.laffeymyth.joiner.command.JoinMessageCommand;
import net.laffeymyth.joiner.dao.JoinMessageDao;
import net.laffeymyth.joiner.dao.JoinerUserDao;
import net.laffeymyth.joiner.factory.LiteCommandFactory;
import net.laffeymyth.joiner.factory.MysqlSourceFactory;
import net.laffeymyth.joiner.listener.JoinerListener;
import net.laffeymyth.joiner.model.JoinMessage;
import net.laffeymyth.joiner.model.JoinerUser;
import net.laffeymyth.joiner.service.JoinMessageService;
import net.laffeymyth.joiner.service.JoinerUserService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

@Getter
public final class Joiner extends JavaPlugin {
    private JdbcPooledConnectionSource source;
    private JoinerUserDao joinerUserDao;
    private JoinMessageDao joinMessageDao;

    @Override
    public void onEnable() {
        source = new MysqlSourceFactory().create();

        try {
            TableUtils.createTableIfNotExists(source, JoinerUser.class);
            TableUtils.createTableIfNotExists(source, JoinMessage.class);
            joinerUserDao = DaoManager.createDao(source, JoinerUser.class);
            joinMessageDao = DaoManager.createDao(source, JoinMessage.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JoinerUserService joinerUserService = new JoinerUserService(joinerUserDao);
        JoinMessageService joinMessageService = new JoinMessageService(joinMessageDao);

        new LiteCommandFactory().create(
                new JoinMessageCommand(joinerUserService, joinMessageService)
        );

        Bukkit.getPluginManager().registerEvents(new JoinerListener(joinerUserService), this);
    }

    @Override
    public void onDisable() {
        try {
            source.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
