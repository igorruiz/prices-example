package com.prices.application.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.prices.adapters.service.PricesService;
import com.prices.application.mapper.PriceEntityMapper;
import com.prices.application.model.entities.PriceEntity;
import com.prices.application.repository.PriceDBRepository;
import com.prices.model.PriceDTO;

@Service
public class PriceServiceImpl implements PricesService{
	
	@Autowired
	PriceDBRepository priceRepository;
	
	@Autowired
	PriceEntityMapper priceEntityMapper;

	@Override
	public Optional<PriceDTO> getApplicablePrice(Long productId, Long brandId, ZonedDateTime startDate) {
		Optional<List<PriceEntity>> pricesList = this.priceRepository.findByProductIdAndBrandIdAndStartDate(productId, brandId, startDate);
		if(pricesList.isEmpty() || CollectionUtils.isEmpty(pricesList.get())) {
			return Optional.empty();
		}
		return Optional.of(priceEntityMapper.priceEntityToPriceDTO(pricesList.get().get(0)));
	}

}
