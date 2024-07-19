package net.laffeymyth.joiner.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;
import net.laffeymyth.joiner.dao.impl.JoinerUserDaoImpl;

@Getter
@Setter
@DatabaseTable(tableName = "joiner_user", daoClass = JoinerUserDaoImpl.class)
@AllArgsConstructor
@NoArgsConstructor
public class JoinerUser {
    @DatabaseField(id = true, unique = true, columnName = "lower_name")
    private String lowerName;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "active_join_message")
    private JoinMessage activeJoinMessage;
}
