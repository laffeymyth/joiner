package net.laffeymyth.joiner.dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import com.j256.ormlite.support.ConnectionSource;
import net.laffeymyth.joiner.dao.JoinMessageDao;
import net.laffeymyth.joiner.model.JoinMessage;

public class JoinMessageDaoImpl extends BaseDaoImpl<JoinMessage, Long> implements JoinMessageDao {
    public JoinMessageDaoImpl(ConnectionSource connectionSource, Class<JoinMessage> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public JoinMessage save(JoinMessage joinMessage) throws SQLException {
        assignEmptyCollections(joinMessage);
        createOrUpdate(joinMessage);
        return joinMessage;
    }

    @Override
    public void saveAll(List<JoinMessage> joinMessages) throws SQLException {
        callBatchTasks((Callable<Void>) () -> {
            for (JoinMessage joinMessage : joinMessages) {
                assignEmptyCollections(joinMessage);
                createOrUpdate(joinMessage);
            }
            return null;
        });
    }

    private void assignEmptyCollections(JoinMessage joinMessage) throws SQLException {
        if (joinMessage.getJoinerUsers() == null) {
            joinMessage.setJoinerUsers(getEmptyForeignCollection("joinerUsers"));
        }
    }

    @Override
    public Optional<JoinMessage> findById(Long id) throws SQLException {
        JoinMessage result = queryForId(id);
        return Optional.ofNullable(result);
    }

    @Override
    public List<JoinMessage> findAll() throws SQLException {
        return queryForAll();
    }
}
