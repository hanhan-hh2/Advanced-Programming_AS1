
public class MainA {
    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        // ==================== STOCK TESTS ====================

        // Test 1: Stock cơ bản - marketCap lớn -> riskScore 3.0
        try {
            Stock apple = new Stock("AAPL", "Apple Inc", 150.0, 3_000_000_000_000L, "Technology");
            assert apple.riskScore() == 3.0 : "FAIL";
            assert apple.assetClass().equals("EQUITY") : "FAIL";
            System.out.println("Test 1 PASSED: Stock large cap riskScore=3.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 1 FAILED: " + e.getMessage()); failed++; }

        // Test 2: Stock marketCap trung bình -> riskScore 5.0
        try {
            Stock mid = new Stock("MID", "MidCap Co", 50.0, 5_000_000_000L, "Finance");
            assert mid.riskScore() == 5.0 : "FAIL";
            System.out.println("Test 2 PASSED: Stock mid cap riskScore=5.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 2 FAILED: " + e.getMessage()); failed++; }

        // Test 3: Stock marketCap nhỏ -> riskScore 7.5
        try {
            Stock startup = new Stock("SML", "Startup Inc", 10.0, 500_000_000L, "Tech");
            assert startup.riskScore() == 7.5 : "FAIL";
            System.out.println("Test 3 PASSED: Stock small cap riskScore=7.5");
            passed++;
        } catch (Exception e) { System.out.println("Test 3 FAILED: " + e.getMessage()); failed++; }

        // Test 4: Stock toString format
        try {
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Technology");
            String str = s.toString();
            assert str.equals("Stock[symbol=AAPL, price=150.00, risk=3.00]") : "FAIL: " + str;
            System.out.println("Test 4 PASSED: Stock toString format");
            passed++;
        } catch (Exception e) { System.out.println("Test 4 FAILED: " + e.getMessage()); failed++; }

        // Test 5: Stock updatePrice hợp lệ
        try {
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Technology");
            s.updatePrice(160.0);
            assert s.getCurrentPriceValue() == 160.0 : "FAIL";
            System.out.println("Test 5 PASSED: Stock updatePrice valid");
            passed++;
        } catch (Exception e) { System.out.println("Test 5 FAILED: " + e.getMessage()); failed++; }

        // Test 6: Stock updatePrice âm -> exception
        try {
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Technology");
            s.updatePrice(-10.0);
            System.out.println("Test 6 FAILED: Expected IllegalArgumentException");
            failed++;
        } catch (IllegalArgumentException e) {
            System.out.println("Test 6 PASSED: updatePrice negative throws exception");
            passed++;
        }

        // Test 7: Stock constructor price âm -> exception
        try {
            Stock s = new Stock("AAPL", "Apple", -1.0, 1_000_000_000L, "Tech");
            System.out.println("Test 7 FAILED: Expected IllegalArgumentException");
            failed++;
        } catch (IllegalArgumentException e) {
            System.out.println("Test 7 PASSED: Constructor negative price throws exception");
            passed++;
        }

        // ==================== BOND TESTS ====================

        // Test 8: Bond maturityYears > 10 -> riskScore 4.0
        try {
            Bond b = new Bond("US30Y", "US 30Y Treasury", 1000.0, 5.0, 30);
            assert b.riskScore() == 4.0 : "FAIL";
            assert b.assetClass().equals("FIXED_INCOME") : "FAIL";
            System.out.println("Test 8 PASSED: Bond long maturity riskScore=4.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 8 FAILED: " + e.getMessage()); failed++; }

        // Test 9: Bond maturityYears <= 10 -> riskScore 2.0
        try {
            Bond b = new Bond("US10Y", "US 10Y Treasury", 1000.0, 5.0, 10);
            assert b.riskScore() == 2.0 : "FAIL";
            System.out.println("Test 9 PASSED: Bond short maturity riskScore=2.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 9 FAILED: " + e.getMessage()); failed++; }

        // Test 10: Bond annualCouponPayment 1 unit
        try {
            Bond b = new Bond("US10Y", "US 10Y Treasury", 1000.0, 5.0, 10);
            assert b.annualCouponPayment(1) == 50.0 : "FAIL: " + b.annualCouponPayment(1);
            System.out.println("Test 10 PASSED: Bond annualCouponPayment 1 unit = 50.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 10 FAILED: " + e.getMessage()); failed++; }

        // Test 11: Bond annualCouponPayment nhiều units
        try {
            Bond b = new Bond("US10Y", "US 10Y Treasury", 1000.0, 5.0, 10);
            assert b.annualCouponPayment(3) == 150.0 : "FAIL: " + b.annualCouponPayment(3);
            System.out.println("Test 11 PASSED: Bond annualCouponPayment 3 units = 150.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 11 FAILED: " + e.getMessage()); failed++; }

