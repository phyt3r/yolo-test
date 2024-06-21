package com.example.yolotest.validation;

import com.example.yolotest.dto.Message;
import com.example.yolotest.dto.Request;

import java.util.List;

public interface BaseValidator<R extends Request> {

	List<Message> validate(R request);
}
