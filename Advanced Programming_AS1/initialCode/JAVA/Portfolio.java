import java.util.*;
import java.util.stream.Collectors;

public class Portfolio implements Observable<String> {
    private final String portfolioId;
    private final String ownerName;
    private final List<Position> positions;
    private final List<Observer<String>> observers;

    public Portfolio(String portfolioId, String ownerName) {
        this.portfolioId = portfolioId; 
        this.ownerName = ownerName; 
        this.positions = new ArrayList<>(); 
        this.observers = new ArrayList<>();
    }

    public void addPosition(Instrument inst, int qty, double costBasis) {
        // Trường hợp xử lí chưa đề cập
        if (inst == null || qty < 0 || costBasis < 0) {
            throw new IllegalArgumentException ("Invalid instrument, quantity, or costBasis");
        }

        try {
            // Nếu position đã tồn tại -> merge quantity & cost
            Position pos = getPosition(inst.getSymbol());
            pos.addQuantity(qty, costBasis);
        } catch (PositionNotFoundException e) {
            // Nếu chưa có 
            Position pos = new Position (inst, qty, costBasis);
            positions.add(pos);
        }

        // Ví dụ:  "ADDED: MSFT x5"
        notifyObservers ("ADDED: " + inst.getSymbol() + " x" + qty);
    }

    public void removePosition(String symbol) throws PositionNotFoundException {
        Position pos = getPosition (symbol); //Nếu không tìm được -> exception
        positions.remove(pos);
        notifyObservers ("REMOVED: " + symbol);
    }

    public Position getPosition(String symbol) throws PositionNotFoundException {
        // position đang là list -> chuyển thành stream thì có thể thao tác filter, map, forEach
        // filter dùng để lọc các phần tử với điều kiện những Position có symbol đúng
        // findFirst() lấy phần tử đầu tiên thỏa filter
            // Nếu có Position đó thì trả về nó 
            // Nếu không thì ném exception
        return positions.stream()
            .filter (p->p.getInstrument().getSymbol().equals(symbol))
            .findFirst()
            .orElseThrow(()-> new PositionNotFoundException ("Position not found: " + symbol));
    }

    public double totalMarketValue() {
        // mapToDouble: biến mỗi phần tử Position thành một giá trị double 
        // Position::marketValue: lấy giá trị marketValue của chúng 
            // Ví dụ: P1-P2-P3 chuyển thành 1000-500-1300
        // sum() tính tổng hết 
        return positions.stream()
                        .mapToDouble(Position::marketValue)
                        .sum();
    }

    public double totalUnrealizedPnL() {
        return positions.stream()
                        .mapToDouble(Position::unrealizedPnL)
                        .sum();
    }

    public List<Position> getPositionsSortedByValue() {
        // comparator trả về âm nếu p2<p1, =0 nếu bằng nhau, dương nếu p2>p1
        // Vì p2 trước p1 nên đang muốn descending by market value
        // collect chuyển stream về thành list lại

        return positions.stream() 
                        .sorted((p1,p2)->Double.compare(p2.marketValue(), p1.marketValue()))
                        .collect (Collectors.toList());
    }

    public void revalueAll(PricingStrategy strategy) {
        for (Position p: positions) {
            double newPrice = strategy.calculateFairValue(p.getInstrument());
            p.getInstrument().updatePrice(newPrice);
        }

        notifyObservers ("REVALUED: " + strategy.strategyName());
    }

    // Không có mô tả cho hàm này
    public Map<String, Double> allocationByAssetClass() {
        Map<String, Double> allocation = new HashMap<>(); 
        double total = totalMarketValue(); 
        for (Position p: positions) {
            String assetClass = p.getInstrument().assetClass(); 
            allocation.put (assetClass, allocation.getOrDefault (assetClass, 0.0) + p.marketValue());
        }
        
        // Convert to percentage 
        for (String key : allocation.keySet()) {
            allocation.put (key, (allocation.get(key) / total) * 100);
        }

        return allocation;
    }

    // Observer pattern
    @Override
    public void addObserver(Observer<String> observer) {
        if (observer != null && !observers.contains(observer)) {
            // Nếu chưa có thì thêm mới vào
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer<String> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event) {
        for (Observer<String> obs: observers) {
            obs.onEvent(event);
            // Chưa có chỗ nào implement onEvent
            // Các thiết bị observer sẽ tự implement hàm này 
        }
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
