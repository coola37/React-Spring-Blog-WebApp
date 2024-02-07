package com.whisper.ws;


import com.whisper.ws.user.repository.UserRepository;
import com.whisper.ws.user.repository.entity.User;
import com.whisper.ws.user.request.UserCreateRequest;
import com.whisper.ws.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);

	}

	@Bean
	CommandLineRunner userCreater(UserRepository repo, PasswordEncoder passwordEncoder){

		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				for(var i=1; i<=25; i++){
					User user = new User();
					user.setUsername("user" + i);
					user.setEmail("user"+i+"@mail.com");
					user.setPassword(passwordEncoder.encode("D3featk123"));
					user.setActive(true);
					user.setActivationToken(null);
					repo.save(user);
				}
				User user = new User();
				user.setUsername("user26");
				user.setEmail("user26@mail.com");
				user.setPassword(passwordEncoder.encode("D3featk123"));
				user.setActive(false);
				user.setActivationToken(null);
				repo.save(user);
			}


		};
	}

}
