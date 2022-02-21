package com.mirsalakhov.socialmediaparser.service;

import com.mirsalakhov.socialmediaparser.ig.dto.IgProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.mirsalakhov.socialmediaparser.mapper.ProfileMapper;
import com.mirsalakhov.socialmediaparser.repository.ProfileRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public void saveAll(List<IgProfile> igProfiles) {
        profileRepository.saveAll(profileMapper.toEntities(igProfiles));
    }
}
