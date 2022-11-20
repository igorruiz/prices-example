package com.prices.application.mapper;

import org.mapstruct.Mapper;

import com.igor.openapi.model.Price;
import com.prices.model.PriceDTO;

@Mapper
public interface PriceMapper{

	Price priceDTOtoPrice(PriceDTO priceDTO);

}
