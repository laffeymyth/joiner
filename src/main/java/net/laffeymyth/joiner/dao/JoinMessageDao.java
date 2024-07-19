package net.laffeymyth.joiner.dao;

import com.j256.ormlite.dao.Dao;
import net.laffeymyth.joiner.model.JoinMessage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface JoinMessageDao extends Dao<JoinMessage, Long> {

    JoinMessage save(JoinMessage joinMessage) throws SQLException;

    void saveAll(List<JoinMessage> joinMessages) throws SQLException;

    Optional<JoinMessage> findById(Long id) throws SQLException;

    List<JoinMessage> findAll() throws SQLException;
}
