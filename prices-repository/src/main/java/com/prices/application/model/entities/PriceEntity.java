package com.prices.application.model.entities;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "PRICES")
public class PriceEntity{
	
	@Id
	private Long id;
	private Double price;
	private String currency;
	private Long priority;
	
	@Column(name = "BRAND_ID")
	private Long brandId;
	@Column(name = "START_DATE")
	private ZonedDateTime startDate;
	@Column(name = "END_DATE")
	private ZonedDateTime endDate;
	@Column(name = "PRODUCT_ID")
	private Long productId;
	@Column(name = "PRICE_LIST")
	private Long priceList;

}