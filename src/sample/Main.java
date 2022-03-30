package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    final static Character[][] matrix1 = {{'E','P','S'},
            {'D','U','C'},
            {'V','W','Y'}};
    final static Character[][] matrix2 = {{'M','.','Z'},
            {'L','K','X'},
            {'N','B','T'}};
    final static Character[][] matrix3 = {{'F','G','O'},
            {'R','I','J'},
            {'H','A','Q'}};

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
