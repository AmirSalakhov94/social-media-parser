package socialmediaparser.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table("profile")
public class Profile {

    @PrimaryKey
    private String id;
    private Long pk;
    private String username;
    private String fulName;
}
