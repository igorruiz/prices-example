package com.prices.adapters.service;

import java.time.ZonedDateTime;
import java.util.Optional;

import com.prices.model.Price;

public interface PricesService {
	
	Optional<Price> getApplicablePrice(Long productId, Long brandId, ZonedDateTime startDate);

}
