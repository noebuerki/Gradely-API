package ch.gradely.api.controller;

import ch.gradely.api.Validator;
import ch.gradely.api.controller.error.Error;
import ch.gradely.api.controller.error.ErrorType;
import ch.gradely.api.database.objects.User;
import ch.gradely.api.database.repositories.UserRepository;
import ch.gradely.api.security.AuthorizationFilter;
import ch.gradely.api.security.Token;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController extends BaseController {

	@PostMapping("/user/login")
	public Token login(@RequestBody User user) {
		List<User> dbUsers = userRepository.readByColumn(UserRepository.COLUMN_USERNAME, user.getUsername());
		if (dbUsers.size() == 1) {
			User dbUser = dbUsers.get(0);
			if (encoder().matches(user.getPassword(), dbUser.getPassword())) {
				return AuthorizationFilter.createToken(dbUser);
			}
		}
		return null;
	}

	@PostMapping("/user/register")
	public Token register(@RequestBody User user) {
		if (Validator.checkIfValidUsername(user.getUsername()) &&
				Validator.checkIfValidEmail(user.getEmail()) &&
				Validator.checkIfValidPassword(user.getPassword())) {
			user.setPassword(encoder().encode(user.getPassword()));
			userRepository.insert(user);
			return AuthorizationFilter.createToken(user);
		}
		return null;
	}

	@PostMapping("/user/set")
	public Error set(@RequestHeader Token token, @RequestBody String request) {
		User user = userRepository.readByColumn(UserRepository.COLUMN_ID, getUserIdString(token)).get(0);
		Map<String, String> parameters = getRequestParameters(request);
		String newValue = parameters.get("newValue");
		switch (parameters.get("column")) {
			case UserRepository.COLUMN_USERNAME:
				if (Validator.checkIfValidUsername(newValue)) {
					user.setUsername(newValue);
				} else {
					return new Error(ErrorType.INVALID_USERNAME);
				}
				break;
			case UserRepository.COLUMN_EMAIL:
				if (Validator.checkIfValidEmail(newValue)) {
					user.setEmail(newValue);
				} else {
					return new Error(ErrorType.INVALID_EMAIL);
				}
				break;
			case UserRepository.COLUMN_PASSWORD:
				if (Validator.checkIfValidPassword(newValue)) {
					user.setPassword(encoder().encode(newValue));
				} else {
					return new Error(ErrorType.INVALID_PASSWORD);
				}
				break;
			default:
				return new Error(ErrorType.UNKNOWN_COLUMN);
		}
		if (userRepository.update(user)) {
			return new Error(ErrorType.SQL_ERROR);
		}
		return null;
	}

	@Bean
	private BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
