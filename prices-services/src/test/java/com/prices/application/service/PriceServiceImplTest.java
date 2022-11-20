package com.prices.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prices.application.PriceDBRepository;
import com.prices.model.Price;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

	@InjectMocks
	PriceServiceImpl priceServiceImpl;

	@Mock
	PriceDBRepository priceRepository;

	private static final Long PRODUCT_ID = 12345L;
	private static final Long BRAND_ID = 1L;
	private static final ZonedDateTime START_TIME = ZonedDateTime.now();

	@Test
	void givenProductIdAndBrandIdAndDate_whenNoResultFromRepository_thenReturnOptionalEmpty() {

		Mockito.when(
				priceRepository.findByProductIdAndBrandIdAndStartDate(eq(PRODUCT_ID), eq(BRAND_ID), eq(START_TIME)))
				.thenReturn(Optional.empty());

		Optional<Price> resultPrice = this.priceServiceImpl.getApplicablePrice(PRODUCT_ID, BRAND_ID, START_TIME);

		assertThat(resultPrice.isEmpty());
	}

	@Test
	void givenProductIdAndBrandIdAndDate_whenSingleResultFromRepository_thenReturnOptionalWithTheResult() {

		Price expectedResult = createPrice(1L);

		List<Price> repositoryResultMock = Arrays.asList(expectedResult);

		Mockito.when(
				priceRepository.findByProductIdAndBrandIdAndStartDate(eq(PRODUCT_ID), eq(BRAND_ID), eq(START_TIME)))
				.thenReturn(Optional.of(repositoryResultMock));

		Optional<Price> resultPrice = this.priceServiceImpl.getApplicablePrice(PRODUCT_ID, BRAND_ID, START_TIME);

		assert (resultPrice.isPresent());
		assertEquals(expectedResult, resultPrice.get());
	}
	
	@Test
	void givenProductIdAndBrandIdAndDate_whenMultipleResultsFromRepository_thenReturnOptionalWithFirstResult() {

		Price expectedResult = createPrice(1L);
		Price anotherResult = createPrice(2L);

		List<Price> repositoryResultMock = Arrays.asList(expectedResult, anotherResult);

		Mockito.when(
				priceRepository.findByProductIdAndBrandIdAndStartDate(eq(PRODUCT_ID), eq(BRAND_ID), eq(START_TIME)))
				.thenReturn(Optional.of(repositoryResultMock));

		Optional<Price> resultPrice = this.priceServiceImpl.getApplicablePrice(PRODUCT_ID, BRAND_ID, START_TIME);

		assert (resultPrice.isPresent());
		assertEquals(expectedResult, resultPrice.get());
	}

	private static Price createPrice(Long id) {
		return new Price(id, BRAND_ID, START_TIME, START_TIME, PRODUCT_ID, 1L, id, 125D, "EUR");
	}

}
