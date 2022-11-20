package com.prices.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import com.igor.openapi.model.Price;
import com.prices.adapters.service.PricesService;
import com.prices.application.mapper.PriceMapper;
import com.prices.model.PriceDTO;

@ExtendWith(MockitoExtension.class)
class PricesControllerTest {

	@InjectMocks
	PricesController pricesController;

	@Mock
	PricesService pricesService;

	@Mock
	PriceMapper priceMapper;

	private static final Long PRODUCT_ID = 12345L;
	private static final Long BRAND_ID = 1L;
	private static final Instant NOW = Instant.now();
	private static final OffsetDateTime OFFSET_TIME = OffsetDateTime.ofInstant(NOW, ZoneId.of("Z"));
	private static final ZonedDateTime START_TIME = ZonedDateTime.ofInstant(NOW, ZoneId.of("Z"));

	@Test
	void givenProductIdAndBrandIdAndDate_whenNoResultFromService_thenReturn204AndDontInvokeMapper() {

		when(this.pricesService.getApplicablePrice(PRODUCT_ID, BRAND_ID, START_TIME)).thenReturn(Optional.empty());
		
		ResponseEntity<Price> response = this.pricesController.getPrice(PRODUCT_ID, BRAND_ID, OFFSET_TIME);
		
		assertEquals(204, response.getStatusCodeValue());
		assertTrue(ObjectUtils.isEmpty(response.getBody()));
		verify(this.pricesService, times(1)).getApplicablePrice(anyLong(), anyLong(), any(ZonedDateTime.class));
		verify(this.priceMapper, times(0)).priceDTOtoPrice(any(PriceDTO.class));
	}

	@Test
	void givenProductIdAndBrandIdAndDate_whenSingleResultFromRepository_thenReturnResultWith200() {

		Price priceResult = createPrice(1L);
		when(this.pricesService.getApplicablePrice(PRODUCT_ID, BRAND_ID, START_TIME)).thenReturn(Optional.of(createPriceDTO(1L)));
		when(this.priceMapper.priceDTOtoPrice(any(PriceDTO.class))).thenReturn(priceResult);
		
		ResponseEntity<Price> response = this.pricesController.getPrice(PRODUCT_ID, BRAND_ID, OFFSET_TIME);

		assertEquals(200, response.getStatusCodeValue());
		assertTrue(!ObjectUtils.isEmpty(response.getBody()));
		assertEquals(priceResult, response.getBody());
		verify(this.pricesService, times(1)).getApplicablePrice(anyLong(), anyLong(), any(ZonedDateTime.class));
		verify(this.priceMapper, times(1)).priceDTOtoPrice(any(PriceDTO.class));
	}


	private static PriceDTO createPriceDTO(Long id) {
		return new PriceDTO(id, BRAND_ID, START_TIME, START_TIME, PRODUCT_ID, 1L, id, 125D, "EUR");
	}	
	
	private static Price createPrice(Long id) {
		
		Price price = new Price();
		price.setBrandId(BRAND_ID);
		price.setEndDate(OFFSET_TIME);
		price.setStartDate(OFFSET_TIME);
		price.setProductId(PRODUCT_ID);
		price.setRateId(1L);
		price.setPrice(125D);
		
		return price;
	}

}
