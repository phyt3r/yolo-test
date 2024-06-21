package com.example.yolotest.util;

import org.springframework.stereotype.Component;

@Component
public class RandomGenerationUtil {

	public int generateRandomNumber(int min, int max) {
		return (int) (Math.random() * (max - min + 1) + min);
	}
}
