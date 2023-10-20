package com.gsg.netcalculator.provider;

import java.math.BigDecimal;

public interface TaxRateProvider {
    BigDecimal getTaxRate(String countryIso);
    boolean countryExists(String countryIso);
}
