package com.materials.shared.validator;

import com.materials.shared.APPConstants;

import gwt.material.design.client.base.validator.RegExValidator;

public class EmailValidator extends RegExValidator {

	public EmailValidator() {
		super(APPConstants.EMAIL_PATTERN, "Email invalid");
	}

	@Override
	public int getPriority() {
		return 0;
	}
}
