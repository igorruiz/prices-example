package com.prices.adapters.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import com.prices.model.PriceDTO;

public interface PricesRepository {
	
	Optional<List<PriceDTO>> findByProductIdAndBrandIdAndStartDate(Long productId, Long brandId, ZonedDateTime startDate);
	
}
