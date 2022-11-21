package com.prices.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.prices.application.mapper.PriceEntityMapper;
import com.prices.application.model.entities.PriceEntity;
import com.prices.application.repository.PriceDBRepository;
import com.prices.model.PriceDTO;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

	@InjectMocks
	PriceServiceImpl priceServiceImpl;

	@Mock
	PriceDBRepository priceRepository;

	@Mock
	PriceEntityMapper priceEntityMapper;

	private static final Long PRODUCT_ID = 12345L;
	private static final Long BRAND_ID = 1L;
	private static final ZonedDateTime START_TIME = ZonedDateTime.now();

	@Test
	void givenProductIdAndBrandIdAndDate_whenNoResultFromRepository_thenReturnOptionalEmpty() {

		Mockito.when(priceRepository.findByProductIdAndBrandIdAndStartDate(PRODUCT_ID, BRAND_ID, START_TIME))
				.thenReturn(Optional.empty());

		Optional<PriceDTO> resultPrice = this.priceServiceImpl.getApplicablePrice(PRODUCT_ID, BRAND_ID, START_TIME);

		assertTrue(resultPrice.isEmpty());
		verify(this.priceRepository, times(1)).findByProductIdAndBrandIdAndStartDate(anyLong(), anyLong(),
				any(ZonedDateTime.class));
	}

	@Test
	void givenProductIdAndBrandIdAndDate_whenSingleResultFromRepository_thenReturnOptionalWithTheResult() {

		PriceEntity expectedEntity = createPrice(1L);

		List<PriceEntity> repositoryResultMock = Arrays.asList(expectedEntity);

		PriceDTO expectedDTO = createPriceDTO(1L);

		Mockito.when(priceRepository.findByProductIdAndBrandIdAndStartDate(PRODUCT_ID, BRAND_ID, START_TIME))
				.thenReturn(Optional.of(repositoryResultMock));
		Mockito.when(priceEntityMapper.priceEntityToPriceDTO(expectedEntity)).thenReturn(expectedDTO);

		Optional<PriceDTO> resultPrice = this.priceServiceImpl.getApplicablePrice(PRODUCT_ID, BRAND_ID, START_TIME);

		assert (resultPrice.isPresent());
		assertEquals(expectedDTO, resultPrice.get());
		verify(this.priceRepository, times(1)).findByProductIdAndBrandIdAndStartDate(anyLong(), anyLong(),
				any(ZonedDateTime.class));
	}

	@Test
	void givenProductIdAndBrandIdAndDate_whenMultipleResultsFromRepository_thenReturnOptionalWithFirstResult() {

		PriceEntity expectedEntity = createPrice(1L);
		PriceEntity anotherResult = createPrice(2L);

		List<PriceEntity> repositoryResultMock = Arrays.asList(expectedEntity, anotherResult);

		PriceDTO expectedDTO = createPriceDTO(1L);

		Mockito.when(
				priceRepository.findByProductIdAndBrandIdAndStartDate(PRODUCT_ID, BRAND_ID, START_TIME))
				.thenReturn(Optional.of(repositoryResultMock));
		Mockito.when(priceEntityMapper.priceEntityToPriceDTO(expectedEntity)).thenReturn(expectedDTO);

		Optional<PriceDTO> resultPrice = this.priceServiceImpl.getApplicablePrice(PRODUCT_ID, BRAND_ID, START_TIME);

		assertTrue(resultPrice.isPresent());
		assertEquals(expectedDTO, resultPrice.get());
		verify(this.priceRepository, times(1)).findByProductIdAndBrandIdAndStartDate(anyLong(), anyLong(),
				any(ZonedDateTime.class));
	}

	private static PriceEntity createPrice(Long id) {
		return new PriceEntity(id, Double.valueOf(125D), "EUR", Long.valueOf(1L), BRAND_ID, START_TIME, START_TIME,
				PRODUCT_ID, Long.valueOf(1L));
	}

	private static PriceDTO createPriceDTO(Long id) {
		return new PriceDTO(id, BRAND_ID, START_TIME, START_TIME, PRODUCT_ID, Long.valueOf(1L), Long.valueOf(1L),
				Double.valueOf(125D), "EUR");
	}
}
