package JAVA;
import java.util.List;

import Full.Stock;

public class MainC {
    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        // ==================== POSITION CƠ BẢN ====================

        // Test 1: Position marketValue cơ bản
        try {
            Stock aapl = new Stock("AAPL", "Apple", 160.0, 3_000_000_000_000L, "Tech");
            Position pos = new Position(aapl, 10, 150.0);
            if (pos.marketValue() != 1600.0)
                throw new AssertionError("Expected 1600.0 but got " + pos.marketValue());
            System.out.println("Test 1 PASSED: marketValue = 1600.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 1 FAILED: " + e.getMessage()); failed++; }

        // Test 2: Position unrealizedPnL dương (lời)
        try {
            Stock aapl = new Stock("AAPL", "Apple", 160.0, 3_000_000_000_000L, "Tech");
            Position pos = new Position(aapl, 10, 150.0);
            if (pos.unrealizedPnL() != 100.0)
                throw new AssertionError("Expected 100.0 but got " + pos.unrealizedPnL());
            System.out.println("Test 2 PASSED: unrealizedPnL = +100.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 2 FAILED: " + e.getMessage()); failed++; }

        // Test 3: Position unrealizedPnL âm (lỗ)
        try {
            Stock aapl = new Stock("AAPL", "Apple", 140.0, 3_000_000_000_000L, "Tech");
            Position pos = new Position(aapl, 10, 150.0);
            if (pos.unrealizedPnL() != -100.0)
                throw new AssertionError("Expected -100.0 but got " + pos.unrealizedPnL());
            System.out.println("Test 3 PASSED: unrealizedPnL = -100.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 3 FAILED: " + e.getMessage()); failed++; }

        // Test 4: Position unrealizedPnL = 0 (hoà vốn)
        try {
            Stock aapl = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            Position pos = new Position(aapl, 10, 150.0);
            if (pos.unrealizedPnL() != 0.0)
                throw new AssertionError("Expected 0.0 but got " + pos.unrealizedPnL());
            System.out.println("Test 4 PASSED: unrealizedPnL = 0.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 4 FAILED: " + e.getMessage()); failed++; }

        // Test 5: Position constructor null instrument -> exception
        try {
            Position pos = new Position(null, 10, 150.0);
            System.out.println("Test 5 FAILED: Expected IllegalArgumentException");
            failed++;
        } catch (IllegalArgumentException e) {
            System.out.println("Test 5 PASSED: null instrument throws exception");
            passed++;
        } catch (Throwable e) { System.out.println("Test 5 FAILED: " + e.getMessage()); failed++; }

        // Test 6: Position constructor quantity âm -> exception
        try {
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            Position pos = new Position(s, -1, 150.0);
            System.out.println("Test 6 FAILED: Expected IllegalArgumentException");
            failed++;
        } catch (IllegalArgumentException e) {
            System.out.println("Test 6 PASSED: negative quantity throws exception");
            passed++;
        } catch (Throwable e) { System.out.println("Test 6 FAILED: " + e.getMessage()); failed++; }

        // ==================== ADD QUANTITY ====================

        // Test 7: addQuantity weighted average cost cơ bản
        try {
            Stock s = new Stock("AAPL", "Apple", 160.0, 3_000_000_000_000L, "Tech");
            Position pos = new Position(s, 10, 150.0); // 10 * 150 = 1500
            pos.addQuantity(10, 170.0);                // 10 * 170 = 1700, total = 3200/20 = 160
            if (pos.getQuantity() != 20)
                throw new AssertionError("Expected qty=20 but got " + pos.getQuantity());
            if (pos.getAverageCostBasis() != 160.0)
                throw new AssertionError("Expected avgCost=160.0 but got " + pos.getAverageCostBasis());
            System.out.println("Test 7 PASSED: addQuantity weighted avg = 160.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 7 FAILED: " + e.getMessage()); failed++; }

        // Test 8: addQuantity cập nhật marketValue và unrealizedPnL đúng sau merge
        try {
            Stock s = new Stock("AAPL", "Apple", 160.0, 3_000_000_000_000L, "Tech");
            Position pos = new Position(s, 10, 150.0);
            pos.addQuantity(10, 170.0); // avgCost = 160.0, qty = 20
            double expectedMV  = 20 * 160.0; // 3200.0
            double expectedPnL = 3200.0 - (20 * 160.0); // 0.0
            if (pos.marketValue() != expectedMV)
                throw new AssertionError("Expected MV=" + expectedMV + " but got " + pos.marketValue());
            if (pos.unrealizedPnL() != expectedPnL)
                throw new AssertionError("Expected PnL=" + expectedPnL + " but got " + pos.unrealizedPnL());
            System.out.println("Test 8 PASSED: marketValue=" + expectedMV + ", pnl=" + expectedPnL);
            passed++;
        } catch (Throwable e) { System.out.println("Test 8 FAILED: " + e.getMessage()); failed++; }

