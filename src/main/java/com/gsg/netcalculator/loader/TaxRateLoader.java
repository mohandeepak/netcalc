package com.gsg.netcalculator.loader;

import java.math.BigDecimal;
import java.util.Map;

public interface TaxRateLoader {
    Map<String, BigDecimal> loadTaxRates();
}
