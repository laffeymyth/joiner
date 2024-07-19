package net.laffeymyth.joiner.service;

import lombok.RequiredArgsConstructor;
import net.laffeymyth.joiner.dao.JoinerUserDao;
import net.laffeymyth.joiner.model.JoinerUser;

import java.sql.SQLException;
import java.util.Optional;

@RequiredArgsConstructor
public class JoinerUserService {
    private final JoinerUserDao joinerUserDao;

    public Optional<JoinerUser> findByName(String name) {
        name = name.toLowerCase();

        try {
            return joinerUserDao.findById(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public JoinerUser save(JoinerUser joinerUser) {
        joinerUser.setLowerName(joinerUser.getLowerName().toLowerCase());

        try {
            return joinerUserDao.save(joinerUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
