package uz.ecommerce.userservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.ecommerce.commons.constant.Role;
import uz.ecommerce.commons.exception.APIExceptionHandler;
import uz.ecommerce.userservice.entity.User;
import uz.ecommerce.userservice.repository.UserRepository;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public ResponseEntityExceptionHandler exceptionHandler(){
        return new APIExceptionHandler();
    }

    @Bean
    public CommandLineRunner commandLineRunner(){
        return args -> {
            boolean adminNotExists = userRepository.findByUsername("admin").isEmpty();
            boolean userNotExists = userRepository.findByUsername("user").isEmpty();

            if (adminNotExists) {
                User user = User.builder()
                        .username("admin")
                        .password(new BCryptPasswordEncoder().encode("admin"))
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(user);
            }

            if (userNotExists) {
                User user = User.builder()
                        .username("user")
                        .password(new BCryptPasswordEncoder().encode("user"))
                        .role(Role.CUSTOMER)
                        .build();

                userRepository.save(user);
            }
        };
    }
}