        // Test 9: addQuantity qty <= 0 -> exception
        try {
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            Position pos = new Position(s, 10, 150.0);
            pos.addQuantity(-10, 150.0);
            System.out.println("Test 9 FAILED: Expected IllegalArgumentException");
            failed++;
        } catch (IllegalArgumentException e) {
            System.out.println("Test 9 PASSED: addQuantity qty=0 throws exception");
            passed++;
        } catch (Throwable e) { System.out.println("Test 9 FAILED: " + e.getMessage()); failed++; }

        // Test 10: addQuantity nhiều lần liên tiếp
        try {
            Stock s = new Stock("AAPL", "Apple", 160.0, 3_000_000_000_000L, "Tech");
            Position pos = new Position(s, 10, 100.0); // cost = 1000
            pos.addQuantity(10, 200.0);                // cost = 2000, total = 3000/20 = 150
            pos.addQuantity(20, 150.0);                // cost = 3000, total = 6000/40 = 150
            if (pos.getQuantity() != 40)
                throw new AssertionError("Expected qty=40 but got " + pos.getQuantity());
            if (pos.getAverageCostBasis() != 150.0)
                throw new AssertionError("Expected avgCost=150.0 but got " + pos.getAverageCostBasis());
            System.out.println("Test 10 PASSED: addQuantity 3 lần, qty=40, avgCost=150.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 10 FAILED: " + e.getMessage()); failed++; }

        // ==================== PORTFOLIO CƠ BẢN ====================

        // Test 11: addPosition tạo mới position
        try {
            Portfolio portfolio = new Portfolio("P1", "Alice");
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            portfolio.addPosition(s, 10, 150.0);
            Position pos = portfolio.getPosition("AAPL");
            if (pos.getQuantity() != 10)
                throw new AssertionError("Expected qty=10 but got " + pos.getQuantity());
            System.out.println("Test 11 PASSED: addPosition tạo mới position");
            passed++;
        } catch (Throwable e) { System.out.println("Test 11 FAILED: " + e.getMessage()); failed++; }

        // Test 12: addPosition merge position đã có
        try {
            Portfolio portfolio = new Portfolio("P1", "Alice");
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            portfolio.addPosition(s, 10, 150.0);
            portfolio.addPosition(s, 5, 160.0);
            Position pos = portfolio.getPosition("AAPL");
            if (pos.getQuantity() != 15)
                throw new AssertionError("Expected qty=15 but got " + pos.getQuantity());
            System.out.println("Test 12 PASSED: addPosition merge qty=15");
            passed++;
        } catch (Throwable e) { System.out.println("Test 12 FAILED: " + e.getMessage()); failed++; }

        // Test 13: removePosition hợp lệ
        try {
            Portfolio portfolio = new Portfolio("P1", "Alice");
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            portfolio.addPosition(s, 10, 150.0);
            portfolio.removePosition("AAPL");
            try {
                portfolio.getPosition("AAPL");
                throw new AssertionError("Expected PositionNotFoundException");
            } catch (PositionNotFoundException ex) {
                // đúng
            }
            System.out.println("Test 13 PASSED: removePosition thành công");
            passed++;
        } catch (Throwable e) { System.out.println("Test 13 FAILED: " + e.getMessage()); failed++; }

        // Test 14: removePosition không tồn tại -> PositionNotFoundException
        try {
            Portfolio portfolio = new Portfolio("P1", "Alice");
            portfolio.removePosition("UNKNOWN");
            System.out.println("Test 14 FAILED: Expected PositionNotFoundException");
            failed++;
        } catch (PositionNotFoundException e) {
            System.out.println("Test 14 PASSED: removePosition not found throws exception");
            passed++;
        } catch (Throwable e) { System.out.println("Test 14 FAILED: " + e.getMessage()); failed++; }

        // Test 15: getPosition không tồn tại -> PositionNotFoundException
        try {
            Portfolio portfolio = new Portfolio("P1", "Alice");
            portfolio.getPosition("UNKNOWN");
            System.out.println("Test 15 FAILED: Expected PositionNotFoundException");
            failed++;
        } catch (PositionNotFoundException e) {
            System.out.println("Test 15 PASSED: getPosition not found throws exception");
            passed++;
        } catch (Throwable e) { System.out.println("Test 15 FAILED: " + e.getMessage()); failed++; }

        // ==================== PORTFOLIO TÍNH TOÁN ====================

