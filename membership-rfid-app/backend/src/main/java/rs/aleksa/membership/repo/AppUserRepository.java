package rs.aleksa.membership.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.aleksa.membership.model.AppUser;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
