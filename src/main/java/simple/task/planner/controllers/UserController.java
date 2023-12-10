package simple.task.planner.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import simple.task.planner.dto.AuthenticationDTO;
import simple.task.planner.security.JWTUtil;

@RestController
@RequestMapping("/users")
@Tag(name = "Контроллер аутентификации",
        description = "Позволяет управлять данными пользователей")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public UserController(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Аутентификация пользователя",
            description = "Передавая валидные email и пароль пользователя возвращается JWT токен")
    public HttpEntity<String> login(@RequestBody AuthenticationDTO userDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException ex) {
            throw new AuthenticationCredentialsNotFoundException("Bad credentials");
        }

        return new HttpEntity<>(jwtUtil.generateToken(userDTO.getUser()));
     }
}
