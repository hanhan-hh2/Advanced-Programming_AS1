public class PositionNotFoundException extends Exception {
    // Hiện tại chỉ cần 1 message là ổn
    public PositionNotFoundException(String msg) {
        super(msg);
    }
}
