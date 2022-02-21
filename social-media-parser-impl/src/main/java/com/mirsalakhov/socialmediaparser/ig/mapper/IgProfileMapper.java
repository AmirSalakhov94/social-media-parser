package com.mirsalakhov.socialmediaparser.ig.mapper;

import com.github.instagram4j.instagram4j.models.user.Profile;
import com.mirsalakhov.socialmediaparser.ig.dto.IgProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IgProfileMapper {

    @Mapping(target = "fullName", source = "full_name")
    IgProfile igProfileToDto(Profile profile);
}
