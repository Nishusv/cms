package com.project.cms.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiException {
	
	public final String message;
	public final Throwable throwable;
	public final HttpStatus httpStatus;
	public final ZonedDateTime timestamp;
	
	

}
