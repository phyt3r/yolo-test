package com.example.yolotest;

import com.example.yolotest.controller.GambleController;
import com.example.yolotest.delegate.GambleDelegate;
import com.example.yolotest.dto.ErrorResponse;
import com.example.yolotest.dto.GambleRequest;
import com.example.yolotest.dto.GambleResponse;
import com.example.yolotest.dto.Response;
import com.example.yolotest.service.GambleService;
import com.example.yolotest.service.GambleServiceImpl;
import com.example.yolotest.util.RandomGenerationUtil;
import com.example.yolotest.validation.GambleValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GambleDelegateIntegrationTest {
	private static final Logger LOGGER = Logger.getLogger(GambleDelegateIntegrationTest.class.getName());

	private GambleController gambleController;
	private RandomGenerationUtil randomGenerationUtil;
	private GambleRequest gambleRequest;

	@Before
	public void setup() {
		randomGenerationUtil = Mockito.mock(RandomGenerationUtil.class);
		GambleDelegate gambleDelegate = new GambleDelegate(new GambleValidator(), randomGenerationUtil);
		GambleService gambleService = new GambleServiceImpl(gambleDelegate);
		gambleController = new GambleController(gambleService);
		gambleRequest = new GambleRequest();
		LOGGER.info("Setup completed");
	}

	@Test
	public void gambleReturnsResponseWhenRequestIsValid() {
		gambleRequest.setBet(new BigDecimal("40.5"));
		gambleRequest.setGuess(50);

		ResponseEntity<? extends Response> response = gambleController.gamble(gambleRequest);

		LOGGER.info("Received response: " + response);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void gambleReturnsResponseWhenRequestIsValidAndPLayerWins() {
		gambleRequest.setBet(new BigDecimal("40.5"));
		gambleRequest.setGuess(50);
		when(randomGenerationUtil.generateRandomNumber(1, 100)).thenReturn(49);

		ResponseEntity<? extends Response> response = gambleController.gamble(gambleRequest);

		LOGGER.info("Received response: " + response);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		Assertions.assertEquals(new BigDecimal("80.19"), ((GambleResponse) response.getBody()).getWin());
	}

	@Test
	public void gambleReturnsResponseWhenRequestIsValidAndPLayerLoses() {
		gambleRequest.setBet(new BigDecimal("40.5"));
		gambleRequest.setGuess(50);
		when(randomGenerationUtil.generateRandomNumber(1, 100)).thenReturn(51);

		ResponseEntity<? extends Response> response = gambleController.gamble(gambleRequest);

		LOGGER.info("Received response: " + response);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(BigDecimal.ZERO, ((GambleResponse) response.getBody()).getWin());
	}

	@Test
	public void gambleReturnsResponseWhenRequestIsInValid() {
		gambleRequest.setBet(new BigDecimal("-40.5"));
		gambleRequest.setGuess(-50);
		when(randomGenerationUtil.generateRandomNumber(1, 100)).thenReturn(49);

		ResponseEntity<? extends Response> response = gambleController.gamble(gambleRequest);

		LOGGER.info("Received response: " + response);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		ErrorResponse errorResponse = (ErrorResponse) response.getBody();
		assertEquals(2, errorResponse.getMessages().size());
		assertEquals("Bet is negative or zero", errorResponse.getMessages().get(0).getMessage());
		assertEquals("Guess is out of allowed range (1-100)", errorResponse.getMessages().get(1).getMessage());
	}
}
