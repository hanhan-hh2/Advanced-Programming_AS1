public class MainB {
    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        // ==================== SIMPLE PRICING STRATEGY ====================

        // Test 1: SimplePricingStrategy với Stock
        try {
            Stock s = new Stock("AAPL", "Apple", 100.0, 3_000_000_000_000L, "Tech");
            PricingStrategy strategy = new SimplePricingStrategy();
            double result = strategy.calculateFairValue(s);
            if (result != 105.0) throw new AssertionError("Expected 105.0 but got " + result);
            System.out.println("Test 1 PASSED: SimplePricingStrategy Stock 100 -> 105");
            passed++;
        } catch (Exception e) { System.out.println("Test 1 FAILED: " + e.getMessage()); failed++; }

        // Test 2: SimplePricingStrategy với Bond
        try {
            Bond b = new Bond("US10Y", "US Treasury", 1000.0, 5.0, 10);
            PricingStrategy strategy = new SimplePricingStrategy();
            double result = strategy.calculateFairValue(b);
            if (result != 1050.0) throw new AssertionError("Expected 1050.0 but got " + result);
            System.out.println("Test 2 PASSED: SimplePricingStrategy Bond 1000 -> 1050");
            passed++;
        } catch (Exception e) { System.out.println("Test 2 FAILED: " + e.getMessage()); failed++; }

        // Test 3: SimplePricingStrategy với Option
        try {
            Option o = new Option("OPT1", "AAPL Call", 200.0, 160.0, true, 30);
            PricingStrategy strategy = new SimplePricingStrategy();
            double result = strategy.calculateFairValue(o);
            if (result != 210.0) throw new AssertionError("Expected 210.0 but got " + result);
            System.out.println("Test 3 PASSED: SimplePricingStrategy Option 200 -> 210");
            passed++;
        } catch (Exception e) { System.out.println("Test 3 FAILED: " + e.getMessage()); failed++; }

        // Test 4: SimplePricingStrategy với Future
        try {
            Future f = new Future("GOLD1", "Gold Future", 1800.0, 100.0, 30);
            PricingStrategy strategy = new SimplePricingStrategy();
            double result = strategy.calculateFairValue(f);
            if (result != 1890.0) throw new AssertionError("Expected 1890.0 but got " + result);
            System.out.println("Test 4 PASSED: SimplePricingStrategy Future 1800 -> 1890");
            passed++;
        } catch (Exception e) { System.out.println("Test 4 FAILED: " + e.getMessage()); failed++; }

        // Test 5: SimplePricingStrategy strategyName
        try {
            PricingStrategy strategy = new SimplePricingStrategy();
            if (!strategy.strategyName().equals("SimplePricingStrategy"))
                throw new AssertionError("Got: " + strategy.strategyName());
            System.out.println("Test 5 PASSED: SimplePricingStrategy strategyName");
            passed++;
        } catch (Exception e) { System.out.println("Test 5 FAILED: " + e.getMessage()); failed++; }

        // Test 6: SimplePricingStrategy price = 0
        try {
            Stock s = new Stock("ZERO", "Zero Co", 0.0, 1_000_000_000L, "Tech");
            PricingStrategy strategy = new SimplePricingStrategy();
            double result = strategy.calculateFairValue(s);
            if (result != 0.0) throw new AssertionError("Expected 0.0 but got " + result);
            System.out.println("Test 6 PASSED: SimplePricingStrategy price=0 -> 0");
            passed++;
        } catch (Exception e) { System.out.println("Test 6 FAILED: " + e.getMessage()); failed++; }

        // ==================== RISK ADJUSTED PRICING STRATEGY ====================

        // Test 7: RiskAdjustedPricingStrategy với Bond riskScore=2.0
        try {
            Bond b = new Bond("US10Y", "US Treasury", 1000.0, 5.0, 10); // <=10 thì là 2
            PricingStrategy strategy = new RiskAdjustedPricingStrategy();
            double result = strategy.calculateFairValue(b);
            double expected = 1000.0 * (1 + 0.01 * 2.0); // 1020.0
            if (result != expected) throw new AssertionError("Expected " + expected + " but got " + result);
            System.out.println("Test 7 PASSED: RiskAdjusted Bond risk=2.0 -> 1020.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 7 FAILED: " + e.getMessage()); failed++; }

        // Test 8: RiskAdjustedPricingStrategy với Bond riskScore=4.0
        try {
            Bond b = new Bond("US30Y", "US 30Y", 1000.0, 5.0, 30);
            PricingStrategy strategy = new RiskAdjustedPricingStrategy();
            double result = strategy.calculateFairValue(b);
            double expected = 1000.0 * (1 + 0.01 * 4.0); // 1040.0
            if (result != expected) throw new AssertionError("Expected " + expected + " but got " + result);
            System.out.println("Test 8 PASSED: RiskAdjusted Bond risk=4.0 -> 1040.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 8 FAILED: " + e.getMessage()); failed++; }

        // Test 9: RiskAdjustedPricingStrategy với Stock riskScore=3.0
        try {
            Stock s = new Stock("AAPL", "Apple", 100.0, 3_000_000_000_000L, "Tech");
            PricingStrategy strategy = new RiskAdjustedPricingStrategy();
            double result = strategy.calculateFairValue(s);
            double expected = 100.0 * (1 + 0.01 * 3.0); // 103.0
            if (result != expected) throw new AssertionError("Expected " + expected + " but got " + result);
            System.out.println("Test 9 PASSED: RiskAdjusted Stock risk=3.0 -> 103.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 9 FAILED: " + e.getMessage()); failed++; }

        // Test 10: RiskAdjustedPricingStrategy với Option riskScore=8.5
        try {
            Option o = new Option("OPT1", "AAPL Call", 200.0, 160.0, true, 30);
            PricingStrategy strategy = new RiskAdjustedPricingStrategy();
            double result = strategy.calculateFairValue(o);
            double expected = 200.0 * (1 + 0.01 * 8.5); // 217.0
            if (result != expected) throw new AssertionError("Expected " + expected + " but got " + result);
            System.out.println("Test 10 PASSED: RiskAdjusted Option risk=8.5 -> 217.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 10 FAILED: " + e.getMessage()); failed++; }

        // Test 11: RiskAdjustedPricingStrategy strategyName
        try {
            PricingStrategy strategy = new RiskAdjustedPricingStrategy();
            if (!strategy.strategyName().equals("RiskAdjustedPricingStrategy"))
                throw new AssertionError("Got: " + strategy.strategyName());
            System.out.println("Test 11 PASSED: RiskAdjustedPricingStrategy strategyName");
            passed++;
        } catch (Exception e) { System.out.println("Test 11 FAILED: " + e.getMessage()); failed++; }

        // Test 12: RiskAdjustedPricingStrategy với Future riskScore=8.5
        try {
            Future f = new Future("GOLD1", "Gold Future", 1000.0, 100.0, 30);
            PricingStrategy strategy = new RiskAdjustedPricingStrategy();
            double result = strategy.calculateFairValue(f);
            double expected = 1000.0 * (1 + 0.01 * 8.5); // 1085.0
            if (result != expected) throw new AssertionError("Expected " + expected + " but got " + result);
            System.out.println("Test 12 PASSED: RiskAdjusted Future risk=8.5 -> 1085.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 12 FAILED: " + e.getMessage()); failed++; }

        // ==================== TRADEABLE ====================

        // Test 13: isAvailableForTrading luôn true
        try {
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            if (!s.isAvailableForTrading())
                throw new AssertionError("Expected true");
            System.out.println("Test 13 PASSED: isAvailableForTrading = true");
            passed++;
        } catch (Exception e) { System.out.println("Test 13 FAILED: " + e.getMessage()); failed++; }

        // Test 14: getSymbol trả đúng symbol
        try {
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            if (!s.getSymbol().equals("AAPL"))
                throw new AssertionError("Got: " + s.getSymbol());
            System.out.println("Test 14 PASSED: getSymbol = AAPL");
            passed++;
        } catch (Exception e) { System.out.println("Test 14 FAILED: " + e.getMessage()); failed++; }

        // Test 15: getCurrentPriceValue trả đúng giá
        try {
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            if (s.getCurrentPriceValue() != 150.0)
                throw new AssertionError("Got: " + s.getCurrentPriceValue());
            System.out.println("Test 15 PASSED: getCurrentPriceValue = 150.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 15 FAILED: " + e.getMessage()); failed++; }

        // Test 16: getTradingInfo format đúng - Available
        try {
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            String info = s.getTradingInfo();
            if (!info.contains("AAPL") || !info.contains("150") || !info.contains("Available"))
                throw new AssertionError("Got: " + info);
            System.out.println("Test 16 PASSED: getTradingInfo contains AAPL, 150, Available");
            passed++;
        } catch (Exception e) { System.out.println("Test 16 FAILED: " + e.getMessage()); failed++; }

        // Test 17: getTradingInfo với Bond
        try {
            Bond b = new Bond("US10Y", "US Treasury", 1000.0, 5.0, 10);
            String info = b.getTradingInfo();
            if (!info.contains("US10Y") || !info.contains("1000"))
                throw new AssertionError("Got: " + info);
            System.out.println("Test 17 PASSED: getTradingInfo Bond contains US10Y, 1000");
            passed++;
        } catch (Exception e) { System.out.println("Test 17 FAILED: " + e.getMessage()); failed++; }

        // ==================== PRICEABLE ====================

        // Test 18: getPriceChange tăng giá
        try {
            Stock s = new Stock("AAPL", "Apple", 105.0, 3_000_000_000_000L, "Tech");
            double change = s.getPriceChange(100.0);
            if (change != 5.0) throw new AssertionError("Expected 5.0 but got " + change);
            System.out.println("Test 18 PASSED: getPriceChange 105-100 = 5.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 18 FAILED: " + e.getMessage()); failed++; }

        // Test 19: getPriceChange giảm giá -> âm
        try {
            Stock s = new Stock("AAPL", "Apple", 90.0, 3_000_000_000_000L, "Tech");
            double change = s.getPriceChange(100.0);
            if (change != -10.0) throw new AssertionError("Expected -10.0 but got " + change);
            System.out.println("Test 19 PASSED: getPriceChange 90-100 = -10.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 19 FAILED: " + e.getMessage()); failed++; }

        // Test 20: getPriceChange không đổi -> 0
        try {
            Stock s = new Stock("AAPL", "Apple", 100.0, 3_000_000_000_000L, "Tech");
            double change = s.getPriceChange(100.0);
            if (change != 0.0) throw new AssertionError("Expected 0.0 but got " + change);
            System.out.println("Test 20 PASSED: getPriceChange same price = 0.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 20 FAILED: " + e.getMessage()); failed++; }

        // Test 21: getPriceChangePercent tăng 5%
        try {
            Stock s = new Stock("AAPL", "Apple", 105.0, 3_000_000_000_000L, "Tech");
            double pct = s.getPriceChangePercent(100.0);
            if (pct != 5.0) throw new AssertionError("Expected 5.0 but got " + pct);
            System.out.println("Test 21 PASSED: getPriceChangePercent = 5.0%");
            passed++;
        } catch (Exception e) { System.out.println("Test 21 FAILED: " + e.getMessage()); failed++; }

        // Test 22: getPriceChangePercent giảm 10%
        try {
            Stock s = new Stock("AAPL", "Apple", 90.0, 3_000_000_000_000L, "Tech");
            double pct = s.getPriceChangePercent(100.0);
            if (pct != -10.0) throw new AssertionError("Expected -10.0 but got " + pct);
            System.out.println("Test 22 PASSED: getPriceChangePercent = -10.0%");
            passed++;
        } catch (Exception e) { System.out.println("Test 22 FAILED: " + e.getMessage()); failed++; }

        // Test 23: getPriceChangePercent với Bond
        try {
            Bond b = new Bond("US10Y", "US Treasury", 1040.0, 5.0, 10);
            double pct = b.getPriceChangePercent(1000.0);
            if (pct != 4.0) throw new AssertionError("Expected 4.0 but got " + pct);
            System.out.println("Test 23 PASSED: getPriceChangePercent Bond = 4.0%");
            passed++;
        } catch (Exception e) { System.out.println("Test 23 FAILED: " + e.getMessage()); failed++; }

        // Test 24: getCurrentPriceValue sau updatePrice
        try {
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            s.updatePrice(200.0);
            if (s.getCurrentPriceValue() != 200.0)
                throw new AssertionError("Expected 200.0 but got " + s.getCurrentPriceValue());
            System.out.println("Test 24 PASSED: getCurrentPriceValue after updatePrice");
            passed++;
        } catch (Exception e) { System.out.println("Test 24 FAILED: " + e.getMessage()); failed++; }

        // Test 25: Gọi liên tiếp nhiều lần updatePrice rồi kiểm tra Tradeable
        try {
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            s.updatePrice(160.0);
            s.updatePrice(170.0);
            s.updatePrice(180.0);
            if (!(s instanceof Tradeable))
                throw new AssertionError("Stock should implement Tradeable");
            if (s.getCurrentPriceValue() != 180.0)
                throw new AssertionError("Expected 180.0 but got " + s.getCurrentPriceValue());
            if (!s.getSymbol().equals("AAPL"))
                throw new AssertionError("Symbol changed after updatePrice");
            System.out.println("Test 25 PASSED: Stock instanceof Tradeable updatePrice");
            passed++;
        } catch (Throwable e) { System.out.println("Test 25 FAILED: " + e.getMessage()); failed++; }

        // Test 26: Gọi getPriceChange và getPriceChangePercent nhiều lần liên tiếp
        // Chưa update rồi getPriceChange
        try {
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            if (!(s instanceof Priceable))
                throw new AssertionError("Stock should implement Priceable");
            double change1 = s.getPriceChange(100.0);   // 50.0
            double change2 = s.getPriceChange(150.0);   // 0.0
            double change3 = s.getPriceChange(200.0);   // -50.0
            if (change1 != 50.0) throw new AssertionError("Expected 50.0 but got " + change1);
            if (change2 != 0.0)  throw new AssertionError("Expected 0.0 but got " + change2);
            if (change3 != -50.0) throw new AssertionError("Expected -50.0 but got " + change3);
            System.out.println("Test 26 PASSED: Priceable getPriceChange " + change1 + ", " + change2 + ", " + change3);
            passed++;
        } catch (Throwable e) { System.out.println("Test 26 FAILED: " + e.getMessage()); failed++; }

        // Test 27: Bond gọi nhiều hàm liên tiếp - riskScore, annualCoupon, getPriceChange, getPriceChangePercent
        try {
            Bond b = new Bond("US10Y", "US Treasury", 1000.0, 5.0, 10);
            if (!(b instanceof Tradeable) || !(b instanceof Priceable))
                throw new AssertionError("Bond should implement both");
            double coupon1 = b.annualCouponPayment(1);   // 50.0
            double coupon5 = b.annualCouponPayment(5);   // 250.0
            double change  = b.getPriceChange(900.0);    // 100.0
            double pct     = b.getPriceChangePercent(900.0); // 11.11...
            if (coupon1 != 50.0)  throw new AssertionError("Expected coupon1=50.0 but got " + coupon1);
            if (coupon5 != 250.0) throw new AssertionError("Expected coupon5=250.0 but got " + coupon5);
            if (change != 100.0)  throw new AssertionError("Expected change=100.0 but got " + change);
            if (Math.abs(pct - 11.111111111111112) > 0.0001)
                throw new AssertionError("Expected pct~11.11 but got " + pct);
            System.out.println("Test 27 PASSED: Bond coupon=" + coupon1 + ", change=" + change + ", pct=" + String.format("%.2f", pct) + "%");
            passed++;
        } catch (Throwable e) { System.out.println("Test 27 FAILED: " + e.getMessage()); failed++; }

        // Test 28: Tradeable reference - gọi nhiều hàm liên tiếp
        try {
            Tradeable t = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            String symbol   = t.getSymbol();             // "AAPL"
            double price    = t.getCurrentPriceValue();  // 150.0
            boolean trading = t.isAvailableForTrading(); // true
            String info     = t.getTradingInfo();
            if (!symbol.equals("AAPL"))   throw new AssertionError("Symbol: " + symbol);
            if (price != 150.0)           throw new AssertionError("Price: " + price);
            if (!trading)                 throw new AssertionError("Should be available");
            if (!info.contains("AAPL") || !info.contains("150") || !info.contains("Available"))
                throw new AssertionError("TradingInfo: " + info);
            System.out.println("Test 28 PASSED: Tradeable reference gọi 4 hàm: symbol=" + symbol + ", price=" + price + ", trading=" + trading);
            passed++;
        } catch (Throwable e) { System.out.println("Test 28 FAILED: " + e.getMessage()); failed++; }

        // Test 29: Priceable reference - updatePrice rồi gọi lại các hàm
        try {
            Stock raw = new Stock("AAPL", "Apple", 100.0, 3_000_000_000_000L, "Tech");
            Priceable p = raw;
            double change1 = p.getPriceChange(80.0);        // 20.0
            double pct1    = p.getPriceChangePercent(80.0); // 25.0%
            raw.updatePrice(120.0);
            double change2 = p.getPriceChange(80.0);        // 40.0 (giá đã update)
            double pct2    = p.getPriceChangePercent(80.0); // 50.0%
            if (change1 != 20.0)  throw new AssertionError("Expected change1=20.0 but got " + change1);
            if (pct1 != 25.0)     throw new AssertionError("Expected pct1=25.0 but got " + pct1);
            if (change2 != 40.0)  throw new AssertionError("Expected change2=40.0 but got " + change2);
            if (pct2 != 50.0)     throw new AssertionError("Expected pct2=50.0 but got " + pct2);
            System.out.println("Test 29 PASSED: Priceable sau updatePrice: change " + change1 + "->" + change2 + ", pct " + pct1 + "%->" + pct2 + "%");
            passed++;
        } catch (Throwable e) { System.out.println("Test 29 FAILED: " + e.getMessage()); failed++; }

        // Test 30: Áp dụng cả 2 strategy liên tiếp lên cùng 1 instrument sau updatePrice
        try {
            Stock s = new Stock("AAPL", "Apple", 100.0, 3_000_000_000_000L, "Tech");
            PricingStrategy simple = new SimplePricingStrategy();
            PricingStrategy risk   = new RiskAdjustedPricingStrategy();

            double simple1 = simple.calculateFairValue(s); // 105.0
            double risk1   = risk.calculateFairValue(s);   // 103.0
            if (simple1 == risk1) throw new AssertionError("Round1: strategies should differ");

            s.updatePrice(200.0);
            double simple2 = simple.calculateFairValue(s); // 210.0
            double risk2   = risk.calculateFairValue(s);   // 206.0
            if (simple2 == risk2) throw new AssertionError("Round2: strategies should differ");
            if (simple2 <= simple1) throw new AssertionError("Simple should increase after updatePrice");
            if (risk2 <= risk1)     throw new AssertionError("Risk should increase after updatePrice");

            System.out.println("Test 30 PASSED: Simple " + simple1 + "->" + simple2 + ", RiskAdjusted " + risk1 + "->" + risk2);
            passed++;
        } catch (Throwable e) { System.out.println("Test 30 FAILED: " + e.getMessage()); failed++; }        

        // ==================== SUMMARY ====================
        System.out.println("\n===== RESULTS: " + passed + " passed, " + failed + " failed =====");
    }
}