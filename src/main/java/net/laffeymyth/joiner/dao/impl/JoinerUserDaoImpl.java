package net.laffeymyth.joiner.dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import com.j256.ormlite.support.ConnectionSource;
import net.laffeymyth.joiner.dao.JoinerUserDao;
import net.laffeymyth.joiner.model.JoinerUser;

public class JoinerUserDaoImpl extends BaseDaoImpl<JoinerUser, String> implements JoinerUserDao {
    public JoinerUserDaoImpl(ConnectionSource connectionSource, Class<JoinerUser> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public JoinerUser save(JoinerUser joinerUser) throws SQLException {
        createOrUpdate(joinerUser);
        return joinerUser;
    }

    @Override
    public void saveAll(List<JoinerUser> joinerUsers) throws SQLException {
        callBatchTasks((Callable<Void>) () -> {
            for (JoinerUser joinerUser : joinerUsers) {
                createOrUpdate(joinerUser);
            }
            return null;
        });
    }

    @Override
    public Optional<JoinerUser> findById(String id) throws SQLException {
        JoinerUser result = queryForId(id);
        return Optional.ofNullable(result);
    }

    @Override
    public List<JoinerUser> findAll() throws SQLException {
        return queryForAll();
    }
}

