package org.sbitnev.part2.v1.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.sbitnev.part2.v1.user.dto.UserDTO;
import org.sbitnev.part2.v1.user.entity.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);
}
