package com.prices.application.mapper;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.igor.openapi.model.Price;
import com.prices.model.PriceDTO;

@Mapper(componentModel = "spring")
public interface PriceMapper{

	@Mapping(source = "priceList", target = "rateId")
	Price priceDTOtoPrice(PriceDTO priceDTO);
	
	default OffsetDateTime zonedDateTimeToOffsetDateTime(ZonedDateTime zonedDateTime) {
		return zonedDateTime.toOffsetDateTime();
	}

}
