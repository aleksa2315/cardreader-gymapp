package rs.aleksa.membership.web;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import rs.aleksa.membership.dto.AuthDtos.*;
import rs.aleksa.membership.repo.AppUserRepository;
import rs.aleksa.membership.security.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager auth; private final JwtService jwt; private final AppUserRepository users;
    public AuthController(AuthenticationManager auth, JwtService jwt, AppUserRepository users){this.auth=auth;this.jwt=jwt;this.users=users;}
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req){
        auth.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        var u = users.findByUsername(req.username()).orElseThrow();
        return new LoginResponse(jwt.generate(u.getUsername(), u.getRole().name()), u.getUsername(), u.getFullName(), u.getRole().name());
    }
}
