package com.prices.application.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PricesIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	private static Long PRODUCT_ID = 35455L;
	private static Long BRAND_ID = 1L;
	
	@Test
	void givenRequest_whenInvokingWithoutParams_thenReturnBadRequest() throws Exception {
		this.mockMvc.perform(get("/prices"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void givenProductIdAndBrandId_whenRequestingForDate14062020at1000_thenReturn35_50() throws Exception {

		this.mockMvc.perform(get("/prices")
				.param("productId", PRODUCT_ID.toString())
				.param("brandId", BRAND_ID.toString())
				.param("startDate", "2020-06-14T10:00:00Z"))
		.andExpect(status().is(200))
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.price").exists())
		.andExpect(jsonPath("$.price").value("35.5"));
	}
	@Test
	void givenProductIdAndBrandId_whenRequestingForDate14062020at1600_thenReturn25_45() throws Exception {

		this.mockMvc.perform(get("/prices")
				.param("productId", PRODUCT_ID.toString())
				.param("brandId", BRAND_ID.toString())
				.param("startDate", "2020-06-14T16:00:00Z"))
		.andExpect(status().is(200))
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.price").exists())
		.andExpect(jsonPath("$.price").value("25.45"));
	}
	@Test
	void givenProductIdAndBrandId_whenRequestingForDate14062020at2100_thenReturn35_50() throws Exception {

		this.mockMvc.perform(get("/prices")
				.param("productId", PRODUCT_ID.toString())
				.param("brandId", BRAND_ID.toString())
				.param("startDate", "2020-06-14T21:00:00Z"))
		.andExpect(status().is(200))
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.price").exists())
		.andExpect(jsonPath("$.price").value("35.5"));
	}
	@Test
	void givenProductIdAndBrandId_whenRequestingForDate15062020at1000_thenReturn30_50() throws Exception {

		this.mockMvc.perform(get("/prices")
				.param("productId", PRODUCT_ID.toString())
				.param("brandId", BRAND_ID.toString())
				.param("startDate", "2020-06-15T10:00:00Z"))
		.andExpect(status().is(200))
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.price").exists())
		.andExpect(jsonPath("$.price").value("30.5"));
	}
	@Test
	void givenProductIdAndBrandId_whenRequestingForDate16062020at2100_thenReturn35_50() throws Exception {

		this.mockMvc.perform(get("/prices")
				.param("productId", PRODUCT_ID.toString())
				.param("brandId", BRAND_ID.toString())
				.param("startDate", "2020-06-16T21:00:00Z"))
		.andExpect(status().is(200))
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.price").exists())
		.andExpect(jsonPath("$.price").value("38.95"));
	}
}
