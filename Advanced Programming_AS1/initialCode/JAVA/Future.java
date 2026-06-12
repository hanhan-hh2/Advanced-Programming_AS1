public class Future extends Derivative {
    private final double contractSize;
    private final int expiryDays;

    // contratSize: số lượng mua/bán 
    // expiryDays: sau bao nhiêu ngày thì hợp đồng phải được thực hiện 
    public Future(String symbol, String name, double currentPrice, double contractSize, int expiryDays) {
        super(symbol, name, currentPrice);
        this.contractSize = contractSize; 
        this.expiryDays = expiryDays;
    }

    @Override
    public double riskScore() {
        return 8.5;
    }

    public double getContractSize() {
        return contractSize;
    }

    public int getExpiryDays() {
        return expiryDays;
    }

    @Override 
    public void accept (InstrumentVisitor visitor) {
        visitor.visit (this);
    }
}
