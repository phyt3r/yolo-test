package com.example.yolotest.delegate;

import com.example.yolotest.dto.GambleRequest;
import com.example.yolotest.dto.GambleResponse;
import com.example.yolotest.util.RandomGenerationUtil;
import com.example.yolotest.validation.GambleValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class GambleDelegate extends BaseDelegateImpl<GambleResponse, GambleRequest> {

	BigDecimal chance99 = BigDecimal.valueOf(99);
	BigDecimal chance100 = BigDecimal.valueOf(100);

	private final RandomGenerationUtil randomGenerationUtil;

	public GambleDelegate(GambleValidator validator,
						  RandomGenerationUtil randomGenerationUtil) {
		super(validator);
		this.randomGenerationUtil = randomGenerationUtil;
	}

	@Override
	protected GambleResponse processSpecific(GambleRequest request) {
		int randomlyGeneratedNumber = randomGenerationUtil.generateRandomNumber(1, 100);
		int guess = request.getGuess();
		BigDecimal bet = request.getBet();

		if (guess > randomlyGeneratedNumber) {
			BigDecimal win = bet.multiply(chance99.divide(chance100.subtract(BigDecimal.valueOf(guess))))
					.setScale(2, RoundingMode.HALF_DOWN);

			return new GambleResponse(win);
		}

		return new GambleResponse(BigDecimal.ZERO);
	}
}
