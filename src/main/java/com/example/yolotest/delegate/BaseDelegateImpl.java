package com.example.yolotest.delegate;

import com.example.yolotest.dto.ErrorResponse;
import com.example.yolotest.dto.Message;
import com.example.yolotest.dto.Request;
import com.example.yolotest.dto.Response;
import com.example.yolotest.validation.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings("unchecked")
public abstract class BaseDelegateImpl<T extends Response, R extends Request> implements BaseDelegate<T, R> {

	@Autowired
	private BaseValidator<R> baseValidator;

	BaseDelegateImpl(BaseValidator<R> baseValidator) {
		this.baseValidator = baseValidator;
	}

	@Override
	public T process(R request) {
		List<Message> messages = baseValidator.validate(request);

		if (!messages.isEmpty()) {
			return (T) new ErrorResponse(messages);
		}

		return processSpecific(request);
	}

	protected abstract T processSpecific(R request);
}
