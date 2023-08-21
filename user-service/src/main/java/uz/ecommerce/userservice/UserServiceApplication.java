package uz.ecommerce.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import uz.ecommerce.commons.constant.Role;
import uz.ecommerce.userservice.entity.User;
import uz.ecommerce.userservice.repository.UserRepository;

@SpringBootApplication
@EnableFeignClients
public class UserServiceApplication {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {

			if (userRepository.findByUsername("admin").isEmpty()) {
				User admin = User.builder()
						.username("admin")
						.password("admin")
						.role(Role.ADMIN)
						.build();
				userRepository.save(admin);
			}

			if (userRepository.findByUsername("customer").isEmpty()) {
				User customer = User.builder()
						.username("customer")
						.password("customer")
						.role(Role.CUSTOMER)
						.build();

				userRepository.save(customer);
			}

		};
	}

}
