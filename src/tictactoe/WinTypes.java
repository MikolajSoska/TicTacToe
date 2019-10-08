package tictactoe;

public enum WinTypes {
    FIRST_COLUMN(100, 550, 100, 50),
    SECOND_COLUMN(300, 550, 300, 50),
    THIRD_COLUMN(500, 550, 500, 50),

    FIRST_ROW(50, 100, 550, 100),
    SECOND_ROW(50, 300, 550, 300),
    THIRD_ROW(50, 500, 550, 500),

    CROSS_RIGHT_TO_LEFT(50, 550, 550, 50),
    CROSS_LEFT_TO_RIGHT(550, 550, 50, 50);

    private final int beginX;
    private final int beginY;
    private final int endX;
    private final int endY;

    WinTypes(int beginX, int beginY, int endX, int endY) {
        this.beginX = beginX;
        this.beginY = beginY;
        this.endX = endX;
        this.endY = endY;
    }

    public int getBeginX() {
        return beginX;
    }

    public int getBeginY() {
        return beginY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }
}
