package ch.gradely.api.controller;

import ch.gradely.api.Validator;
import ch.gradely.api.controller.error.Error;
import ch.gradely.api.controller.error.ErrorType;
import ch.gradely.api.database.objects.School;
import ch.gradely.api.database.repositories.SchoolRepository;
import ch.gradely.api.security.Token;
import org.springframework.web.bind.annotation.*;

@RestController
public class SchoolController extends BaseController {

	@PostMapping("/school/add")
	public Error add(@RequestHeader Token token, @RequestBody School school) {
		if (Validator.checkIfNotEmptyString(school.getName())) {
			if (Validator.checkIfGreaterZero(school.getSemester())) {
				school.setUserId(getUserId(token));
				if (!schoolRepository.insert(school)){
					return new Error(ErrorType.SQL_ERROR);
				}
				return null;
			}
			return new Error(ErrorType.VALUE_LOWER_OR_EQUAL_ZERO);
		}
		return new Error(ErrorType.EMPTY_NAME);
	}

	@PostMapping("/school/update")
	public Error update(@RequestHeader Token token, @RequestBody School school) {
		if (checkSchool(getUserIdString(token), school.getId())) {
			if (Validator.checkIfNotEmptyString(school.getName())) {
				if (Validator.checkIfGreaterZero(school.getSemester())) {
					if (!schoolRepository.update(school)){
						return new Error(ErrorType.SQL_ERROR);
					}
					return null;
				}
				return new Error(ErrorType.VALUE_LOWER_OR_EQUAL_ZERO);
			}
			return new Error(ErrorType.EMPTY_NAME);
		}
		return new Error(ErrorType.UNKNOWN_SCHOOL);
	}

	@DeleteMapping("/school/destroy")
	public Error destroy(@RequestHeader Token token, @RequestBody School school) {
		if (checkSchool(getUserIdString(token), school.getId())) {
			if (!schoolRepository.delete(school)) {
				return new Error(ErrorType.SQL_ERROR);
			}
			return null;
		}
		return new Error(ErrorType.UNKNOWN_SCHOOL);
	}

	private boolean checkSchool(String userId, int schoolId) {
		return schoolRepository.readBy2Columns(SchoolRepository.COLUMN_ID, Integer.toString(schoolId), SchoolRepository.COLUMN_USER_ID, userId).size() == 1;
	}
}
