package ch.gradely.api.controller.error;

public class Error {
	private ErrorType cause;

	public Error(ErrorType cause) {
		this.cause = cause;
	}

	public ErrorType getCause() {
		return cause;
	}

	public void setCause(ErrorType cause) {
		this.cause = cause;
	}
}
