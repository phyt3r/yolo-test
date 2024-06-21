package com.example.yolotest;

import com.example.yolotest.util.RandomGenerationUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RandomGenerationUtilTest {
	private static final Logger LOGGER = Logger.getLogger(RandomGenerationUtilTest.class.getName());

	private RandomGenerationUtil randomGenerationUtil;

	@Before
	public void setup() {
		randomGenerationUtil = new RandomGenerationUtil();
		LOGGER.info("Setup completed");
	}

	@Test
	public void generateRandomNumberReturnsNumberWithinRange() {
		int min = 1;
		int max = 100;
		int randomNumber = randomGenerationUtil.generateRandomNumber(min, max);

		LOGGER.info("Generated random number: " + randomNumber);

		assertTrue(randomNumber >= min && randomNumber <= max);
	}

	@Test
	public void generateRandomNumberReturnsDifferentNumbersOnSubsequentCalls() {
		int min1 = 1;
		int max1 = 10;
		int min2 = 11;
		int max2 = 20;
		int firstRandomNumber = randomGenerationUtil.generateRandomNumber(min1, max1);
		int secondRandomNumber = randomGenerationUtil.generateRandomNumber(min2, max2);

		LOGGER.info("Generated first random number: " + firstRandomNumber);
		LOGGER.info("Generated second random number: " + secondRandomNumber);

		assertTrue(firstRandomNumber != secondRandomNumber);
		assertTrue(firstRandomNumber < secondRandomNumber);
	}

	@Test
	public void generateRandomNumberReturnsMinWhenMinEqualsMax() {
		int min = 5;
		int max = 5;
		int randomNumber = randomGenerationUtil.generateRandomNumber(min, max);

		LOGGER.info("Generated random number when min equals max: " + randomNumber);

		assertTrue(randomNumber == min);
	}
}
