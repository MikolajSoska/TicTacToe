package tictactoe.game;

import tictactoe.WinTypes;

import static tictactoe.controllers.Board.BOARD_SIZE;

public class Player {
    private boolean[][] fields;
    private String tag;

    public Player(String tag) {
        this.tag = tag;
        fields = new boolean[BOARD_SIZE][BOARD_SIZE];
    }

    public void setField(int x, int y) {
        fields[y][x] = true;
    }

    public String getTag() {
        return tag;
    }

    public WinTypes checkIfWin() {
        int counter = 0;

        for(int i = 0; i < BOARD_SIZE; i++, counter = 0) {
            for (int j = 0; j < BOARD_SIZE; j++)
                if (fields[i][j])
                    counter++;
            if (counter == 3) {
                if (i == 0)
                    return WinTypes.FIRST_ROW;
                else if (i == 1)
                    return WinTypes.SECOND_ROW;
                else
                    return WinTypes.THIRD_ROW;
            }
        }

        for(int i = 0; i < BOARD_SIZE; i++, counter = 0) {
            for (int j = 0; j < BOARD_SIZE; j++)
                if (fields[j][i])
                    counter++;
            if (counter == 3) {
                if (i == 0)
                    return WinTypes.FIRST_COLUMN;
                else if (i == 1)
                    return WinTypes.SECOND_COLUMN;
                else
                    return WinTypes.THIRD_COLUMN;
            }
        }

        for (int i = 0; i < BOARD_SIZE; i++)
            if (fields[i][i])
                counter++;
        if (counter == BOARD_SIZE)
            return WinTypes.CROSS_LEFT_TO_RIGHT;

        counter = 0;
        for (int i = 0, j = BOARD_SIZE - 1; i < BOARD_SIZE; i++, j--)
            if (fields[i][j])
                counter++;

        if (counter == 3)
            return WinTypes.CROSS_RIGHT_TO_LEFT;
        else
            return null;
    }
}
