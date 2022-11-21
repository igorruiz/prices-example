package com.prices.application.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prices.adapters.repository.PricesRepository;
import com.prices.application.model.entities.PriceEntity;

@Repository
public interface PriceDBRepository extends JpaRepository<PriceEntity, Long>, PricesRepository<PriceEntity> {

	@Override
	@Query("SELECT P FROM PRICES P WHERE PRODUCT_ID = ?1 AND BRAND_ID = ?2 AND START_DATE <= ?3 AND END_DATE >= ?3 ORDER BY PRIORITY DESC")
	Optional<List<PriceEntity>> findByProductIdAndBrandIdAndStartDate(Long productId, Long brandId, ZonedDateTime startDate);

}
