package ch.gradely.api.controller;

import ch.gradely.api.Validator;
import ch.gradely.api.controller.error.Error;
import ch.gradely.api.controller.error.ErrorType;
import ch.gradely.api.database.objects.Semester;
import ch.gradely.api.database.repositories.SubjectRepository;
import ch.gradely.api.security.Token;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SemesterController extends BaseController {

	@PostMapping("/semester/add")
	public Error add(@RequestHeader Token token, @RequestBody Semester semester) {
		if (checkSubject(getUserIdString(token), semester.getSubjectId())) {
			if (Validator.checkIfGreaterZero(semester.getSemester())) {
				semester.setUserId(getUserId(token));
				if (!semesterRepository.insert(semester)) {
					return new Error(ErrorType.SQL_ERROR);
				}
				return null;
			}
			return new Error(ErrorType.VALUE_LOWER_OR_EQUAL_ZERO);
		}
		return new Error(ErrorType.UNKNOWN_SUBJECT);
	}

	@PostMapping("/semester/update")
	public Error update(@RequestHeader Token token, @RequestBody Semester semester) {
		if (checkSubject(getUserIdString(token), semester.getSubjectId())) {
			if (checkSemester(getUserIdString(token), semester.getId())) {
				if (Validator.checkIfGreaterZero(semester.getSemester())) {
					semester.setUserId(getUserId(token));
					if (!semesterRepository.update(semester)) {
						return new Error(ErrorType.SQL_ERROR);
					}
					return null;
				}
				return new Error(ErrorType.VALUE_LOWER_OR_EQUAL_ZERO);
			}
			return new Error(ErrorType.UNKNOWN_SEMESTER);
		}
		return new Error(ErrorType.UNKNOWN_SUBJECT);
	}

	@PostMapping("/semester/destroy")
	public Error destroy(@RequestHeader Token token, @RequestBody Semester semester) {
		if (checkSemester(getUserIdString(token), semester.getId())) {
			if (!semesterRepository.delete(semester)) {
				return new Error(ErrorType.SQL_ERROR);
			}
			return null;
		}
		return new Error(ErrorType.UNKNOWN_SUBJECT);
	}

	private boolean checkSubject(String userId, int subjectId) {
		return subjectRepository.readBy2Columns(SubjectRepository.COLUMN_ID, Integer.toString(subjectId), SubjectRepository.COLUMN_SCHOOL_ID, userId).size() == 1;
	}

	private boolean checkSemester(String userId, int subjectId) {
		return subjectRepository.readBy2Columns(SubjectRepository.COLUMN_ID, Integer.toString(subjectId), SubjectRepository.COLUMN_SCHOOL_ID, userId).size() == 1;
	}
}
