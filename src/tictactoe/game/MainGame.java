package tictactoe.game;

import tictactoe.WinTypes;
import tictactoe.controllers.Board;
import static tictactoe.controllers.Board.BOARD_SIZE;

public class MainGame extends Thread {
    public final static String CIRCLE = "circle";
    public final static String CROSS = "cross";

    private final Board board;
    private Player player1;
    private Player player2;
    private boolean[][] boardField;
    private boolean endGame;
    private boolean skip;
    private int gameNumber;


    public MainGame(Board board, int gameNumber) {
        this.board = board;

        player1 = new Player(CROSS);
        player2 = new Player(CIRCLE);

        boardField = new boolean[BOARD_SIZE][BOARD_SIZE];
        endGame = false;
        skip = false;

        this.gameNumber = gameNumber;
    }

    private void playerMove(Player player) {
        try {
            synchronized (board) {
                String tag = player.getTag();

                board.setInfoLabel(tag);
                board.wait();

                int x = board.getClickedFieldX();
                int y = board.getClickedFieldY();

                if (x != -1 && y != -1) {
                    boardField[y][x] = true;
                    board.setFieldImage(tag, x, y);
                    player.setField(x, y);
                    WinTypes winType = player.checkIfWin();
                    if (winType != null) {
                        endGame = true;
                        board.endGame(tag, winType);
                    }
                    else if (isBoardFull()) {
                        endGame = true;
                        board.endGame("draw",null);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isBoardFull() {
        for (boolean[] row : boardField)
            for (boolean field : row)
                if (!field)
                    return false;
        return true;
    }

    public boolean isFieldEmpty(int x, int y) {
        return !boardField[y][x];
    }

    public void end() {
        endGame = true;
    }

    @Override
    public void run() {
        skip = (++gameNumber % 2) == 0;

        while (!endGame) {
            if (skip)
                skip = false;
            else
                playerMove(player1);

            if (endGame)
                break;

            playerMove(player2);
        }
    }
}
