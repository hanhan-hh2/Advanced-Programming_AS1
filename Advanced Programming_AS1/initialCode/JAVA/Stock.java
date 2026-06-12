
public class Stock extends Instrument {
    private final double marketCap;
    private final String sector;

    // Stock là cổ phẩn của một công ty 
    public Stock(String symbol, String name, double currentPrice, double marketCap, String sector) {
        super(symbol, name, currentPrice);
        // Constructor gọi lại constructor của cha 
        this.marketCap = marketCap; 
        this.sector = sector;
    }

    @Override
    public double riskScore() {
        // marketCap càng lớn thì riskScore càng nhỏ 
        // Có thể viết số theo kiểu này cho dễ đọc chứ không ảnh hưởng đến giá trị 
        if (marketCap < 1_000_000_000) {
            return 7.5;
        } else if (marketCap < 10_000_000_000L) {
            // Thêm L để biết đây là kiểu long thay vì int
            return 5.0;
        } else {
            return 3.0;
        }
    }

    @Override
    public String assetClass() {
        return "EQUITY";
    }
    
    public double getMarketCap() {
        return marketCap;
    }

    public String getSector() {
        return sector;
    }

    @Override 
    public void accept (InstrumentVisitor visitor) {
        visitor.visit(this);
    }
}
