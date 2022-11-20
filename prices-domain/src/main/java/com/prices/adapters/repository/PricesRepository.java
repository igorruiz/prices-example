package com.prices.adapters.repository;

import java.time.ZonedDateTime;
import java.util.Optional;

import com.prices.model.Price;

public interface PricesRepository {
	
	Optional<Price> findByProductIdAndBrandIdAndStartDate(Long productId, Long brandId, ZonedDateTime startDate);
	
}
