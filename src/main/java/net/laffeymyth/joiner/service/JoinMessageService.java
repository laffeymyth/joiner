package net.laffeymyth.joiner.service;

import lombok.RequiredArgsConstructor;
import net.laffeymyth.joiner.dao.JoinMessageDao;
import net.laffeymyth.joiner.model.JoinMessage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JoinMessageService {
    private final JoinMessageDao joinMessageDao;

    public Optional<JoinMessage> findById(long id) {
        try {
            return joinMessageDao.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<JoinMessage> findAll() {
        try {
            return joinMessageDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public JoinMessage save(JoinMessage joinMessage) {
        try {
            return joinMessageDao.save(joinMessage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(JoinMessage joinMessage) {
        try {
            joinMessageDao.delete(joinMessage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