        // ==================== OPTION TESTS ====================

        // Test 12: Option riskScore = 8.5
        try {
            Option o = new Option("OPT1", "AAPL Call", 5.0, 160.0, true, 30);
            assert o.riskScore() == 8.5 : "FAIL";
            assert o.assetClass().equals("DERIVATIVE") : "FAIL";
            System.out.println("Test 12 PASSED: Option riskScore=8.5, assetClass=DERIVATIVE");
            passed++;
        } catch (Exception e) { System.out.println("Test 12 FAILED: " + e.getMessage()); failed++; }

        // Test 13: Call option isInTheMoney = true
        try {
            Option call = new Option("OPT1", "AAPL Call", 5.0, 160.0, true, 30);
            assert call.isInTheMoney(170.0) == true : "FAIL";
            System.out.println("Test 13 PASSED: Call option in the money");
            passed++;
        } catch (Exception e) { System.out.println("Test 13 FAILED: " + e.getMessage()); failed++; }

        // Test 14: Call option isInTheMoney = false
        try {
            Option call = new Option("OPT1", "AAPL Call", 5.0, 160.0, true, 30);
            assert call.isInTheMoney(150.0) == false : "FAIL";
            System.out.println("Test 14 PASSED: Call option out of the money");
            passed++;
        } catch (Exception e) { System.out.println("Test 14 FAILED: " + e.getMessage()); failed++; }

        // Test 15: Put option isInTheMoney = true
        try {
            Option put = new Option("OPT2", "AAPL Put", 5.0, 160.0, false, 30);
            assert put.isInTheMoney(150.0) == true : "FAIL";
            System.out.println("Test 15 PASSED: Put option in the money");
            passed++;
        } catch (Exception e) { System.out.println("Test 15 FAILED: " + e.getMessage()); failed++; }

        // Test 16: Put option isInTheMoney = false
        try {
            Option put = new Option("OPT2", "AAPL Put", 5.0, 160.0, false, 30);
            assert put.isInTheMoney(170.0) == false : "FAIL";
            System.out.println("Test 16 PASSED: Put option out of the money");
            passed++;
        } catch (Exception e) { System.out.println("Test 16 FAILED: " + e.getMessage()); failed++; }

        // ==================== FUTURE TESTS ====================

        // Test 17: Future riskScore = 8.5
        try {
            Future f = new Future("GOLD1", "Gold Future", 1800.0, 100.0, 30);
            assert f.riskScore() == 8.5 : "FAIL";
            assert f.assetClass().equals("DERIVATIVE") : "FAIL";
            System.out.println("Test 17 PASSED: Future riskScore=8.5, assetClass=DERIVATIVE");
            passed++;
        } catch (Exception e) { System.out.println("Test 17 FAILED: " + e.getMessage()); failed++; }

        // ==================== EDGE CASES ====================

        // Test 18: marketCap đúng boundary 1_000_000_000 -> riskScore 5.0
        try {
            Stock s = new Stock("EDGE1", "Edge Co", 10.0, 1_000_000_000L, "Tech");
            assert s.riskScore() == 5.0 : "FAIL: " + s.riskScore();
            System.out.println("Test 18 PASSED: Stock boundary marketCap=1B riskScore=5.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 18 FAILED: " + e.getMessage()); failed++; }

        // Test 19: maturityYears đúng boundary = 10 -> riskScore 2.0
        try {
            Bond b = new Bond("EDGE2", "Edge Bond", 1000.0, 5.0, 10);
            assert b.riskScore() == 2.0 : "FAIL: " + b.riskScore();
            System.out.println("Test 19 PASSED: Bond boundary maturityYears=10 riskScore=2.0");
            passed++;
        } catch (Exception e) { System.out.println("Test 19 FAILED: " + e.getMessage()); failed++; }

        // Test 20: Call option spotPrice == strikePrice -> không in the money
        try {
            Option call = new Option("OPT3", "Edge Call", 5.0, 160.0, true, 30);
            assert call.isInTheMoney(160.0) == false : "FAIL";
            System.out.println("Test 20 PASSED: Call option spotPrice==strikePrice -> not in the money");
            passed++;
        } catch (Exception e) { System.out.println("Test 20 FAILED: " + e.getMessage()); failed++; }

        // ==================== SUMMARY ====================
        System.out.println("\n===== RESULTS: " + passed + " passed, " + failed + " failed =====");
    }
}
