package com.prices.application.mapper;

import org.mapstruct.Mapper;

import com.prices.application.model.entities.PriceEntity;
import com.prices.model.PriceDTO;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {
	
	PriceDTO priceEntityToPriceDTO(PriceEntity priceEntity);
	
}