        // Test 16: totalMarketValue nhiều positions
        try {
            Portfolio portfolio = new Portfolio("P1", "Alice");
            Stock aapl = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            Bond bond  = new Bond("US10Y", "US Treasury", 1000.0, 5.0, 10);
            portfolio.addPosition(aapl, 10, 150.0); // MV = 1500
            portfolio.addPosition(bond, 2, 1000.0); // MV = 2000
            double total = portfolio.totalMarketValue();
            if (total != 3500.0)
                throw new AssertionError("Expected 3500.0 but got " + total);
            System.out.println("Test 16 PASSED: totalMarketValue = 3500.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 16 FAILED: " + e.getMessage()); failed++; }

        // Test 17: totalUnrealizedPnL nhiều positions
        try {
            Portfolio portfolio = new Portfolio("P1", "Alice");
            Stock aapl = new Stock("AAPL", "Apple", 160.0, 3_000_000_000_000L, "Tech");
            Stock msft = new Stock("MSFT", "Microsoft", 90.0, 3_000_000_000_000L, "Tech");
            portfolio.addPosition(aapl, 10, 150.0); // PnL = +100
            portfolio.addPosition(msft, 10, 100.0); // PnL = -100
            double pnl = portfolio.totalUnrealizedPnL();
            if (pnl != 0.0)
                throw new AssertionError("Expected 0.0 but got " + pnl);
            System.out.println("Test 17 PASSED: totalUnrealizedPnL = 0.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 17 FAILED: " + e.getMessage()); failed++; }

        // Test 18: getPositionsSortedByValue descending
        try {
            Portfolio portfolio = new Portfolio("P1", "Alice");
            Stock s1 = new Stock("AAPL", "Apple",  100.0, 3_000_000_000_000L, "Tech"); // MV=500
            Stock s2 = new Stock("MSFT", "Microsoft", 200.0, 3_000_000_000_000L, "Tech"); // MV=2000
            Stock s3 = new Stock("GOOG", "Google", 300.0, 3_000_000_000_000L, "Tech"); // MV=900
            portfolio.addPosition(s1, 5,  100.0);
            portfolio.addPosition(s2, 10, 200.0);
            portfolio.addPosition(s3, 3,  300.0);
            List<Position> sorted = portfolio.getPositionsSortedByValue();
            if (sorted.get(0).marketValue() != 2000.0)
                throw new AssertionError("Expected first=2000 but got " + sorted.get(0).marketValue());
            if (sorted.get(1).marketValue() != 900.0)
                throw new AssertionError("Expected second=900 but got " + sorted.get(1).marketValue());
            if (sorted.get(2).marketValue() != 500.0)
                throw new AssertionError("Expected third=500 but got " + sorted.get(2).marketValue());
            System.out.println("Test 18 PASSED: getPositionsSortedByValue descending: 2000, 900, 500");
            passed++;
        } catch (Throwable e) { System.out.println("Test 18 FAILED: " + e.getMessage()); failed++; }

        // Test 19: revalueAll cập nhật giá đúng
        try {
            Portfolio portfolio = new Portfolio("P1", "Alice");
            Stock s = new Stock("AAPL", "Apple", 100.0, 3_000_000_000_000L, "Tech");
            portfolio.addPosition(s, 10, 100.0);
            portfolio.revalueAll(new SimplePricingStrategy()); // 100 * 1.05 = 105
            double mv = portfolio.getPosition("AAPL").marketValue();
            if (mv != 1050.0)
                throw new AssertionError("Expected MV=1050.0 but got " + mv);
            System.out.println("Test 19 PASSED: revalueAll SimplePricingStrategy MV=1050.0");
            passed++;
        } catch (Throwable e) { System.out.println("Test 19 FAILED: " + e.getMessage()); failed++; }

        // ==================== OBSERVER PATTERN ====================

        // Test 20: Observer nhận event ADDED
        try {
            Portfolio portfolio = new Portfolio("P1", "Alice");
            StringBuilder received = new StringBuilder();
            portfolio.addObserver(event -> received.append(event));
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            portfolio.addPosition(s, 5, 150.0);
            if (!received.toString().contains("ADDED") || !received.toString().contains("AAPL"))
                throw new AssertionError("Expected ADDED AAPL but got: " + received);
            System.out.println("Test 20 PASSED: Observer nhận ADDED: " + received);
            passed++;
        } catch (Throwable e) { System.out.println("Test 20 FAILED: " + e.getMessage()); failed++; }

        // Test 21: Observer nhận event REMOVED
        try {
            Portfolio portfolio = new Portfolio("P1", "Alice");
            StringBuilder received = new StringBuilder();
            portfolio.addObserver(event -> received.append(event));
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            portfolio.addPosition(s, 5, 150.0);
            received.setLength(0); // reset
            portfolio.removePosition("AAPL");
            if (!received.toString().contains("REMOVED") || !received.toString().contains("AAPL"))
                throw new AssertionError("Expected REMOVED AAPL but got: " + received);
            System.out.println("Test 21 PASSED: Observer nhận REMOVED: " + received);
            passed++;
        } catch (Throwable e) { System.out.println("Test 21 FAILED: " + e.getMessage()); failed++; }

