package Main;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

import DataProccesing.DrawPolygon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView prevImage;

    @FXML
    private TextField polygonInput;

    @FXML
    private Button clearButton;

    @FXML
    private Button getButton;

    @FXML
    private Button undoButton;

    @FXML
    private Text sizePolygon;

    @FXML
    private TextField mousePosition;

    @FXML
    void initialize() {
        assert prevImage != null : "fx:id=\"prevImage\" was not injected: check your FXML file 'Untitled'.";
        assert polygonInput != null : "fx:id=\"polygonInput\" was not injected: check your FXML file 'Untitled'.";
        assert clearButton != null : "fx:id=\"clearButton\" was not injected: check your FXML file 'Untitled'.";
        assert getButton != null : "fx:id=\"getButton\" was not injected: check your FXML file 'Untitled'.";
        assert undoButton != null : "fx:id=\"undoButton\" was not injected: check your FXML file 'Untitled'.";
        assert sizePolygon != null : "fx:id=\"sizePolygon\" was not injected: check your FXML file 'Untitled'.";
        assert mousePosition != null : "fx:id=\"mousePosition\" was not injected: check your FXML file 'main.fxml'.";

        DrawPolygon drawPolygon = new DrawPolygon(prevImage,sizePolygon);

        polygonInput.setOnAction(value -> {
            drawPolygon.drawPolygon(polygonInput.getText());
            polygonInput.setText("");
        });

        clearButton.setOnAction(value -> {
            drawPolygon.resetPolygon();
        });

        getButton.setOnAction(value -> {
            drawPolygon.getProgramCode();
        });

        anchorPane.setOnMouseReleased(value -> drawPolygon.drawPolygon((int)value.getX()+","+(int)value.getY()));

        anchorPane.setOnMouseMoved(value -> {
            mousePosition.setText((int)value.getX()+","+(int)value.getY());
            mousePosition.setLayoutX((value.getX()+12 > 650) ? value.getX()-60 : value.getX() + 12);
            mousePosition.setLayoutY((value.getY()+16 > 675) ? value.getY()-23 : value.getY() + 16);
        });

        anchorPane.setOnMouseExited(value -> {
            mousePosition.setVisible(false);
        });
        anchorPane.setOnMouseEntered(value -> {
            mousePosition.setVisible(true);
        });

    }
}
