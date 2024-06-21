package com.example.yolotest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GambleResponse extends Response {
	private BigDecimal win;
}
