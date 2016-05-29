package ordertracker.util.logger

class DateChangedException extends RuntimeException {

    DateChangedException(String date) {
        super(date)
    }
}
