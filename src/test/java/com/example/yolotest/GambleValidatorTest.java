package com.example.yolotest;

import com.example.yolotest.dto.GambleRequest;
import com.example.yolotest.dto.Message;
import com.example.yolotest.validation.GambleValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GambleValidatorTest {
	private static final Logger LOGGER = Logger.getLogger(GambleValidatorTest.class.getName());

	private GambleValidator gambleValidator;
	private GambleRequest gambleRequest;

	@Before
	public void setup() {
		gambleValidator = Mockito.spy(new GambleValidator());
		gambleRequest = new GambleRequest();
		LOGGER.info("Setup completed");
	}

	@Test
	public void validateReturnsNoErrorsWhenRequestIsValid() {
		gambleRequest.setBet(new BigDecimal(10));
		gambleRequest.setGuess(50);

		List<Message> messages = gambleValidator.validate(gambleRequest);

		LOGGER.info("Validation completed with " + messages.size() + " messages");

		assertEquals(0, messages.size());
	}

	@Test
	public void validateReturnsErrorWhenRequestIsNull() {
		List<Message> messages = gambleValidator.validate(null);

		LOGGER.info("Validation completed with " + messages.size() + " messages");

		assertEquals(1, messages.size());
		assertEquals("Request is null", messages.get(0).getMessage());
	}

	@Test
	public void validateReturnsErrorWhenBetIsNull() {
		gambleRequest.setGuess(50);

		List<Message> messages = gambleValidator.validate(gambleRequest);

		LOGGER.info("Validation completed with " + messages.size() + " messages");

		assertEquals(1, messages.size());
		assertEquals("Bet is null", messages.get(0).getMessage());
	}

	@Test
	public void validateReturnsErrorWhenGuessIsNull() {
		gambleRequest.setBet(new BigDecimal(10));

		List<Message> messages = gambleValidator.validate(gambleRequest);

		LOGGER.info("Validation completed with " + messages.size() + " messages");

		assertEquals(1, messages.size());
		assertEquals("Guess is null", messages.get(0).getMessage());
	}

	@Test
	public void validateReturnsErrorWhenBetIsZeroOrNegative() {
		gambleRequest.setBet(BigDecimal.ZERO);
		gambleRequest.setGuess(50);

		List<Message> messages = gambleValidator.validate(gambleRequest);

		LOGGER.info("Validation completed with " + messages.size() + " messages");

		assertEquals(1, messages.size());
		assertEquals("Bet is negative or zero", messages.get(0).getMessage());
	}

	@Test
	public void validateReturnsErrorWhenGuessIsOutOfRange() {
		gambleRequest.setBet(new BigDecimal(10));
		gambleRequest.setGuess(101);

		List<Message> messages = gambleValidator.validate(gambleRequest);

		LOGGER.info("Validation completed with " + messages.size() + " messages");

		assertEquals(1, messages.size());
		assertEquals("Guess is out of allowed range (1-100)", messages.get(0).getMessage());
	}

	@Test
	public void validateReturnsTwoErrorsWhenGuessBetAndGuessAreNegative() {
		gambleRequest.setBet(new BigDecimal(-10));
		gambleRequest.setGuess(-101);

		List<Message> messages = gambleValidator.validate(gambleRequest);

		LOGGER.info("Validation completed with " + messages.size() + " messages");

		assertEquals(2, messages.size());
		assertEquals("Bet is negative or zero", messages.get(0).getMessage());
		assertEquals("Guess is out of allowed range (1-100)", messages.get(1).getMessage());
	}
}