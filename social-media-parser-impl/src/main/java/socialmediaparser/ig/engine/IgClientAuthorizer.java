package socialmediaparser.ig.engine;

import com.github.instagram4j.instagram4j.IGClient;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

@Component
public class IgClientAuthorizer {

    public IGClient getClient(String username, String password, OkHttpClient okHttpClient) {
        return IGClient.builder()
                .username(username)
                .password(password)
                .client(okHttpClient)
                .build();
    }
}
