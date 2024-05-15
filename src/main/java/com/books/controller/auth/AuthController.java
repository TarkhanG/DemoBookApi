package com.books.controller.auth;

import com.books.constants.Constants;
import com.books.dto.ResponseDto;
import com.books.dto.auth.RegisterDto;
import com.books.dto.auth.response.AuthenticationResponse;
import com.books.entity.AppUser;
import com.books.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody RegisterDto user){
        authService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(
                        Constants.STATUS_201,
                        Constants.MESSAGE_REGISTER_SUCCESSFUL)
                );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AppUser user){
        return ResponseEntity.ok(authService.login(user));
    }

    @PostMapping("/adminRole")
    public ResponseEntity<ResponseDto> adminRole(@RequestParam Integer id){
        authService.grantAdminRole(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new ResponseDto(
                        Constants.STATUS_201,
                        Constants.MESSAGE_ADMIN_ROLE_GRANTED)
                );
    }
}
