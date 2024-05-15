package com.books.service.auth;

import com.books.constants.Constants;
import com.books.dto.auth.RegisterDto;
import com.books.dto.auth.response.AuthenticationResponse;
import com.books.entity.AppUser;
import com.books.entity.enums.Role;
import com.books.exception.ResourceNotFoundException;
import com.books.mapper.UserMapper;
import com.books.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public RegisterDto register(RegisterDto request) {
        AppUser user = userMapper.toAppUser(request);

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);

        user = userRepository.save(user);

        String token = jwtService.generateToken(user);

        RegisterDto response = new RegisterDto();
        response.setToken(token);

        return response;
    }

    public AuthenticationResponse login(AppUser request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        AppUser user = userRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Email", "Email", request.getUsername()));

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(Constants.STATUS_200, Constants.MESSAGE_LOGIN_SUCCESSFUL, token);
    }

    public void grantAdminRole(Integer userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }
}
