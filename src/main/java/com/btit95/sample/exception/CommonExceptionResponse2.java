package com.btit95.sample.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommonExceptionResponse2 {
	private String serverName;
	private Date timestamp;
	private String message;
	private String details;
}
