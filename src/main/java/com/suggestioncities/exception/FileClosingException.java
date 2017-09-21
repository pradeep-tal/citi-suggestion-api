package com.suggestioncities.exception;

@SuppressWarnings("serial")
public class FileClosingException extends Exception {

	private static final long serialVersionUID = 134032541474001981L;

	public FileClosingException(Throwable e) {

		super(e);
	}

	public FileClosingException(String message) {

		super(message);
	}
	
}
