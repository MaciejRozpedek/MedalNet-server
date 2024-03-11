package com.macroz.medalnetserver.dtos;

import com.macroz.medalnetserver.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String username;
    private String email;
    private String base64profilePicture;

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setBase64profilePicture(user.getBase64profilePicture());
        return userDto;
    }
}
