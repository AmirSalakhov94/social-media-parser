package dto.ig;

import lombok.Data;

import java.net.Proxy;

@Data
public class ClientProxy {

    public Proxy.Type type;
    public String hostname;
    public int port;
    public String username;
    public String password;
}
