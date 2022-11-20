package com.prices.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prices.adapters.repository.PricesRepository;
import com.prices.application.model.entities.PriceEntity;

@Repository
public interface PriceDBRepository extends JpaRepository<PriceEntity, Long>, PricesRepository {

}
