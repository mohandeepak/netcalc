import com.gsg.netcalculator.loader.CsvTaxRateLoader;
import com.gsg.netcalculator.provider.CsvTaxRateProvider;
import com.gsg.netcalculator.service.NetPriceCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NetPriceCalculatorTest {
    private CsvTaxRateLoader taxRateLoader;
    private CsvTaxRateProvider taxRateProvider;
    private NetPriceCalculator netPriceCalculator;

    @BeforeEach
    public void setUp() {
        taxRateLoader = new CsvTaxRateLoader("tax_rates.csv");
        taxRateProvider = new CsvTaxRateProvider(taxRateLoader);
        netPriceCalculator = new NetPriceCalculator(taxRateProvider);
    }

    @Test
    public void testCalculateNetPriceWithValidTaxRate() {
        BigDecimal grossPrice = new BigDecimal("100.00");
        BigDecimal expectedNetPrice = new BigDecimal("84.03");

        BigDecimal netPriceDE = netPriceCalculator.calculateNetPrice(grossPrice, "DE");
        assertEquals(expectedNetPrice, netPriceDE);
    }

    @Test
    public void testCalculateNetPriceWithValidTaxRateForDifferentCountry() {
        BigDecimal grossPrice = new BigDecimal("100.00");
        BigDecimal expectedNetPrice = new BigDecimal("85.00");

        BigDecimal netPriceForDifferentCountry = netPriceCalculator.calculateNetPrice(grossPrice, "FR");
        assertNotEquals(expectedNetPrice, netPriceForDifferentCountry);
    }

    @Test
    public void testCalculateNetPriceWithInvalidCountry() {
        BigDecimal grossPrice = new BigDecimal("100.00");

        assertThrows(IllegalArgumentException.class, () -> {
            netPriceCalculator.calculateNetPrice(grossPrice, "HJKLO");
        });
    }

    @Test
    public void testCalculateNetPriceWithEmptyCountry() {
        BigDecimal grossPrice = new BigDecimal("100.00");

        assertThrows(IllegalArgumentException.class, () -> {
            netPriceCalculator.calculateNetPrice(grossPrice, "   ");
        });
    }

    @Test
    public void testCalculateNetPriceWithNullCountry() {
        BigDecimal grossPrice = new BigDecimal("100.00");

        assertThrows(IllegalArgumentException.class, () -> {
            netPriceCalculator.calculateNetPrice(grossPrice, null);
        });
    }

    @Test
    public void testCalculateNetPriceWithZeroGrossPrice() {
        BigDecimal grossPrice = new BigDecimal("0.00");
        BigDecimal expectedNetPrice = new BigDecimal("0.00");

        BigDecimal netPriceZeroRate = netPriceCalculator.calculateNetPrice(grossPrice, "DE");
        assertEquals(expectedNetPrice, netPriceZeroRate);
    }

    @Test
    public void testCalculateNetPriceWithNegativeGrossPrice() {
        BigDecimal grossPrice = new BigDecimal("-100.00");

        assertThrows(IllegalArgumentException.class, () -> {
            netPriceCalculator.calculateNetPrice(grossPrice, "DE");
        });
    }

    @Test
    public void testCalculateNetPriceWithLargeGrossPrice() {
        BigDecimal grossPrice = new BigDecimal("1000000.00");
        BigDecimal expectedNetPrice = new BigDecimal("840336.13");

        BigDecimal netPriceMaxGross = netPriceCalculator.calculateNetPrice(grossPrice, "DE");
        assertEquals(expectedNetPrice, netPriceMaxGross);
    }

    @Test
    public void testCalculateNetPriceWithSmallGrossPrice() {
        BigDecimal grossPrice = new BigDecimal("0.01");
        BigDecimal expectedNetPrice = new BigDecimal("0.01");

        BigDecimal netPriceMinGross = netPriceCalculator.calculateNetPrice(grossPrice, "DE");
        assertEquals(expectedNetPrice, netPriceMinGross);
    }

    @Test
    public void testCalculateNetPriceWithZeroTaxRate() {
        BigDecimal grossPrice = new BigDecimal("100.00");
        BigDecimal expectedNetPrice = new BigDecimal("100.00");

        BigDecimal netPriceZeroRate = netPriceCalculator.calculateNetPrice(grossPrice, "US");
        assertEquals(expectedNetPrice, netPriceZeroRate);
    }
}
