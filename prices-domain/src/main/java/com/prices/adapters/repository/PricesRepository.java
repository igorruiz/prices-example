package com.prices.adapters.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import com.prices.model.Price;

public interface PricesRepository {
	
	Optional<List<Price>> findByProductIdAndBrandIdAndStartDate(Long productId, Long brandId, ZonedDateTime startDate);
	
}
