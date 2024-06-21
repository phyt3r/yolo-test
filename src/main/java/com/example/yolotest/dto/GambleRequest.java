package com.example.yolotest.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class GambleRequest extends Request {
	private BigDecimal bet;
	private Integer guess;
}
