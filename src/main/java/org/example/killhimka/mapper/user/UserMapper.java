package org.example.killhimka.mapper.user;

import org.example.killhimka.dto.user.RegistrationUserDto;
import org.example.killhimka.dto.user.UserDto;
import org.example.killhimka.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity (RegistrationUserDto registrationUserDto);

    UserDto toDto (User user);
}
