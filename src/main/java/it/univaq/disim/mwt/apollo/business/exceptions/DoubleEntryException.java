package it.univaq.disim.mwt.apollo.business.exceptions;

@SuppressWarnings("serial")
public class DoubleEntryException extends BusinessException{

	public DoubleEntryException() {
		super();
	}

	public DoubleEntryException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DoubleEntryException(String message, Throwable cause) {
		super(message, cause);
	}

	public DoubleEntryException(String message) {
		super(message);
	}

	public DoubleEntryException(Throwable cause) {
		super(cause);
	}
	
}
