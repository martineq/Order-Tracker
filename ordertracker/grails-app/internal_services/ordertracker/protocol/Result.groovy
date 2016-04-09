package ordertracker.protocol

enum Result {
    OK("ok"),
    ERROR("error")

    private final String value

    private Result(String value) {
        this.value = value
    }

    @Override
    String toString() {
        return this.value
    }

    static String getEnumName() {
        return "result"
    }

    static Result defineResult(boolean result) {
        return ( result == true ) ? Result.OK : Result.ERROR
    }
}