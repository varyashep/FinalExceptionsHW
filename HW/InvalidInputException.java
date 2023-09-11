package HW;

public class InvalidInputException extends Exception { // Класс исключения для входных данных неверного размера
    
    private int size;
    public int getSize() {
        return size;
    }
    public InvalidInputException(String message, int dataSize) {
        super(message);
        size = dataSize;
    }
}