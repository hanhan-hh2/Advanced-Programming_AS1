public class Bond extends Instrument {
    private final double couponRate;
    private final int maturityYears;

    // Bond = trái phiếu, là một khoản vay mà mình cho một tổ chức vay, họ trả lãi hàng năm (coupon) và trả gốc sau một thời gian
    // couponRate: là suất hàng năm 
    // maturityYears: số năm còn đến hạn trả gốc 

    public Bond(String symbol, String name, double currentPrice, double couponRate, int maturityYears) {
        super(symbol, name, currentPrice);
        this.couponRate = couponRate; 
        this.maturityYears = maturityYears;
        
    }

    @Override
    public double riskScore() {
        // Thời hạn càng lâu thì rủi ro càng lớn
        if (maturityYears > 10) return 4.0; 
        else return 2.0;
    }

    @Override
    public String assetClass() {
        return "FIXED_INCOME";
    }

    public double annualCouponPayment(int units) {
        return units* getCurrentPriceValue() * couponRate / 100;
        // getCurrentPriceValue() là hàm ở đâu ra
        // Là phương thức trong class Instrument 
    }

    public double getCouponRate() {
        return couponRate;
    }

    public int getMaturityYears() {
        return maturityYears;
    }

    @Override 
    public void accept (InstrumentVisitor visitor) {
        visitor.visit(this);
    }
}
