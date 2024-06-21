package com.example.yolotest.service;

import com.example.yolotest.dto.GambleRequest;
import com.example.yolotest.dto.Response;

public interface GambleService {

	Response gamble(GambleRequest request);
}
