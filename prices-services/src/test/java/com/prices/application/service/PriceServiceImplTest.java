package com.prices.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import com.prices.model.PriceDTO;

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

		Optional<PriceDTO> resultPrice = this.priceServiceImpl.getApplicablePrice(PRODUCT_ID, BRAND_ID, START_TIME);

		assertTrue(resultPrice.isEmpty());
		verify(this.priceRepository, times(1)).findByProductIdAndBrandIdAndStartDate(eq(PRODUCT_ID), eq(BRAND_ID), eq(START_TIME));
	}

	@Test
	void givenProductIdAndBrandIdAndDate_whenSingleResultFromRepository_thenReturnOptionalWithTheResult() {

		PriceDTO expectedResult = createPrice(1L);

		List<PriceDTO> repositoryResultMock = Arrays.asList(expectedResult);

		Mockito.when(
				priceRepository.findByProductIdAndBrandIdAndStartDate(eq(PRODUCT_ID), eq(BRAND_ID), eq(START_TIME)))
				.thenReturn(Optional.of(repositoryResultMock));

		Optional<PriceDTO> resultPrice = this.priceServiceImpl.getApplicablePrice(PRODUCT_ID, BRAND_ID, START_TIME);

		assert (resultPrice.isPresent());
		assertEquals(expectedResult, resultPrice.get());
		verify(this.priceRepository, times(1)).findByProductIdAndBrandIdAndStartDate(eq(PRODUCT_ID), eq(BRAND_ID), eq(START_TIME));
	}
	
	@Test
	void givenProductIdAndBrandIdAndDate_whenMultipleResultsFromRepository_thenReturnOptionalWithFirstResult() {

		PriceDTO expectedResult = createPrice(1L);
		PriceDTO anotherResult = createPrice(2L);

		List<PriceDTO> repositoryResultMock = Arrays.asList(expectedResult, anotherResult);

		Mockito.when(
				priceRepository.findByProductIdAndBrandIdAndStartDate(eq(PRODUCT_ID), eq(BRAND_ID), eq(START_TIME)))
				.thenReturn(Optional.of(repositoryResultMock));

		Optional<PriceDTO> resultPrice = this.priceServiceImpl.getApplicablePrice(PRODUCT_ID, BRAND_ID, START_TIME);

		assertTrue(resultPrice.isPresent());
		assertEquals(expectedResult, resultPrice.get());
		verify(this.priceRepository, times(1)).findByProductIdAndBrandIdAndStartDate(eq(PRODUCT_ID), eq(BRAND_ID), eq(START_TIME));
	}

	private static PriceDTO createPrice(Long id) {
		return new PriceDTO(id, BRAND_ID, START_TIME, START_TIME, PRODUCT_ID, 1L, id, 125D, "EUR");
	}

}
