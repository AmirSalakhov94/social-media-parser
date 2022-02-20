package dto.ig;

import lombok.Data;

@Data
public class ClientMetaData {

    public String username;
    public String password;
    public ClientProxy bindClientProxy;
}
