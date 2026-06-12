public interface Observer <T> {
    void onEvent(T event);
    
    // Nhận thông báo 
    // T là kiểu dữ liệu mà Observer sẽ nhận
}
