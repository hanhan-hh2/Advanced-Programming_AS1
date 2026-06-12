import java.time.LocalDateTime;
public abstract class Instrument implements Tradeable, Priceable{
    private final String symbol;
    private String name;
    private double currentPrice;
    private LocalDateTime lastUpdated;

    public Instrument(String symbol, String name, double currentPrice) {
        if (currentPrice < 0) {
            throw new IllegalArgumentException ("Price cannot be negative");
        }

        this.symbol = symbol; 
        this.name = name; 
        this.currentPrice = currentPrice; 
        this.lastUpdated = LocalDateTime.now();
    }

    public abstract double riskScore();

    public abstract String assetClass();
    
    public void updatePrice(double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException ("Price cannot be negative");
        }

        this.currentPrice = newPrice; 
        this.lastUpdated = LocalDateTime.now();
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public double getCurrentPriceValue() {
        return currentPrice;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public String toString() {
        // Gọi hàm này để in ra chuỗi thông tin theo cách mình định dạng 
        // Không nói rõ là in ra bao nhiêu số thập phân
        return String.format ("%s[symbol=%s, price=%.2f, risk=%.2f]", 
        getClass().getSimpleName(),
        symbol, 
        currentPrice, 
        riskScore()
        );

        // % là chèn vào 
        // s là tring 
        // 2f là chèn số thập phân có 2 chữ số 
        // getClass().getSimpleName() là lấy tên class của object 
    }

    // Các hàm implements từ Tradeable và Priceable 
    @Override 
    public boolean isAvailableForTrading () {
        return true;
    }

    @Override 
    public double getPriceChange (double previousPrice) {
        return currentPrice - previousPrice;
    }

    @Override 
    public double getPriceChangePercent (double previousPrice) {
        return ((currentPrice - previousPrice) / previousPrice) * 100;
    }

    // Cho phép một đối tượng Instrumet nhận một visitor và cho phép visitor xử lý trên nó
    // Không có body, kể cả {} bỏ trống như vầy
    public abstract void accept (InstrumentVisitor visitor);
}
