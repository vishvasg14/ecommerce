package com.example.demo.exception;


public class CustomException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1177698906888224583L;

	public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }
}
