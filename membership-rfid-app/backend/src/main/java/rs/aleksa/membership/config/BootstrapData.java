package rs.aleksa.membership.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.aleksa.membership.model.AppUser;
import rs.aleksa.membership.model.Enums;
import rs.aleksa.membership.repo.AppUserRepository;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username:admin}")
    private String adminUsername;

    @Value("${admin.password:admin123}")
    private String adminPassword;

    public BootstrapData(AppUserRepository appUserRepository,
                         PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (appUserRepository.count() > 0) {
            return;
        }

        if (adminPassword == null || adminPassword.isBlank()) {
            throw new IllegalStateException("ADMIN_PASSWORD nije podešen.");
        }

        AppUser admin = new AppUser();
        admin.setUsername(adminUsername);
        admin.setPasswordHash(passwordEncoder.encode(adminPassword));
        admin.setFullName("Administrator");
        admin.setRole(Enums.UserRole.valueOf("ADMIN"));
        admin.setActive(true);

        appUserRepository.save(admin);

        System.out.println("Kreiran početni admin korisnik: " + adminUsername);
    }
}
