package com.prices.adapters.service;

import java.time.ZonedDateTime;
import java.util.Optional;

import com.prices.model.PriceDTO;

public interface PricesService {
	
	Optional<PriceDTO> getApplicablePrice(Long productId, Long brandId, ZonedDateTime startDate);

}
