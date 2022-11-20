package com.prices.model;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Price {
	
	private Long id;
	private Long brandId;
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
	private Long productId;
	private Long priceList;
	private Long priority;
	private Double price;
	private String currency;
	
	

}
