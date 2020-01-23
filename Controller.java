package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class Controller {
    public BorderPane StartMenu;
    public BorderPane Menu;
    public GridPane MenuGridPane;
    public RadioButton Host;
    public RadioButton Client;
    public TextField IPAdresse;
    public TextField Spielfeldgroesse;
    public BorderPane SpielfeldAnzeige;
    public GridPane Spielfeld;
    public String IP; //<--- hier wird IP und Spielfeldgroesse gespeichert
    public int SPGroesse;

    public void startMenu(MouseEvent mouseEvent){
       //String test = mouseEvent.getPickResult().getIntersectedNode().getId();

    }

    public void NewGame(MouseEvent mouseEvent) {
        StartMenu.setDisable(true);
        StartMenu.setVisible(false);
        Menu.setDisable(false);
        Menu.setVisible(true);
        IPAdresse.appendText("IP");
        //SpielfeldAnzeige.setDisable(false);
        //SpielfeldAnzeige.setVisible(true);
    }
    public void ZurueckAnfang(MouseEvent mouseEvent) {
        IPAdresse.clear();
        Spielfeldgroesse.clear();
        Menu.setDisable(true);
        Menu.setVisible(false);
        StartMenu.setDisable(false);
        StartMenu.setVisible(true);
    }
    public void WeiterSpielfeld(MouseEvent mouseEvent) {
        IP = IPAdresse.getText();
        SPGroesse = Integer.parseInt(Spielfeldgroesse.getText());
        IPAdresse.clear();
        Spielfeldgroesse.clear();
        Menu.setDisable(true);
        Menu.setVisible(false);
        SpielfeldAnzeige.setDisable(false);
        SpielfeldAnzeige.setVisible(true);
        Spielfeld.getChildren().removeAll();
        GridPane playerfield = new GridPane();
        GridPane enemyfield = new GridPane();
        Spielfeld.add(Spielfelderstellen(playerfield),0,0);
        Spielfeld.add(Spielfelderstellen(enemyfield),2,0);
    }
    public void ZurueckSpielfeld(MouseEvent mouseEvent) {
        SpielfeldAnzeige.setDisable(true);
        SpielfeldAnzeige.setVisible(false);
        StartMenu.setDisable(false);
        StartMenu.setVisible(true);
        System.out.println("ok");
    }
    public GridPane Spielfelderstellen(GridPane Feld)
    {
        Feld.setGridLinesVisible(true);
        final int num = SPGroesse;
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
    public void HostInit(MouseEvent mouseEvent) { //Max hier die IP eintragen
        Client.setSelected(false);
        IPAdresse.setEditable(false);
        IPAdresse.appendText("IP"); //<---
        Host.setDisable(true);
        Client.setDisable(false);
    }
    public void ClientInit(MouseEvent mouseEvent) {
        Host.setSelected(false);
        IPAdresse.setEditable(true);
        IPAdresse.clear();
        Client.setDisable(true);
        Host.setDisable(false);
    }
}
