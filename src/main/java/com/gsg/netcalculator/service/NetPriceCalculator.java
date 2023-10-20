package com.gsg.netcalculator.service;

import com.gsg.netcalculator.provider.TaxRateProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NetPriceCalculator {
    private final TaxRateProvider taxRateProvider;

    public NetPriceCalculator(TaxRateProvider taxRateProvider){
        this.taxRateProvider = taxRateProvider;
    }

    public BigDecimal calculateNetPrice(BigDecimal grossPrice, String countryIso) {
        validateCountryIso(countryIso);
        validateGrossPrice(grossPrice);

        BigDecimal taxRate = taxRateProvider.getTaxRate(countryIso);
        return grossPrice.divide(BigDecimal.ONE.add(taxRate), 2, RoundingMode.HALF_UP);
    }

    private void validateCountryIso(String countryIso) {
        if (countryIso == null || countryIso.trim().isEmpty()) {
            throw new IllegalArgumentException("Country code is null or empty.");
        }
        if (!taxRateProvider.countryExists(countryIso)) {
            throw new IllegalArgumentException("Invalid country code: " + countryIso);
        }
    }

    private void validateGrossPrice(BigDecimal grossPrice) {
        if (grossPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Gross price must be greater than or equal to zero");
        }
    }
}
