package ch.noebuerki.gradelyapi.controller;

import ch.noebuerki.gradelyapi.Validator;
import ch.noebuerki.gradelyapi.database.objects.User;
import ch.noebuerki.gradelyapi.database.repositories.UserRepository;
import ch.noebuerki.gradelyapi.security.AuthorizationFilter;
import ch.noebuerki.gradelyapi.security.Token;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

	private final UserRepository userRepository = new UserRepository();

	@GetMapping("/user/get")
	public List<User> get(@RequestHeader Token token) {
		return userRepository.readByColumn("id", Integer.toString(AuthorizationFilter.getTokenId(token)));
	}

	@PostMapping(value = "/user/register")
	public void register(@RequestBody User user) {
		if (Validator.checkIsValidUsername(user.getUsername()) &&
				Validator.checkIsValidEmail(user.getEmail()) &&
				Validator.checkIsValidPassword(user.getPassword())) {
			user.setPassword(encoder().encode(user.getPassword()));
			userRepository.insert(user);
		}
	}

	@Bean
	private BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}


	@PostMapping("/user/login")
	public Token login(@RequestBody User user) {
		List<User> dbUsers = userRepository.readByColumn("username", user.getUsername());
		if (dbUsers.size() == 1) {
			User dbUser = dbUsers.get(0);
			if (encoder().matches(user.getPassword(), dbUser.getPassword())) {
				Token token = AuthorizationFilter.createToken(dbUser);
				return token;
			}
		}
		return null;
	}
}
