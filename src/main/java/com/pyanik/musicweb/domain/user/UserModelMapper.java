package com.pyanik.musicweb.domain.user;

import com.pyanik.musicweb.domain.user.dto.UserCredentialsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserModelMapper {

    UserCredentialsDto mapUserEntityToUserCredentialsDto(User user);

    default String userRoleToUserName(UserRole userRole) {
        return userRole != null ? userRole.getName() : null;
    }
}