package com.Selenium_Java.dto;

public record ManufacturerWithAddressDTO(
        Long manufacturerId,
        String manufacturerName,
        String city,
        Long productCount,
        Double totalProductPrice
) {
}
