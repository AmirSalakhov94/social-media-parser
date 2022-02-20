package socialmediaparser.ig.service;

import com.github.instagram4j.instagram4j.IGClient;
import dto.ig.IgProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import socialmediaparser.ig.mapper.IgProfileMapper;
import socialmediaparser.ig.provider.IgClientProvider;

@Service
@RequiredArgsConstructor
public class IgParser {

    private final IgClientProvider clientProvider;
    private final IgProfileMapper igProfileMapper;

    public IgProfile getProfileInfo(String username) {
        IGClient client = clientProvider.getClient();
        try {
            return client.getActions().users().findByUsername(username)
                    .thenApply(usersSearchResponse -> igProfileMapper.igProfileToDto(usersSearchResponse.getUser()))
                    .join();
        } finally {
            clientProvider.addClient(client);
        }
    }
}
