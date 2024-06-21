package com.example.yolotest.service;

import com.example.yolotest.delegate.GambleDelegate;
import com.example.yolotest.dto.GambleRequest;
import com.example.yolotest.dto.Response;
import org.springframework.stereotype.Service;

@Service
public class GambleServiceImpl implements GambleService {

	private GambleDelegate gambleDelegate;

	public GambleServiceImpl(GambleDelegate gambleDelegate) {
		this.gambleDelegate = gambleDelegate;
	}

	@Override
	public Response gamble(GambleRequest request) {
		return gambleDelegate.process(request);
	}
}
