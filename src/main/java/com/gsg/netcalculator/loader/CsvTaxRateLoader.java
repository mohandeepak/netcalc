package com.gsg.netcalculator.loader;

import com.gsg.netcalculator.provider.CsvTaxRateProvider;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CsvTaxRateLoader implements TaxRateLoader{

    private final Map<String, BigDecimal> taxRates;
    private final String csvFileName;
    public CsvTaxRateLoader(String csvFileName) {
        this.csvFileName = csvFileName;
        this.taxRates = new HashMap<>();
    }

    @Override
    public Map<String, BigDecimal> loadTaxRates() {
        try (InputStream inputStream = CsvTaxRateProvider.class.getClassLoader().getResourceAsStream(csvFileName)) {
            if (inputStream == null) {
                throw new FileNotFoundException("CSV file not found: " + csvFileName);
            }

            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                 BufferedReader reader = new BufferedReader(inputStreamReader)) {

                reader.readLine();   // Skip the headers

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] lineTokens = line.split(",");
                    if (lineTokens.length == 2) {
                        String countryIso = lineTokens[0].trim();
                        String taxRate = lineTokens[1].trim();

                        if (isValidCountryIso(countryIso) && taxRate != null && isValidTaxRate(taxRate)) {
                            BigDecimal rate = new BigDecimal(taxRate);
                            taxRates.put(countryIso, rate);
                        } else {
                            throw new IllegalArgumentException("Invalid data in line: " + line);
                        }
                    } else {
                        throw new IllegalArgumentException("Invalid CSV format in line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + csvFileName, e);
        }
        return taxRates;
    }

    private boolean isValidCountryIso(String countryIso) {
        return countryIso != null && !countryIso.isEmpty();
    }

    private boolean isValidTaxRate(String taxRate) {
        try {
            BigDecimal rate = new BigDecimal(taxRate);
            return rate.compareTo(BigDecimal.ZERO) >= 0 && rate.compareTo(BigDecimal.ONE) <= 0;
        } catch (NumberFormatException e) {
            return false; // Skip invalid tax rate
        }
    }
}
