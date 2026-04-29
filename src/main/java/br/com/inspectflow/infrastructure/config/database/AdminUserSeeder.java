package br.com.inspectflow.infrastructure.config.database;

import br.com.inspectflow.domain.user.enums.Role;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AdminUserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        final String email = "rafaeljunio.jsm@outlook.com";
        final String password = "admin123";

        if (userRepository.findAll().isEmpty()) {
            log.info("Nenhum usuário encontrado na base de dados. Iniciando criação do administrador padrão...");

            User admin = User.builder()
                    .name("Rafael Moraes")
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(Role.ADMINISTRADOR)
                    .active(true)
                    .mustChangePassword(true)
                    .build();

            userRepository.save(admin);

            log.info("##########################################################");
            log.info("USUÁRIO ADMINISTRADOR INICIAL CRIADO:");
            log.info(email);
            log.info(password);
            log.info("##########################################################");
        } else {
            log.debug("A base de dados já contém usuários. Pulando seeder de administrador.");
        }
    }
}
