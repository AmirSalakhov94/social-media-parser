package socialmediaparser.mapper;

import dto.ig.IgProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import socialmediaparser.entity.Profile;

import java.util.List;

@Mapper
public interface ProfileMapper {

    @Mapping(target = "id", ignore = true)
    Profile toEntity(IgProfile igProfile);

    @Mapping(target = "id", ignore = true)
    List<Profile> toEntities(List<IgProfile> igProfiles);
}
