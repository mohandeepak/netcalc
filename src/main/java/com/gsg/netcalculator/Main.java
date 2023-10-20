package com.gsg.netcalculator;

import com.gsg.netcalculator.loader.CsvTaxRateLoader;
import com.gsg.netcalculator.loader.TaxRateLoader;
import com.gsg.netcalculator.provider.CsvTaxRateProvider;
import com.gsg.netcalculator.provider.TaxRateProvider;
import com.gsg.netcalculator.service.NetPriceCalculator;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        TaxRateLoader taxRateLoader = new CsvTaxRateLoader("tax_rates.csv");
        TaxRateProvider taxRateProvider = new CsvTaxRateProvider(taxRateLoader);
        NetPriceCalculator calculator = new NetPriceCalculator(taxRateProvider);

        BigDecimal netPriceDE = calculator.calculateNetPrice(BigDecimal.valueOf(100), "DE");
        System.out.println("Net Price in Germany: " + netPriceDE);

        BigDecimal netPriceFR = calculator.calculateNetPrice(BigDecimal.valueOf(1.99), "FR");
        System.out.println("Net Price in France: " + netPriceFR);

        BigDecimal netPriceUS= calculator.calculateNetPrice(BigDecimal.valueOf(1000), "US");
        System.out.println("Net Price in US: " + netPriceUS);
        }
    }