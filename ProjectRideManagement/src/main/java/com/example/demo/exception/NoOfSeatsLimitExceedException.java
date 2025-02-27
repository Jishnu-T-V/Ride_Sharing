package com.example.demo.exception;

public class NoOfSeatsLimitExceedException extends RuntimeException {
	public NoOfSeatsLimitExceedException(String message) {
		super(message);
	}
}
