package rs.aleksa.membership.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.aleksa.membership.model.AppUser;
import rs.aleksa.membership.repo.AppUserRepository;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AppUserRepository users;
    public CustomUserDetailsService(AppUserRepository users) { this.users = users; }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser u = users.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPasswordHash(), u.isActive(), true, true, true,
                List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name())));
    }
}
