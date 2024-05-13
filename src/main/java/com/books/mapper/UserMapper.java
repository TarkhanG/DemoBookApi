package com.books.mapper;

import com.books.dto.auth.RegisterDto;
import com.books.entity.AppUser;
import com.books.entity.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public AppUser toAppUser(RegisterDto registerDto) {
        AppUser user = new AppUser();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());

        user.setRole(Role.USER);
        return user;
    }
}
