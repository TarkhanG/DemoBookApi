package com.books.dto.auth;

import com.books.entity.enums.Role;
import lombok.Data;

import java.util.List;

@Data
public class JWTDto {
    private List<Role> roles;
    private Integer userId;
}
