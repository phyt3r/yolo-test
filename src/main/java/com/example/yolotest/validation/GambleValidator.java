package com.example.yolotest.validation;

import com.example.yolotest.dto.GambleRequest;
import com.example.yolotest.dto.Message;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class GambleValidator extends BaseValidatorImpl<GambleRequest> {

	@Override
	public List<Message> validate(GambleRequest request) {
		List<Message> messages = super.validate(request);

		if (!messages.isEmpty()) {
			return messages;
		}

		if (request.getBet() == null) {
			messages.add(new Message("Bet is null"));
			return messages;
		}

		if (request.getGuess() == null) {
			messages.add(new Message("Guess is null"));
			return messages;
		}

		if (request.getBet().compareTo(BigDecimal.ZERO) <= 0) {
			messages.add(new Message("Bet is negative or zero"));
		}

		if (request.getGuess() < 1 || request.getGuess() > 100) {
			messages.add(new Message("Guess is out of allowed range (1-100)"));
		}

		return messages;
	}
}
