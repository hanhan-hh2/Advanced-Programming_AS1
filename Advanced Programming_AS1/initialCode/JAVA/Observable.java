public interface Observable<T> {
    void addObserver (Observer<T> observer);
    void removeObserver (Observer<T> observer);
    void notifyObservers (T event);

    // publisher : thông báo 
    // Có nhiệm vụ lưu và thay đổi danh sách observers
}
