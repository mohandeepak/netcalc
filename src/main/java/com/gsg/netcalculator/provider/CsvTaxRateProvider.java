package com.gsg.netcalculator.provider;

import com.gsg.netcalculator.loader.TaxRateLoader;

import java.math.BigDecimal;
import java.util.Map;

public class CsvTaxRateProvider implements TaxRateProvider {

    private final Map<String, BigDecimal> taxRates;

    public CsvTaxRateProvider(TaxRateLoader taxRateLoader) {
        this.taxRates = taxRateLoader.loadTaxRates();
    }

    @Override
    public BigDecimal getTaxRate(String countryIso) {
        return taxRates.getOrDefault(countryIso, BigDecimal.ZERO);
    }

    @Override
    public boolean countryExists(String countryCode) {
        return taxRates.containsKey(countryCode);
    }
}
