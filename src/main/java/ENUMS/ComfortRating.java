package ENUMS;

public enum ComfortRating {
    ONE_STAR(1),
    TWO_STARS(2),
    THREE_STARS(3),
    FOUR_STARS(4),
    FIVE_STARS(5);

    private final int value;

    ComfortRating(int value) {
        this.value = value;
    }

    public int getRating() {
        return value;
    }
}
