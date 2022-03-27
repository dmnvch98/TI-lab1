package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    FileChooser fileChooser = new FileChooser();

    @FXML
    private Text path;

    @FXML
    private TextArea textArea;

    public void setTextArea(String text) {
        this.textArea.appendText(text);
    }

    @FXML
    void getText(MouseEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());
        path.setText(file.getPath());
    }

    @FXML
    void encrypt(MouseEvent event) throws FileNotFoundException {
        new Encrypt(path.getText()).encrypt(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileChooser.setInitialDirectory(new File("D:\\"));
    }
}
