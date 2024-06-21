package com.example.yolotest.controller;

import com.example.yolotest.dto.ErrorResponse;
import com.example.yolotest.dto.GambleRequest;
import com.example.yolotest.dto.Message;
import com.example.yolotest.dto.Response;
import com.example.yolotest.service.GambleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class GambleController {

	private final GambleService gambleService;

	public GambleController(GambleService gambleService) {
		this.gambleService = gambleService;
	}

	@PostMapping("/gamble")
	public ResponseEntity<? extends Response> gamble(@RequestBody GambleRequest request) {
		try {
			Response response = gambleService.gamble(request);

			if (response instanceof ErrorResponse) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			return ResponseEntity.ok().body(response);
		} catch (RuntimeException e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse(
							Collections.singletonList(new Message(e.getMessage()))));
		}
	}
}
