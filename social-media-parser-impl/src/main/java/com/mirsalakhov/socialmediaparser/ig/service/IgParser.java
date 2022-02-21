package com.mirsalakhov.socialmediaparser.ig.service;

import com.github.instagram4j.instagram4j.IGClient;
import com.mirsalakhov.socialmediaparser.ig.dto.IgProfile;
import com.mirsalakhov.socialmediaparser.ig.mapper.IgProfileMapper;
import com.mirsalakhov.socialmediaparser.ig.provider.IgClientProvider;
import org.springframework.stereotype.Service;

@Service
public record IgParser(IgClientProvider clientProvider,
                       IgProfileMapper igProfileMapper) {

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
