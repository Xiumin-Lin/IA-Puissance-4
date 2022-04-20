package gui;

import game.Piece;
import game.Puissance4;
import ia.Niveau;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import player.Computer;
import player.Human;
import player.Player;

public class GameGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // init le jeu
        Player p1 = new Human("Joker", Piece.ROUGE);
        Player p2 = new Computer("AI", Piece.JAUNE, Niveau.FORT);
        Puissance4 game = new Puissance4(p1, p2, true); // TODO ajouter dans menu
        // Affiche le jeu
        stage.setTitle("Puissance 4");
        Puissance4Pane gui = new Puissance4Pane(game);
        Scene scene = new Scene(gui); // TODO CREER MENU
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
