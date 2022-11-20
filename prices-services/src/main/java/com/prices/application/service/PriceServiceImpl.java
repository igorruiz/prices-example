package com.prices.application.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.prices.adapters.service.PricesService;
import com.prices.application.PriceDBRepository;
import com.prices.model.PriceDTO;

@Service
public class PriceServiceImpl implements PricesService{
	
	@Autowired
	PriceDBRepository priceRepository;

	@Override
	public Optional<PriceDTO> getApplicablePrice(Long productId, Long brandId, ZonedDateTime startDate) {
		Optional<List<PriceDTO>> pricesList = this.priceRepository.findByProductIdAndBrandIdAndStartDate(productId, brandId, startDate);
		if(pricesList.isEmpty() || CollectionUtils.isEmpty(pricesList.get())) {
			return Optional.empty();
		}
		return Optional.of(pricesList.get().get(0));
	}

}
