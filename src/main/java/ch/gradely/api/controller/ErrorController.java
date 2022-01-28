package ch.gradely.api.controller;

import ch.gradely.api.controller.error.Error;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {
	@GetMapping("/error")
	public JSONObject get(@RequestBody Error error) {
		JSONObject response = new JSONObject();
		String cause = null;
		switch (error.getCause()) {
			case SQL_ERROR:
				cause = "Database error";
				break;
			case UNKNOWN_SCHOOL:
				cause = "This school could not be found";
				break;
			case UNKNOWN_SUBJECT:
				cause = "This subject could not be found";
				break;
			case UNKNOWN_SEMESTER:
				cause = "This semester could not be found";
				break;
			case UNKNOWN_GRADE:
				cause = "This grade could not be found";
				break;
			case UNKNOWN_COLUMN:
				cause = "This column does not exist";
				break;
			case INVALID_EMAIL:
				cause = "This email is either already registered or is invalid";
				break;
			case INVALID_PASSWORD:
				cause = "This password does not meet our criteria";
				break;
			case INVALID_USERNAME:
				cause = "This username is either already taken or is invalid";
				break;
			case INVALID_GRADE_VALUE:
				cause = "This grade value does not meet our criteria";
				break;
			case EMPTY_NAME:
				cause = "The name can not be empty";
				break;
			case VALUE_LOWER_ZERO:
				cause = "The value can not be lower than zero";
				break;
			case VALUE_LOWER_OR_EQUAL_ZERO:
				cause = "The value can not be lower or equal to zero";
				break;
		}
		response.put("cause", cause);
		return response;
	}
}
