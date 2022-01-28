package ch.gradely.api.controller;

import ch.gradely.api.database.repositories.*;
import ch.gradely.api.security.AuthorizationFilter;
import ch.gradely.api.security.Token;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class BaseController {

	protected UserRepository userRepository = new UserRepository();
	protected GradeRepository gradeRepository = new GradeRepository();
	protected SchoolRepository schoolRepository = new SchoolRepository();
	protected SubjectRepository subjectRepository = new SubjectRepository();
	protected SemesterRepository semesterRepository = new SemesterRepository();

	protected String getUserIdString(Token token) {
		return Integer.toString(AuthorizationFilter.getTokenId(token));
	}

	protected int getUserId(Token token) {
		return AuthorizationFilter.getTokenId(token);
	}

	protected Map<String, String> getRequestParameters(String request) {
		JSONObject object = new JSONObject(request);
		Map<String, String> parameters = new HashMap<>();
		Iterator<String> keys = object.keys();

		while (keys.hasNext()) {
			String key = keys.next();
			parameters.put(key, object.getString(key));
		}

		return parameters;
	}
}
