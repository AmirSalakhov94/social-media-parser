package socialmediaparser.service;

import dto.ig.IgProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import socialmediaparser.mapper.ProfileMapper;
import socialmediaparser.repository.ProfileRepository;

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
