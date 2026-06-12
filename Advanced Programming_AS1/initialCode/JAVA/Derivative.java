public abstract class Derivative extends Instrument {

    // Derivative: hợp đồng liên quan đén giá của món đồ khác 
    // Option: có quyền mua/bán nhưng không bắt buộc 
    // Future: bắt buộc phải mua/bán với giá đã định vào hôm nay (bất kể vào ngày mua bán giá có thay đổi thế nào)
   
    public Derivative(String symbol, String name, double currentPrice) {
        super(symbol, name, currentPrice);
        // Không có thêm thuộc tính gì 
    }

    @Override
    public String assetClass() {
        return "DERIVATIVE";
    }
}
