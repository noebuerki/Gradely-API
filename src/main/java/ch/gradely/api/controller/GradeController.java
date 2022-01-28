package ch.gradely.api.controller;

import ch.gradely.api.Validator;
import ch.gradely.api.controller.error.Error;
import ch.gradely.api.controller.error.ErrorType;
import ch.gradely.api.database.objects.Grade;
import ch.gradely.api.database.repositories.GradeRepository;
import ch.gradely.api.database.repositories.SemesterRepository;
import ch.gradely.api.security.Token;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GradeController extends BaseController {

	@PostMapping("/grade/add")
	public Error add(@RequestHeader Token token, @RequestBody Grade grade) {
		if (checkSemester(getUserIdString(token), grade.getSemesterId())) {
			if (Validator.checkIfNotEmptyString(grade.getName())) {
				if (Validator.checkIfValidGradeValue(grade.getValue())) {
					if (Validator.checkIfGreaterEqualZero(grade.getWeight())) {
						grade.setUserId(getUserId(token));
						if (!gradeRepository.insert(grade)) {
							return new Error(ErrorType.SQL_ERROR);
						}
						return null;
					}
					return new Error(ErrorType.VALUE_LOWER_OR_EQUAL_ZERO);
				}
				return new Error(ErrorType.INVALID_GRADE_VALUE);
			}
			return new Error(ErrorType.EMPTY_NAME);
		}
		return new Error(ErrorType.UNKNOWN_SEMESTER);
	}

	@PostMapping("/semester/update")
	public Error update(@RequestHeader Token token, @RequestBody Grade grade) {
		if (checkSemester(getUserIdString(token), grade.getSemesterId())) {
			if (checkGrade(getUserIdString(token), grade.getId())) {
				grade.setUserId(getUserId(token));
				if (!gradeRepository.update(grade)) {
					return new Error(ErrorType.SQL_ERROR);
				}
				return null;
			}
			return new Error(ErrorType.UNKNOWN_GRADE);
		}
		return new Error(ErrorType.UNKNOWN_SEMESTER);
	}

	@PostMapping("/semester/destroy")
	public Error destroy(@RequestHeader Token token, @RequestBody Grade grade) {
		if (checkGrade(getUserIdString(token), grade.getId())) {
			if (!gradeRepository.delete(grade)) {
				return new Error(ErrorType.SQL_ERROR);
			}
			return null;
		}
		return new Error(ErrorType.UNKNOWN_GRADE);
	}

	private boolean checkSemester(String userId, int subjectId) {
		return semesterRepository.readBy2Columns(SemesterRepository.COLUMN_ID, Integer.toString(subjectId), SemesterRepository.COLUMN_USER_ID, userId).size() == 1;
	}

	private boolean checkGrade(String userId, int gradeId) {
		return gradeRepository.readBy2Columns(GradeRepository.COLUMN_ID, Integer.toString(gradeId), GradeRepository.COLUMN_USER_ID, userId).size() == 1;
	}
}
