package ordertracker.database

class OutOfStockException extends DatabaseException{
    OutOfStockException(String message) {
        super(message)
    }
}
