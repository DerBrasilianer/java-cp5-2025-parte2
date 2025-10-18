package fiap.com.br.brinquedos_revisao.service;

import fiap.com.br.brinquedos_revisao.enums.Role;
import fiap.com.br.brinquedos_revisao.model.AppUser;
import fiap.com.br.brinquedos_revisao.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("user").isEmpty()) {
            userRepository.save(AppUser.builder()
                    .username("user")
                    .password(passwordEncoder.encode("userpass"))
                    .role(Role.USER)
                    .enabled(true)
                    .build());
            System.out.println("=== USUÁRIO USER CRIADO ===");
            System.out.println("Usuário: user");
            System.out.println("Senha: userpass");
            System.out.println("Role: USER");
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            userRepository.save(AppUser.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("adminpass"))
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build());
            System.out.println("=== USUÁRIO ADMIN CRIADO ===");
            System.out.println("Usuário: admin");
            System.out.println("Senha: adminpass");
            System.out.println("Role: ADMIN");
        }

        System.out.println("=== USUÁRIOS DE TESTE PRONTOS ===");
    }
}