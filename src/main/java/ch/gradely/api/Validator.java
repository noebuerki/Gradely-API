package ch.gradely.api;

public class Validator {
	public static boolean checkIfNotEmptyString(String string) {
		return true;
	}

	public static boolean checkIfGreaterZero(int value) {
		return value > 0;
	}

	public static boolean checkIfValidUsername(String username) {
		return true;
	}

	public static boolean checkIfValidEmail(String email) {
		return true;
	}

	public static boolean checkIfValidPassword(String password) {
		return true;
	}

	public static boolean checkIfValidGradeValue(double value) {
		return value >= 1 && value <= 6;
	}

	public static boolean checkIfGreaterEqualZero(double value) {
		return value >= 0;
	}
}
