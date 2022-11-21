package com.prices.adapters.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface PricesRepository<T> {
	
	Optional<List<T>> findByProductIdAndBrandIdAndStartDate(Long productId, Long brandId, ZonedDateTime startDate);
	
}
