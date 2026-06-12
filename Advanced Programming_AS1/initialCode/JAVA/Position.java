public class Position {
    private final Instrument instrument;
    private int quantity;
    private double averageCostBasis;

    public Position(Instrument instrument, int quantity, double averageCostBasis) {
        // Đề bài không nói các trường hợp không có xử lý thế nào 
        if (instrument == null) {
            throw new IllegalArgumentException ("Instrument cannot be null");
        }

        if (quantity < 0 || averageCostBasis < 0) {
            throw new IllegalArgumentException ("Quantity and cost basis must be non-negative");
        }

        this.instrument = instrument; 
        this.quantity = quantity; 
        this.averageCostBasis = averageCostBasis;
    }

    public double marketValue() {
        return quantity * instrument.getCurrentPriceValue();
    }

    public double unrealizedPnL() {
        return marketValue() - (quantity*averageCostBasis);
        // Khoảng lời hơn so với giá mua vào
    }

    public void addQuantity(int qty, double costBasis) {
        if (qty <= 0 || costBasis < 0) {
            throw new IllegalArgumentException ("Invalid quantity or cost basis");
        }

        // Đề nói là phải cập nhật lại weight 
        double totalCost = (this.quantity * this.averageCostBasis) + (qty * costBasis);
        this.quantity += qty; 
        this.averageCostBasis = totalCost / this.quantity;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAverageCostBasis() {
        return averageCostBasis;
    }

    @Override
    public String toString() {
        return String.format(
            "Position[symbol=%s, qty=%d, value=%.2f, pnl=%.2f]",
            instrument.getSymbol(),
            quantity,
            marketValue(),
            unrealizedPnL()
        );
    }
}
