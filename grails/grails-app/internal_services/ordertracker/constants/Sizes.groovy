package ordertracker.constants
enum Sizes {

    MAX_IMAGE_WIDTH(360),
    MAX_IMAGE_HEIGHT(360),

    private final int value

    private Sizes(int value) {
        this.value = value
    }

    int getInt() {
        return this.value
    }

}