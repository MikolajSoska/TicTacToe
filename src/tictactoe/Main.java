package tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tictactoe.controllers.Board;

public class Main extends Application {
    private Board controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/board.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() {
        controller.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
