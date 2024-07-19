package net.laffeymyth.joiner.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;
import net.laffeymyth.joiner.dao.impl.JoinMessageDaoImpl;

@Getter
@Setter
@DatabaseTable(tableName = "join_message", daoClass = JoinMessageDaoImpl.class)
@AllArgsConstructor
@NoArgsConstructor
public class JoinMessage {
    @DatabaseField(generatedId = true, unique = true)
    private long id;
    @DatabaseField(canBeNull = false)
    private String message;
    @ForeignCollectionField
    private ForeignCollection<JoinerUser> joinerUsers;
}