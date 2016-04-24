package ordertracker.database

class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message)
    }
}
