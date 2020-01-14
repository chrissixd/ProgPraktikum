package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class Controller {
    public BorderPane StartMenu;
    public Label Titelname;
    public BorderPane SpielfeldAnzeige;
    public GridPane Spielfeld;
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

    public void ZurückSpielfeld(MouseEvent mouseEvent) {
        SpielfeldAnzeige.setDisable(true);
        SpielfeldAnzeige.setVisible(false);
        StartMenu.setDisable(false);
        StartMenu.setVisible(true);
    }
    public GridPane Spielfelderstellen(GridPane Feld)
    {
        Feld.setGridLinesVisible(true);

        final int num = Integer.parseInt(Spielfeldgroesse.getText());
        for (int i = 0; i < num; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / num);
            Feld.getColumnConstraints().add(colConst);
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / num);
            Feld.getRowConstraints().add(rowConst);
            for(int x = 0; x < num; x++)
            {
                Button knopf = new Button();
                knopf.setId(x + " " + i);
                knopf.setPrefSize(Double.MAX_VALUE,Double.MAX_VALUE);
                knopf.setOpacity(0.25);
                knopf.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println(mouseEvent.getPickResult().getIntersectedNode().getId());
                    }
                });
                Feld.add(knopf,i,x);
            }
        }
        Feld.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        return Feld;
    }
    public void SpielfeldAnlegen(ActionEvent actionEvent) {
        Spielfeld.getChildren().removeAll();
        GridPane playerfield = new GridPane();
        GridPane enemyfield = new GridPane();
        Spielfeld.add(Spielfelderstellen(playerfield),1,1);
        Spielfeld.add(Spielfelderstellen(enemyfield),3,1);
    }

}
