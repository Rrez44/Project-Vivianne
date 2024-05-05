package ENUMS;

public enum ComfortRating {
    ONE_STAR(1),
    TWO_STARS(2),
    THREE_STARS(3),
    FOUR_STARS(4),
    FIVE_STARS(5);

    private final int value;

    public static ComfortRating fromInt(int num) {
        switch (num) {
            case 1:
                return ONE_STAR;
            case 2:
                return TWO_STARS;
            case 3:
                return THREE_STARS;
            case 4:
                return FOUR_STARS;
            case 5:
                return FIVE_STARS;
            default:
                throw new IllegalArgumentException("Invalid rating: " + num);
        }
    }

    ComfortRating(int value) {
        this.value = value;
    }

    public int getRating() {
        return value;
    }
}
