package com.github.yaogouh.common.base.exception;

/**
 * The class Config exception.
 *
 * @author WYG
 */
public class ConfigException extends RuntimeException {

	private static final long serialVersionUID = -6590918127924604819L;

	/**
	 * Instantiates a new Config exception.
	 *
	 * @param message the message
	 */
	public ConfigException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new Config exception.
	 */
	public ConfigException() {

	}
}
