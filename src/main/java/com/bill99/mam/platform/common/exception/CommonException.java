package com.bill99.mam.platform.common.exception;

public class CommonException extends RuntimeException {
	private static final long serialVersionUID = -6403477978143189009L;
	private String errorCode;

	public CommonException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public CommonException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public CommonException(String errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public CommonException(String message, String errorCode, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommonException [");
		if (this.getMessage() != null) {
			builder.append(this.getMessage()).append(",");
		}
		if (errorCode != null) {
			builder.append("errorCode=").append(errorCode).append(", ");
		}

		builder.append("]\n");
		Throwable t = this.getCause();
		while (t != null) {
			builder.append("\t\tCause: ").append(
					t.getClass().getCanonicalName());
			builder.append(":").append(t.getMessage()).append("\n");
			t = t.getCause();
		}
		return builder.toString();
	}
}
