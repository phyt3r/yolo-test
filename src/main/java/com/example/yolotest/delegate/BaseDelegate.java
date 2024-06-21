package com.example.yolotest.delegate;

import com.example.yolotest.dto.Request;
import com.example.yolotest.dto.Response;

public interface BaseDelegate<T extends Response, R extends Request> {

	T process(R request);
}
