package com.books;

import com.books.entity.AppUser;
import com.books.entity.enums.Role;
import com.books.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DemoBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoBookApplication.class, args);
	}

	@Component
	class AdminUserInitializer implements CommandLineRunner {

		private final UserRepository userRepository;
		private final PasswordEncoder passwordEncoder;

		public AdminUserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
			this.userRepository = userRepository;
			this.passwordEncoder = passwordEncoder;
		}

		@Override
		public void run(String... args) throws Exception {
			AppUser appUser = new AppUser();
			appUser.setFirstName("Admin");
			appUser.setLastName("User");
			appUser.setEmail("admin@gmail.com");
			appUser.setPassword(passwordEncoder.encode("1234"));
			appUser.setRole(Role.ADMIN);
			userRepository.save(appUser);
		}
	}
}
