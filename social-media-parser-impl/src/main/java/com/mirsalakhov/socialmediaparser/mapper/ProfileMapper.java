package com.mirsalakhov.socialmediaparser.mapper;

import com.mirsalakhov.socialmediaparser.ig.dto.IgProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.mirsalakhov.socialmediaparser.entity.Profile;

import java.util.List;

@Mapper
public interface ProfileMapper {

    @Mapping(target = "id", ignore = true)
    Profile toEntity(IgProfile igProfile);

    @Mapping(target = "id", ignore = true)
    List<Profile> toEntities(List<IgProfile> igProfiles);
}
