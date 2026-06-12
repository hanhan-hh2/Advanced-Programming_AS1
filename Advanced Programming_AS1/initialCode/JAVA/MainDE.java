package JAVA;
import java.util.List;

import Full.Instrument;
import Full.Stock;

public class MainDE {
    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        // ==================== PART D: RISK ANALYZER ====================

        // Test 1: averageRisk cơ bản 2 stocks
        try {
            RiskAnalyzer<Stock> analyzer = new RiskAnalyzer<>();
            analyzer.add(new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech")); // risk=3.0
            analyzer.add(new Stock("SML",  "Startup", 10.0, 500_000_000L, "Tech"));      // risk=7.5
            double avg = analyzer.averageRisk(); // (3.0 + 7.5) / 2 = 5.25
            if (avg != 5.25)
                throw new AssertionError("Expected 5.25 but got " + avg);
            System.out.println("Test 1 PASSED: averageRisk = 5.25");
            passed++;
        } catch (Throwable e) { System.out.println("Test 1 FAILED: " + e.getMessage()); failed++; }

        // Test 2: highestRisk cơ bản
        try {
            RiskAnalyzer<Stock> analyzer = new RiskAnalyzer<>();
            Stock large   = new Stock("AAPL", "Apple",   150.0, 3_000_000_000_000L, "Tech"); // 3.0
            Stock startup = new Stock("SML",  "Startup",  10.0, 500_000_000L, "Tech");       // 7.5
            analyzer.add(large);
            analyzer.add(startup);
            Stock highest = analyzer.highestRisk();
            if (!highest.getSymbol().equals("SML"))
                throw new AssertionError("Expected SML but got " + highest.getSymbol());
            System.out.println("Test 2 PASSED: highestRisk = SML (7.5)");
            passed++;
        } catch (Throwable e) { System.out.println("Test 2 FAILED: " + e.getMessage()); failed++; }

        // Test 3: lowestRisk cơ bản
        try {
            RiskAnalyzer<Stock> analyzer = new RiskAnalyzer<>();
            Stock large   = new Stock("AAPL", "Apple",   150.0, 3_000_000_000_000L, "Tech"); // 3.0
            Stock startup = new Stock("SML",  "Startup",  10.0, 500_000_000L, "Tech");       // 7.5
            analyzer.add(large);
            analyzer.add(startup);
            Stock lowest = analyzer.lowestRisk();
            if (!lowest.getSymbol().equals("AAPL"))
                throw new AssertionError("Expected AAPL but got " + lowest.getSymbol());
            System.out.println("Test 3 PASSED: lowestRisk = AAPL (3.0)");
            passed++;
        } catch (Throwable e) { System.out.println("Test 3 FAILED: " + e.getMessage()); failed++; }

        // Test 4: getAboveRiskThreshold lọc đúng
        try {
            RiskAnalyzer<Stock> analyzer = new RiskAnalyzer<>();
            analyzer.add(new Stock("AAPL", "Apple",   150.0, 3_000_000_000_000L, "Tech")); // 3.0
            analyzer.add(new Stock("MID",  "MidCap",   50.0, 5_000_000_000L, "Tech"));    // 5.0
            analyzer.add(new Stock("SML",  "Startup",  10.0, 500_000_000L, "Tech"));      // 7.5
            List<Stock> above = analyzer.getAboveRiskThreshold(5.0);
            if (above.size() != 1)
                throw new AssertionError("Expected 1 but got " + above.size());
            if (!above.get(0).getSymbol().equals("SML"))
                throw new AssertionError("Expected SML but got " + above.get(0).getSymbol());
            System.out.println("Test 4 PASSED: getAboveRiskThreshold(5.0) = [SML]");
            passed++;
        } catch (Throwable e) { System.out.println("Test 4 FAILED: " + e.getMessage()); failed++; }

        // Test 5: getAboveRiskThreshold không có phần tử nào -> list rỗng
        try {
            RiskAnalyzer<Stock> analyzer = new RiskAnalyzer<>();
            analyzer.add(new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech")); // 3.0
            List<Stock> above = analyzer.getAboveRiskThreshold(9.0);
            if (!above.isEmpty())
                throw new AssertionError("Expected empty list but got " + above.size());
            System.out.println("Test 5 PASSED: getAboveRiskThreshold(9.0) = []");
            passed++;
        } catch (Throwable e) { System.out.println("Test 5 FAILED: " + e.getMessage()); failed++; }

        // Test 6: getAboveRiskThreshold threshold = riskScore chính xác -> không lấy (strict >)
        try {
            RiskAnalyzer<Stock> analyzer = new RiskAnalyzer<>();
            analyzer.add(new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech")); // 3.0
            List<Stock> above = analyzer.getAboveRiskThreshold(3.0); // strict > không lấy 3.0
            if (!above.isEmpty())
                throw new AssertionError("Expected empty but got " + above.size());
            System.out.println("Test 6 PASSED: getAboveRiskThreshold strict > không lấy boundary");
            passed++;
        } catch (Throwable e) { System.out.println("Test 6 FAILED: " + e.getMessage()); failed++; }

        // Test 7: averageRisk 1 phần tử
        try {
            RiskAnalyzer<Bond> analyzer = new RiskAnalyzer<>();
            analyzer.add(new Bond("US10Y", "US Treasury", 1000.0, 5.0, 10)); // risk=2.0
            double avg = analyzer.averageRisk();
            if (avg != 2.0)
                throw new AssertionError("Expected 2.0 but got " + avg);
            System.out.println("Test 7 PASSED: averageRisk 1 bond = 2.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 7 FAILED: " + e.getMessage()); failed++; }

        // Test 8: averageRisk list rỗng -> exception
        try {
            RiskAnalyzer<Stock> analyzer = new RiskAnalyzer<>();
            analyzer.averageRisk();
            System.out.println("Test 8 FAILED: Expected exception");
            failed++;
        } catch (Exception e) {
            System.out.println("Test 8 PASSED: averageRisk empty throws exception");
            passed++;
        } catch (Throwable e) { System.out.println("Test 8 FAILED: " + e.getMessage()); failed++; }

        // Test 9: highestRisk list rỗng -> exception
        try {
            RiskAnalyzer<Stock> analyzer = new RiskAnalyzer<>();
            analyzer.highestRisk();
            System.out.println("Test 9 FAILED: Expected exception");
            failed++;
        } catch (Exception e) {
            System.out.println("Test 9 PASSED: highestRisk empty throws exception");
            passed++;
        } catch (Throwable e) { System.out.println("Test 9 FAILED: " + e.getMessage()); failed++; }

        // Test 10: lowestRisk list rỗng -> exception
        try {
            RiskAnalyzer<Stock> analyzer = new RiskAnalyzer<>();
            analyzer.lowestRisk();
            System.out.println("Test 10 FAILED: Expected exception");
            failed++;
        } catch (Exception e) {
            System.out.println("Test 10 PASSED: lowestRisk empty throws exception");
            passed++;
        } catch (Throwable e) { System.out.println("Test 10 FAILED: " + e.getMessage()); failed++; }

        // Test 11: RiskAnalyzer với Bond
        try {
            RiskAnalyzer<Bond> analyzer = new RiskAnalyzer<>();
            analyzer.add(new Bond("B1", "Short Bond", 1000.0, 5.0, 10)); // risk=2.0
            analyzer.add(new Bond("B2", "Long Bond",  1000.0, 5.0, 30)); // risk=4.0
            if (analyzer.averageRisk() != 3.0)
                throw new AssertionError("Expected avg=3.0 but got " + analyzer.averageRisk());
            if (!analyzer.highestRisk().getSymbol().equals("B2"))
                throw new AssertionError("Expected B2 highest");
            if (!analyzer.lowestRisk().getSymbol().equals("B1"))
                throw new AssertionError("Expected B1 lowest");
            System.out.println("Test 11 PASSED: RiskAnalyzer<Bond> avg=3.0, highest=B2, lowest=B1");
            passed++;
        } catch (Throwable e) { System.out.println("Test 11 FAILED: " + e.getMessage()); failed++; }

        // Test 12: RiskAnalyzer với Option
        try {
            RiskAnalyzer<Option> analyzer = new RiskAnalyzer<>();
            analyzer.add(new Option("OPT1", "Call1", 5.0, 160.0, true,  30)); // risk=8.5
            analyzer.add(new Option("OPT2", "Call2", 5.0, 170.0, false, 60)); // risk=8.5
            double avg = analyzer.averageRisk();
            if (avg != 8.5)
                throw new AssertionError("Expected 8.5 but got " + avg);
            System.out.println("Test 12 PASSED: RiskAnalyzer<Option> avg=8.5");
            passed++;
        } catch (Throwable e) { System.out.println("Test 12 FAILED: " + e.getMessage()); failed++; }

        // Test 13: add nhiều phần tử rồi tính toán liên tiếp
        try {
            RiskAnalyzer<Stock> analyzer = new RiskAnalyzer<>();
            analyzer.add(new Stock("S1", "Large",  100.0, 3_000_000_000_000L, "Tech")); // 3.0
            analyzer.add(new Stock("S2", "Mid",    100.0, 5_000_000_000L,     "Tech")); // 5.0
            analyzer.add(new Stock("S3", "Small",  100.0, 500_000_000L,       "Tech")); // 7.5

            double avg     = analyzer.averageRisk();       // (3+5+7.5)/3 = 5.166...
            String highest = analyzer.highestRisk().getSymbol(); // S3
            String lowest  = analyzer.lowestRisk().getSymbol();  // S1
            List<Stock> above4 = analyzer.getAboveRiskThreshold(4.0); // S2, S3
            List<Stock> above6 = analyzer.getAboveRiskThreshold(6.0); // S3

            if (Math.abs(avg - 5.1666666) > 0.0001)
                throw new AssertionError("Expected avg~5.167 but got " + avg);
            if (!highest.equals("S3")) throw new AssertionError("Expected highest=S3");
            if (!lowest.equals("S1"))  throw new AssertionError("Expected lowest=S1");
            if (above4.size() != 2)    throw new AssertionError("Expected above4 size=2 but got " + above4.size());
            if (above6.size() != 1)    throw new AssertionError("Expected above6 size=1 but got " + above6.size());

            System.out.println("Test 13 PASSED: avg=" + String.format("%.2f", avg) +
                ", highest=" + highest + ", above4=" + above4.size() + ", above6=" + above6.size());
            passed++;
        } catch (Throwable e) { System.out.println("Test 13 FAILED: " + e.getMessage()); failed++; }

        // Test 14: getAboveRiskThreshold tất cả đều trên threshold
        try {
            RiskAnalyzer<Option> analyzer = new RiskAnalyzer<>();
            analyzer.add(new Option("O1", "Opt1", 5.0, 100.0, true,  30)); // 8.5
            analyzer.add(new Option("O2", "Opt2", 5.0, 110.0, false, 60)); // 8.5
            analyzer.add(new Option("O3", "Opt3", 5.0, 120.0, true,  90)); // 8.5
            List<Option> above = analyzer.getAboveRiskThreshold(5.0);
            if (above.size() != 3)
                throw new AssertionError("Expected 3 but got " + above.size());
            System.out.println("Test 14 PASSED: getAboveRiskThreshold tất cả 3 options");
            passed++;
        } catch (Throwable e) { System.out.println("Test 14 FAILED: " + e.getMessage()); failed++; }

        // Test 15: highestRisk và lowestRisk khi tất cả cùng riskScore
        try {
            RiskAnalyzer<Option> analyzer = new RiskAnalyzer<>();
            Option o1 = new Option("O1", "Opt1", 5.0, 100.0, true,  30); // 8.5
            Option o2 = new Option("O2", "Opt2", 5.0, 110.0, false, 60); // 8.5
            analyzer.add(o1);
            analyzer.add(o2);
            // Khi bằng nhau, phải trả về 1 trong 2, không được exception
            Stock highest = null;
            try { analyzer.highestRisk(); } catch (Exception ex) {
                throw new AssertionError("highestRisk should not throw when all equal");
            }
            try { analyzer.lowestRisk(); } catch (Exception ex) {
                throw new AssertionError("lowestRisk should not throw when all equal");
            }
            System.out.println("Test 15 PASSED: highestRisk/lowestRisk không exception khi tất cả bằng nhau");
            passed++;
        } catch (Throwable e) { System.out.println("Test 15 FAILED: " + e.getMessage()); failed++; }

        // ==================== PART E: VISITOR PATTERN ====================

        // Test 16: TaxReportVisitor Stock 15%
        try {
            TaxReportVisitor visitor = new TaxReportVisitor();
            Stock s = new Stock("AAPL", "Apple", 100.0, 3_000_000_000_000L, "Tech");
            s.accept(visitor);
            double tax = visitor.getTotalTaxLiability(); // 100 * 0.15 = 15.0
            if (tax != 15.0)
                throw new AssertionError("Expected 15.0 but got " + tax);
            System.out.println("Test 16 PASSED: Stock tax = 15.0 (15%)");
            passed++;
        } catch (Throwable e) { System.out.println("Test 16 FAILED: " + e.getMessage()); failed++; }

        // Test 17: TaxReportVisitor Bond 30% of annualCoupon
        try {
            TaxReportVisitor visitor = new TaxReportVisitor();
            Bond b = new Bond("US10Y", "US Treasury", 1000.0, 5.0, 10);
            // annualCoupon(1) = 1000 * 5 / 100 = 50, tax = 50 * 0.30 = 15.0
            b.accept(visitor);
            double tax = visitor.getTotalTaxLiability();
            if (tax != 15.0)
                throw new AssertionError("Expected 15.0 but got " + tax);
            System.out.println("Test 17 PASSED: Bond tax = 15.0 (30% of coupon 50)");
            passed++;
        } catch (Throwable e) { System.out.println("Test 17 FAILED: " + e.getMessage()); failed++; }

        // Test 18: TaxReportVisitor Option 20%
        try {
            TaxReportVisitor visitor = new TaxReportVisitor();
            Option o = new Option("OPT1", "AAPL Call", 100.0, 160.0, true, 30);
            o.accept(visitor);
            double tax = visitor.getTotalTaxLiability(); // 100 * 0.20 = 20.0
            if (tax != 20.0)
                throw new AssertionError("Expected 20.0 but got " + tax);
            System.out.println("Test 18 PASSED: Option tax = 20.0 (20%)");
            passed++;
        } catch (Throwable e) { System.out.println("Test 18 FAILED: " + e.getMessage()); failed++; }

        // Test 19: TaxReportVisitor Future 20%
        try {
            TaxReportVisitor visitor = new TaxReportVisitor();
            Future f = new Future("GOLD1", "Gold Future", 100.0, 100.0, 30);
            f.accept(visitor);
            double tax = visitor.getTotalTaxLiability(); // 100 * 0.20 = 20.0
            if (tax != 20.0)
                throw new AssertionError("Expected 20.0 but got " + tax);
            System.out.println("Test 19 PASSED: Future tax = 20.0 (20%)");
            passed++;
        } catch (Throwable e) { System.out.println("Test 19 FAILED: " + e.getMessage()); failed++; }

        // Test 20: TaxReportVisitor tích lũy nhiều instruments
        try {
            TaxReportVisitor visitor = new TaxReportVisitor();
            new Stock("AAPL", "Apple", 100.0, 3_000_000_000_000L, "Tech").accept(visitor); // 15
            new Option("O1", "Call", 100.0, 160.0, true, 30).accept(visitor);              // 20
            double tax = visitor.getTotalTaxLiability(); // 35.0
            if (tax != 35.0)
                throw new AssertionError("Expected 35.0 but got " + tax);
            System.out.println("Test 20 PASSED: Stock+Option total tax = 35.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 20 FAILED: " + e.getMessage()); failed++; }

        // Test 21: TaxReportVisitor tích lũy tất cả 4 loại
        try {
            TaxReportVisitor visitor = new TaxReportVisitor();
            new Stock("S1", "Stock",   100.0, 3_000_000_000_000L, "Tech").accept(visitor); // 15
            new Bond("B1",  "Bond",   1000.0, 5.0, 10).accept(visitor);                   // 15
            new Option("O1","Option",  100.0, 160.0, true, 30).accept(visitor);            // 20
            new Future("F1","Future",  100.0, 100.0, 30).accept(visitor);                  // 20
            double tax = visitor.getTotalTaxLiability(); // 70.0
            if (tax != 70.0)
                throw new AssertionError("Expected 70.0 but got " + tax);
            System.out.println("Test 21 PASSED: Stock+Bond+Option+Future total tax = 70.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 21 FAILED: " + e.getMessage()); failed++; }

        // Test 22: getReport không null và không rỗng
        try {
            TaxReportVisitor visitor = new TaxReportVisitor();
            new Stock("AAPL", "Apple", 100.0, 3_000_000_000_000L, "Tech").accept(visitor);
            String report = visitor.getReport();
            if (report == null || report.isEmpty())
                throw new AssertionError("Report should not be null or empty");
            System.out.println("Test 22 PASSED: getReport not null/empty: " + report.trim());
            passed++;
        } catch (Throwable e) { System.out.println("Test 22 FAILED: " + e.getMessage()); failed++; }

        // Test 23: accept gọi đúng visit method - Stock không gọi nhầm Bond
        try {
            TaxReportVisitor visitor = new TaxReportVisitor();
            // Stock $200 -> tax = 200 * 0.15 = 30
            new Stock("S1", "Stock", 200.0, 3_000_000_000_000L, "Tech").accept(visitor);
            double tax = visitor.getTotalTaxLiability();
            if (tax != 30.0)
                throw new AssertionError("Expected 30.0 but got " + tax);
            System.out.println("Test 23 PASSED: Stock $200 tax = 30.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 23 FAILED: " + e.getMessage()); failed++; }

        // Test 24: accept gọi đúng - Bond coupon rate khác nhau
        try {
            TaxReportVisitor visitor = new TaxReportVisitor();
            // Bond price=1000, coupon=10% -> annualCoupon=100, tax=30
            new Bond("B1", "Bond", 1000.0, 10.0, 10).accept(visitor);
            double tax = visitor.getTotalTaxLiability();
            if (tax != 30.0)
                throw new AssertionError("Expected 30.0 but got " + tax);
            System.out.println("Test 24 PASSED: Bond coupon=10% tax = 30.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 24 FAILED: " + e.getMessage()); failed++; }

        // Test 25: Visitor pattern - dùng Instrument reference gọi accept
        try {
            TaxReportVisitor visitor = new TaxReportVisitor();
            Instrument inst = new Stock("AAPL", "Apple", 100.0, 3_000_000_000_000L, "Tech");
            inst.accept(visitor); // gọi qua Instrument reference
            double tax = visitor.getTotalTaxLiability(); // 15.0
            if (tax != 15.0)
                throw new AssertionError("Expected 15.0 but got " + tax);
            System.out.println("Test 25 PASSED: accept qua Instrument reference tax = 15.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 25 FAILED: " + e.getMessage()); failed++; }

        // Test 26: Visitor mới - totalTaxLiability bắt đầu từ 0
        try {
            TaxReportVisitor visitor = new TaxReportVisitor();
            double tax = visitor.getTotalTaxLiability();
            if (tax != 0.0)
                throw new AssertionError("Expected 0.0 but got " + tax);
            System.out.println("Test 26 PASSED: TaxReportVisitor mới totalTaxLiability = 0.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 26 FAILED: " + e.getMessage()); failed++; }

        // Test 27: accept nhiều Stock liên tiếp tích lũy đúng
        try {
            TaxReportVisitor visitor = new TaxReportVisitor();
            new Stock("S1", "Stock1", 100.0, 3_000_000_000_000L, "Tech").accept(visitor); // 15
            new Stock("S2", "Stock2", 200.0, 3_000_000_000_000L, "Tech").accept(visitor); // 30
            new Stock("S3", "Stock3", 300.0, 3_000_000_000_000L, "Tech").accept(visitor); // 45
            double tax = visitor.getTotalTaxLiability(); // 90.0
            if (tax != 90.0)
                throw new AssertionError("Expected 90.0 but got " + tax);
            System.out.println("Test 27 PASSED: 3 Stocks total tax = 90.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 27 FAILED: " + e.getMessage()); failed++; }

        // Test 28: accept nhiều Bond liên tiếp tích lũy đúng
        try {
            TaxReportVisitor visitor = new TaxReportVisitor();
            // B1: price=1000, coupon=5% -> coupon=50, tax=15
            // B2: price=2000, coupon=5% -> coupon=100, tax=30
            new Bond("B1", "Bond1", 1000.0, 5.0, 10).accept(visitor);
            new Bond("B2", "Bond2", 2000.0, 5.0, 10).accept(visitor);
            double tax = visitor.getTotalTaxLiability(); // 45.0
            if (tax != 45.0)
                throw new AssertionError("Expected 45.0 but got " + tax);
            System.out.println("Test 28 PASSED: 2 Bonds total tax = 45.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 28 FAILED: " + e.getMessage()); failed++; }

        // Test 29: mix nhiều loại, tính từng bước rồi kiểm tra tổng
        try {
            TaxReportVisitor visitor = new TaxReportVisitor();
            // Stock $500 -> 75
            // Bond price=1000, coupon=5% -> coupon=50, tax=15
            // Future $200 -> 40
            new Stock("S1", "Stock",   500.0, 3_000_000_000_000L, "Tech").accept(visitor);
            new Bond("B1",  "Bond",   1000.0, 5.0, 10).accept(visitor);
            new Future("F1","Future",  200.0, 100.0, 30).accept(visitor);
            double tax = visitor.getTotalTaxLiability(); // 130.0
            if (tax != 130.0)
                throw new AssertionError("Expected 130.0 but got " + tax);
            System.out.println("Test 29 PASSED: Stock+Bond+Future total = 130.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 29 FAILED: " + e.getMessage()); failed++; }

        // Test 30: getReport chứa tổng cuối cùng
        try {
            TaxReportVisitor visitor = new TaxReportVisitor();
            new Stock("AAPL", "Apple", 100.0, 3_000_000_000_000L, "Tech").accept(visitor); // 15
            new Option("O1", "Call",   100.0, 160.0, true, 30).accept(visitor);            // 20
            String report = visitor.getReport();
            if (!report.contains("35") && !report.contains("35.00"))
                throw new AssertionError("Report should contain total 35, got: " + report);
            System.out.println("Test 30 PASSED: getReport chứa total=35: " + report.trim());
            passed++;
        } catch (Throwable e) { System.out.println("Test 30 FAILED: " + e.getMessage()); failed++; }

        // ==================== SUMMARY ====================
        System.out.println("\n===== RESULTS: " + passed + " passed, " + failed + " failed =====");
    }
}