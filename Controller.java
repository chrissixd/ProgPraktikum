package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
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
    public MenuButton Schwierigkeitswahl;
    public MenuButton Spielartwahl;
    public GridPane Spielfeld;
    public String IP; //<--- hier wird IP und Spielfeldgroesse gespeichert
    public int SPGroesse; // Spielfeldgroesse 5 - 30;
    public String Spielart; // Spielart kann sein "PvP" "PvKI" oder "KIvKI"
    public String Schwierigkeit; // Schwierigkeit kann sein "Easy" oder "Hard"
    public String Schiffauswahl; // Schiffauswahl ist das Schiff das man setzen möchte

    public void startMenu(MouseEvent mouseEvent){
       //String test = mouseEvent.getPickResult().getIntersectedNode().getId();
    }

    public void NewGame(MouseEvent mouseEvent) {
        StartMenu.setDisable(true);
        StartMenu.setVisible(false);
        Menu.setDisable(false);
        Menu.setVisible(true);
        IPAdresse.appendText("IP");
        Spielart = "PvP";
        Schwierigkeit = null;
        Schwierigkeitswahl.setDisable(true);
        //SpielfeldAnzeige.setDisable(false);
        //SpielfeldAnzeige.setVisible(true);
    } //Neues Spiel starten
    public void ZurueckAnfang(MouseEvent mouseEvent) {
        IPAdresse.clear();
        Spielfeldgroesse.clear();
        Menu.setDisable(true);
        Menu.setVisible(false);
        StartMenu.setDisable(false);
        StartMenu.setVisible(true);
    } // Vom Menu zum Start zurueck
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
    } // Vom Menu zum Spielfeld
    public void ZurueckSpielfeld(MouseEvent mouseEvent) {
        SpielfeldAnzeige.setDisable(true);
        SpielfeldAnzeige.setVisible(false);
        StartMenu.setDisable(false);
        StartMenu.setVisible(true);
        System.out.println("ok");
    } // Vom Spielfeld zurueck zum Anfang
    public GridPane Spielfelderstellen(GridPane Feld) // Neues Spielfeld erstellen Max hier musst du reingehen
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
                        if(Schiffauswahl!= null) // Sobald man auf ein Spielfeld drückt wird diese Funktion aufgerufen also Pack den Code für die Schiffsplatzierung hier rein außerdem müsste man noch abfragen wie viele Schiffe man noch legen darf
                        {
                            String Feld = mouseEvent.getPickResult().getIntersectedNode().getId(); // Hier steht die Koordinate vom gedrückten Feld drinnen
                            System.out.println(Feld);
                            //mouseEvent.getPickResult().getIntersectedNode().setStyle("-fx-background-color: #c0c0c0;");
                        }
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
        IPAdresse.clear();
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

    public void Schiffwahl(MouseEvent mouseEvent) {
        Schiffauswahl = mouseEvent.getPickResult().getIntersectedNode().getId();
    } // Hier wählt man das Schiff aus das man legen möchte;
    public void Spielartauswahl(ActionEvent actionEvent) {
        Spielart = ((MenuItem)actionEvent.getSource()).getId();
        Schwierigkeitswahl.setDisable(true);
        IPAdresse.setDisable(false);
        if(Spielart.equals("PvKI") || Spielart.equals("KIvKI"))
        {
            Schwierigkeitswahl.setDisable(false);
            IPAdresse.setDisable(true);
        }
    }
    public void Schwierigkeitauswahl(ActionEvent actionEvent) {
        Schwierigkeit = ((MenuItem)actionEvent.getSource()).getId();
        System.out.println(Schwierigkeit);
    }

    public void RandomSchiffeLegen(MouseEvent mouseEvent) { // Für Aaron wollte der
    }

    public void SchiffeLegenFertig(MouseEvent mouseEvent) { // Was passieren soll wenn alle Schiffe gelegt worden sind
    }
}