        // Test 22: Observer nhận event REVALUED
        try {
            Portfolio portfolio = new Portfolio("P1", "Alice");
            StringBuilder received = new StringBuilder();
            portfolio.addObserver(event -> received.append(event));
            Stock s = new Stock("AAPL", "Apple", 100.0, 3_000_000_000_000L, "Tech");
            portfolio.addPosition(s, 10, 100.0);
            received.setLength(0); // reset
            portfolio.revalueAll(new SimplePricingStrategy());
            if (!received.toString().contains("REVALUED"))
                throw new AssertionError("Expected REVALUED but got: " + received);
            System.out.println("Test 22 PASSED: Observer nhận REVALUED: " + received);
            passed++;
        } catch (Throwable e) { System.out.println("Test 22 FAILED: " + e.getMessage()); failed++; }

        // Test 23: removeObserver -> không còn nhận event
        try {
            Portfolio portfolio = new Portfolio("P1", "Alice");
            StringBuilder received = new StringBuilder();
            Observer<String> obs = event -> received.append(event);
            portfolio.addObserver(obs);
            portfolio.removeObserver(obs);
            Stock s = new Stock("AAPL", "Apple", 150.0, 3_000_000_000_000L, "Tech");
            portfolio.addPosition(s, 5, 150.0);
            if (received.length() != 0)
                throw new AssertionError("Observer should not receive after remove, got: " + received);
            System.out.println("Test 23 PASSED: removeObserver không nhận event nữa");
            passed++;
        } catch (Throwable e) { System.out.println("Test 23 FAILED: " + e.getMessage()); failed++; }

        // ==================== GỌI NHIỀU HÀM LIÊN TIẾP ====================

        // Test 24: Thêm nhiều positions, tính toán tổng, sort, revalue liên tiếp
        try {
            Portfolio portfolio = new Portfolio("P1", "Alice");
            Stock aapl = new Stock("AAPL", "Apple",  100.0, 3_000_000_000_000L, "Tech");
            Stock msft = new Stock("MSFT", "Microsoft", 200.0, 3_000_000_000_000L, "Tech");
            Bond  bond = new Bond("US10Y", "US Treasury", 1000.0, 5.0, 10);

            portfolio.addPosition(aapl, 10, 100.0); // MV=1000
            portfolio.addPosition(msft, 5,  200.0); // MV=1000
            portfolio.addPosition(bond, 1, 1000.0); // MV=1000

            double totalBefore = portfolio.totalMarketValue(); // 3000
            if (totalBefore != 3000.0)
                throw new AssertionError("Expected total=3000 but got " + totalBefore);

            portfolio.revalueAll(new SimplePricingStrategy()); // *1.05
            double totalAfter = portfolio.totalMarketValue();  // 3150
            if (Math.abs(totalAfter - 3150.0) > 0.001)
                throw new AssertionError("Expected total=3150 but got " + totalAfter);

            List<Position> sorted = portfolio.getPositionsSortedByValue();
            if (sorted.size() != 3)
                throw new AssertionError("Expected 3 positions but got " + sorted.size());

            System.out.println("Test 24 PASSED: totalBefore=" + totalBefore + ", totalAfter=" + totalAfter + ", sorted size=" + sorted.size());
            passed++;
        } catch (Throwable e) { System.out.println("Test 24 FAILED: " + e.getMessage()); failed++; }

        // Test 25: Merge position nhiều lần, kiểm tra weighted avg liên tục
        try {
            Portfolio portfolio = new Portfolio("P1", "Alice");
            Stock s = new Stock("AAPL", "Apple", 200.0, 3_000_000_000_000L, "Tech");
            portfolio.addPosition(s, 10, 100.0); // avgCost=100
            portfolio.addPosition(s, 10, 200.0); // avgCost=150
            portfolio.addPosition(s, 20, 150.0); // avgCost=150

            Position pos = portfolio.getPosition("AAPL");
            if (pos.getQuantity() != 40)
                throw new AssertionError("Expected qty=40 but got " + pos.getQuantity());
            if (pos.getAverageCostBasis() != 150.0)
                throw new AssertionError("Expected avgCost=150 but got " + pos.getAverageCostBasis());
            double pnl = pos.unrealizedPnL(); // (200-150)*40 = 2000
            if (pnl != 2000.0)
                throw new AssertionError("Expected pnl=2000 but got " + pnl);

            System.out.println("Test 25 PASSED: merge 3 lần qty=40, avgCost=150, pnl=2000");
            passed++;
        } catch (Throwable e) { System.out.println("Test 25 FAILED: " + e.getMessage()); failed++; }

        // ==================== SUMMARY ====================
        System.out.println("\n===== RESULTS: " + passed + " passed, " + failed + " failed =====");
    }
}