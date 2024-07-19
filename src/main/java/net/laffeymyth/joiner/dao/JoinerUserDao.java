package net.laffeymyth.joiner.dao;

import com.j256.ormlite.dao.Dao;
import net.laffeymyth.joiner.model.JoinerUser;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface JoinerUserDao extends Dao<JoinerUser, String> {

    JoinerUser save(JoinerUser joinerUser) throws SQLException;

    void saveAll(List<JoinerUser> joinerUsers) throws SQLException;

    Optional<JoinerUser> findById(String id) throws SQLException;

    List<JoinerUser> findAll() throws SQLException;
}

