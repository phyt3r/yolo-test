package com.example.yolotest.validation;

import com.example.yolotest.dto.Message;
import com.example.yolotest.dto.Request;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseValidatorImpl<R extends Request> implements BaseValidator<R> {

	@Override
	public List<Message> validate(R request) {
		List<Message> messages = new ArrayList<>();

		if (request == null) {
			messages.add(new Message("Request is null"));
		}

		return messages;
	}
}
