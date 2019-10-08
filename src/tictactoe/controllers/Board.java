package tictactoe.controllers;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import tictactoe.WinTypes;
import tictactoe.game.MainGame;

import static tictactoe.game.MainGame.*;

public class Board {
    public GridPane board;
    public Label commandLabel;
    public Label infoLabel;
    public Label player1Score;
    public Label player2Score;
    public Button newGameButton;
    public Canvas canvas;

    public final static int BOARD_SIZE = 3;

    private GraphicsContext gc;
    private MainGame game;
    private int clickedFieldX;
    private int clickedFieldY;
    private int gameNumber;

    public void initialize() {
        initializeBoard();
        clickedFieldX = -1;
        clickedFieldY = -1;
        gameNumber = 0;

        gc = canvas.getGraphicsContext2D();
        game = new MainGame(this, gameNumber);
        game.start();
    }

    private void initializeBoard(){
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                ImageView field = new ImageView("tictactoe/images/empty_field.png");
                board.add(field, i, j);
            }
        }
    }

    public void startNewGame() {
        initializeBoard();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        newGameButton.setVisible(false);
        commandLabel.setText("Next move:");
        game = new MainGame(this, gameNumber);
        game.start();
    }

    public void endGame(String tag, WinTypes winType) {
        Platform.runLater(() -> {
            switch (tag) {
                case CROSS:
                    updateScore(player1Score);
                    infoLabel.setText("Player 1 wins!");
                    break;
                case CIRCLE:
                    updateScore(player2Score);
                    infoLabel.setText("Player 2 wins!");
                    break;
                default:
                    infoLabel.setText("DRAW!");
            }

            if (winType != null) {
                gc.setStroke(Color.RED);
                gc.setLineWidth(50);
                gc.strokeLine(winType.getBeginX(), winType.getBeginY(), winType.getEndX(), winType.getEndY());
            }

            commandLabel.setText("GAME OVER");
            newGameButton.setVisible(true);
            gameNumber++;
        });

    }

    public void setClickedField(MouseEvent mouseEvent) {
        clickedFieldX = (int) (mouseEvent.getX() / 200);
        clickedFieldY = (int) (mouseEvent.getY() / 200);

        if (clickedFieldX < BOARD_SIZE && clickedFieldY < BOARD_SIZE && game.isFieldEmpty(clickedFieldX, clickedFieldY)) {
            synchronized (this) {
                notifyAll();
            }
        }
        else {
            clickedFieldX = -1;
            clickedFieldY = -1;
        }
    }

    public int getClickedFieldX() {
        int x = clickedFieldX;
        clickedFieldX = -1;

        return x;
    }

    public int getClickedFieldY() {
        int y = clickedFieldY;
        clickedFieldY = -1;

        return y;
    }

    public void setFieldImage(String tag, int x, int y) {
        Platform.runLater(() -> {
            ImageView fieldIcon = new ImageView("tictactoe/images/" + tag + "_field.png");
            board.add(fieldIcon, x, y);
        });

    }

    public void setInfoLabel(String tag) {
        Platform.runLater(() -> {
            switch (tag) {
                case CROSS:
                    infoLabel.setText("Player 1 (Cross)");
                    break;
                case CIRCLE:
                    infoLabel.setText("Player 2 (Circle)");
                    break;
            }
        });

    }

    private void updateScore(Label playerScore) {
        int score = Integer.parseInt(playerScore.getText());
        playerScore.setText(String.valueOf(++score));
    }

    public void close() {
        if (game.isAlive()) {
            synchronized (this) {
                notifyAll();
            }
            game.end();
        }
    }
}