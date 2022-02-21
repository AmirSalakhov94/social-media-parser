package com.mirsalakhov.socialmediaparser.ig.service;

import com.github.instagram4j.instagram4j.IGClient;
import com.mirsalakhov.socialmediaparser.ig.dto.IgProfile;
import com.mirsalakhov.socialmediaparser.ig.mapper.IgProfileMapper;
import com.mirsalakhov.socialmediaparser.ig.provider.IgClientProviderWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public record IgParser(IgClientProviderWrapper clientProviderWrapper,
                       IgProfileMapper igProfileMapper) {

    public IgProfile getProfileInfo(String username) {
        IGClient client = clientProviderWrapper.getClient();
        try {
            return client.getActions().users().findByUsername(username)
                    .thenApply(usersSearchResponse -> igProfileMapper.igProfileToDto(usersSearchResponse.getUser()))
                    .join();
        } catch (HttpClientErrorException.TooManyRequests e) {
            clientProviderWrapper.incrementFallenClient();
        } finally {
            clientProviderWrapper.addClient(client);
        }
    }
}
