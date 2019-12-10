package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class Controller {
    public BorderPane SpielfeldAnzeige;
    public BorderPane StartMenu;
    public Label Titelname;
    public TextField Spielfeldgroesse;

    public void startMenu(MouseEvent mouseEvent){
       //String test = mouseEvent.getPickResult().getIntersectedNode().getId();

    }

    public void NewGame(MouseEvent mouseEvent) {
        StartMenu.setDisable(true);
        StartMenu.setVisible(false);
        SpielfeldAnzeige.setDisable(false);
        SpielfeldAnzeige.setVisible(true);
    }

    public void SpielfeldAnlegen(ActionEvent actionEvent) {
        SpielfeldAnzeige.getChildren().removeAll();
        GridPane spielfeld = new GridPane();
        spielfeld.setGridLinesVisible(true);
        final int num = Integer.parseInt(Spielfeldgroesse.getText());
        for (int i = 0; i < num; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / num);
            spielfeld.getColumnConstraints().add(colConst);
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / num);
            spielfeld.getRowConstraints().add(rowConst);
        }
        spielfeld.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        SpielfeldAnzeige.setCenter(spielfeld);
    }
}
