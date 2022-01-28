package ch.gradely.api.controller;

import ch.gradely.api.Validator;
import ch.gradely.api.controller.error.Error;
import ch.gradely.api.controller.error.ErrorType;
import ch.gradely.api.database.objects.Subject;
import ch.gradely.api.database.repositories.SchoolRepository;
import ch.gradely.api.database.repositories.SubjectRepository;
import ch.gradely.api.security.Token;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubjectController extends BaseController {

	@PostMapping("/subject/add")
	public Error add(@RequestHeader Token token, @RequestBody Subject subject) {
		if (checkSchool(getUserIdString(token), subject.getSchoolId())) {
			if (Validator.checkIfNotEmptyString(subject.getName())) {
				subject.setUserId(getUserId(token));
				if (!subjectRepository.insert(subject)) {
					return new Error(ErrorType.SQL_ERROR);
				}
				return null;
			}
			return new Error(ErrorType.EMPTY_NAME);
		}
		return new Error(ErrorType.UNKNOWN_SCHOOL);
	}

	@PostMapping("/subject/update")
	public Error update(@RequestHeader Token token, @RequestBody Subject subject) {
		if (checkSchool(getUserIdString(token), subject.getSchoolId())) {
			if (checkSubject(getUserIdString(token), subject.getId())) {
				if (Validator.checkIfNotEmptyString(subject.getName())) {
					if (!subjectRepository.update(subject)) {
						return new Error(ErrorType.SQL_ERROR);
					}
					return null;
				}
				return new Error(ErrorType.EMPTY_NAME);
			}
			return new Error(ErrorType.UNKNOWN_SUBJECT);
		}
		return new Error(ErrorType.UNKNOWN_SCHOOL);
	}

	@DeleteMapping("/subject/destroy")
	public Error destroy(@RequestHeader Token token, @RequestBody Subject subject) {
		if (checkSubject(getUserIdString(token), subject.getId())) {
			if (!subjectRepository.delete(subject)) {
				return new Error(ErrorType.SQL_ERROR);
			}
			return null;
		}
		return new Error(ErrorType.UNKNOWN_SUBJECT);
	}

	private boolean checkSchool(String userId, int schoolId) {
		return schoolRepository.readBy2Columns(SchoolRepository.COLUMN_ID, Integer.toString(schoolId), SchoolRepository.COLUMN_USER_ID, userId).size() == 1;
	}

	private boolean checkSubject(String userId, int subjectId) {
		return subjectRepository.readBy2Columns(SubjectRepository.COLUMN_ID, Integer.toString(subjectId), SubjectRepository.COLUMN_SCHOOL_ID, userId).size() == 1;
	}
}
