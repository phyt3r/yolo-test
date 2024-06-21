package com.example.yolotest;

import com.example.yolotest.delegate.GambleDelegate;
import com.example.yolotest.dto.GambleRequest;
import com.example.yolotest.dto.GambleResponse;
import com.example.yolotest.util.RandomGenerationUtil;
import com.example.yolotest.validation.GambleValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GambleDelegateTest {
	private static final Logger LOGGER = Logger.getLogger(GambleDelegateTest.class.getName());

	private GambleDelegate gambleDelegate;
	private GambleRequest gambleRequest;
	private RandomGenerationUtil randomGenerationUtil;

	@Before
	public void setup() {
		randomGenerationUtil = Mockito.mock(RandomGenerationUtil.class);
		gambleDelegate = new GambleDelegate(Mockito.mock(GambleValidator.class), randomGenerationUtil);
		gambleRequest = new GambleRequest();
		LOGGER.info("Setup completed");
	}

	@Test
	public void gambleReturnsWinWhenGuessIsHigherThanRandomNumber() {
		gambleRequest.setBet(new BigDecimal("40.5"));
		gambleRequest.setGuess(50);
		when(randomGenerationUtil.generateRandomNumber(1, 100)).thenReturn(49);

		GambleResponse response = gambleDelegate.process(gambleRequest);

		LOGGER.info("Expected win: 80.19, Actual win: " + response.getWin());

		assertEquals(new BigDecimal("80.19"), response.getWin());
	}

	@Test
	public void gambleReturnsZeroWinWhenGuessIsLowerThanRandomNumber() {
		gambleRequest.setBet(new BigDecimal(10));
		gambleRequest.setGuess(50);
		when(randomGenerationUtil.generateRandomNumber(1, 100)).thenReturn(51);

		GambleResponse response = gambleDelegate.process(gambleRequest);

		LOGGER.info("Expected win: 0, Actual win: " + response.getWin());

		assertEquals(BigDecimal.ZERO, response.getWin());
	}

	@Test
	public void gambleReturnsZeroWinWhenGuessIsEqualToRandomNumber() {
		gambleRequest.setBet(new BigDecimal(10));
		gambleRequest.setGuess(50);
		when(randomGenerationUtil.generateRandomNumber(1, 100)).thenReturn(50);

		GambleResponse response = gambleDelegate.process(gambleRequest);

		LOGGER.info("Expected win: 0, Actual win: " + response.getWin());

		assertEquals(BigDecimal.ZERO, response.getWin());
	}

	@Test
	public void testRTP() throws InterruptedException, ExecutionException {
		int numThreads = 24;
		int numGames = 1_000_000;
		BigDecimal totalBet;
		BigDecimal totalWin = BigDecimal.ZERO;

		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		List<Future<BigDecimal>> futures = new ArrayList<>();

		for (int i = 0; i < numGames; i++) {
			futures.add(executor.submit(() -> {
				GambleDelegate gambleDelegate = new GambleDelegate(new GambleValidator(), new RandomGenerationUtil());
				GambleRequest gambleRequest = new GambleRequest();
				gambleRequest.setBet(BigDecimal.ONE);
				gambleRequest.setGuess(50);
				GambleResponse response = gambleDelegate.process(gambleRequest);
				return response.getWin();
			}));
		}

		for (Future<BigDecimal> future : futures) {
			totalWin = totalWin.add(future.get());
		}

		totalBet = BigDecimal.valueOf(numGames);
		BigDecimal rtp = totalWin.divide(totalBet, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

		LOGGER.info("Total Bet: " + totalBet);
		LOGGER.info("Total Win: " + totalWin);
		LOGGER.info("RTP: " + rtp + "%");

		executor.shutdown();

		assertTrue(rtp.compareTo(BigDecimal.ZERO) > 0);
	}
}
