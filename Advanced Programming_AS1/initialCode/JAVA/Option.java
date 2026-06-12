public class Option extends Derivative {
    private final double strikePrice;
    private final boolean isCall;
    private final int expiryDays;

    // strikePrice: giá bạn được quyền mua/bán 
    // expiryDays = hạn cuối để sử dụng quyền 
    // isInTheMoney: xem xét việc mua bán hiện tại có lời hay lỗ 
    // isCall là quyền mua bán (true = quyền mua, false = quyền bán)

    public Option(String symbol, String name, double currentPrice, double strikePrice, boolean isCall, int expiryDays) {
        super(symbol, name, currentPrice);
        // Vì nó extends Derivative, mà Derivative lại extend Instrument nên nó các attribute của instrument
        this.strikePrice = strikePrice; 
        this.isCall = isCall; 
        this.expiryDays = expiryDays;
    }

    @Override
    public double riskScore() {
        // Đề cho mặc định luôn 
        return 8.5;
    }
    
    public boolean isInTheMoney(double spotPrice) {
        // Kiểm tra dùng coupon vào bây giờ có lợi không 
        // spotPrice: giá bán của thị trường hiện tại 
        // isCall là quyền mua bán (true = quyền mua, false = quyền bán)
        
        // Mình mua ở giá strikePrice, mà giá thị trường hiện tại lớn hơn strikePrice => lời 
        // Mình bán ở giá strike cao hơn giá thị trường nên lời hơn

        if (isCall) return spotPrice > strikePrice;
        else return spotPrice < strikePrice;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public boolean isCall() {
        return isCall;
    }

    public int getExpiryDays() {
        return expiryDays;
    }

    @Override 
    public void accept (InstrumentVisitor visitor) {
        visitor.visit (this);
    }
}
