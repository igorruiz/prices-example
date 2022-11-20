package com.prices.application.controller;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.igor.openapi.model.Price;
import com.igor.openapi.prices.PricesApi;
import com.prices.adapters.service.PricesService;
import com.prices.application.mapper.PriceMapper;
import com.prices.model.PriceDTO;

public class PricesController implements PricesApi {

	@Autowired
	PricesService pricesService;

	@Autowired
	PriceMapper priceMapper;

	@Override
	public ResponseEntity<Price> getPrice(@NotNull @Valid Long productId, @NotNull @Valid Long brandId,
			@NotNull @Valid OffsetDateTime startDate) {

		ZonedDateTime zonedStartDate = startDate.toZonedDateTime().withZoneSameInstant(ZoneId.of("Z"));
		Optional<PriceDTO> availablePrice = pricesService.getApplicablePrice(productId, brandId, zonedStartDate);

		if (availablePrice.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}

		Price resultPrice = priceMapper.priceDTOtoPrice(availablePrice.get());
		return ResponseEntity.ok(resultPrice);
	}

}
