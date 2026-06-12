
public class TaxReportVisitor implements InstrumentVisitor{
    private double totalTaxLiability = 0; 

    @Override 
    public void visit (Stock stock) {
        totalTaxLiability += stock.getCurrentPriceValue() * 0.15;
    }

    @Override 
    public void visit (Bond bond) {
        totalTaxLiability += bond.annualCouponPayment(1) * 0.30;
    }

    @Override 
    public void visit (Option option) {
        totalTaxLiability += option.getCurrentPriceValue() * 0.20;
    }

    @Override 
    public void visit (Future future) {
        totalTaxLiability += future.getCurrentPriceValue() * 0.20;
    }

    public double getTotalTaxLiability() {
        return totalTaxLiability;
    }

    // Đề không nói rõ hàm này là trong từng cái con phải có hay sao ???
    public String getReport () {
        return String.format ("Total Tax Liability: %.2f", totalTaxLiability);
    }
}
