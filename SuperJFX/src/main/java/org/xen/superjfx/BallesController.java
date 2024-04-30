package org.xen.superjfx;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BallesController {

    @FXML
    private Pane MainPane;

    List<Balle> balleList = new ArrayList<>();

    int prochaine_balle = 0;

    String[] styles_balles = {"balle_pierre", "balle_papier", "balle_ciseau"};

    Random rnd = new Random();

    int randint(int a, int b) {
        return rnd.nextInt(a, b + 1);
    }

    public void Jouer(ActionEvent event) {
        onUpdate.start();
    }

    public void Pause(ActionEvent event) {
        onUpdate.stop();
    }

    final int vitesse_max = 3;

    class Balle {
        int x_mv;
        int y_mv;
        Circle circle;

        Balle() {
            do {
                x_mv = randint(-vitesse_max, vitesse_max);
            } while (x_mv == 0);
            do {
                y_mv = randint(-vitesse_max, vitesse_max);
            } while (y_mv == 0);

            circle = new Circle(25);
            circle.getStyleClass().add(styles_balles[prochaine_balle]);

            MainPane.getChildren().add(circle);

            circle.setLayoutX(randint(25, (int) (MainPane.getWidth() - 25)));
            circle.setLayoutY(randint(25, (int) (MainPane.getHeight() - 25)));

            prochaine_balle = ++prochaine_balle % 3;
        }
    }

    public void ajouterBalle(ActionEvent event) {
        balleList.add(new Balle());
    }

    AnimationTimer onUpdate = new AnimationTimer() {
        @Override
        public void handle(long now) {
            Balle balleActuelle, balleVerif;
            for (int i = 0; i < balleList.size(); i++) {
                balleActuelle = balleList.get(i);

                balleActuelle.circle.setLayoutX(balleActuelle.circle.getLayoutX()+balleActuelle.x_mv);

                if (balleActuelle.circle.getLayoutX() <= 25){
                    balleActuelle.x_mv = -balleActuelle.x_mv;
                    balleActuelle.circle.setLayoutX(25);
                }
                else if (balleActuelle.circle.getLayoutX() >= MainPane.getWidth()-25){
                    balleActuelle.x_mv = -balleActuelle.x_mv;
                    balleActuelle.circle.setLayoutX(MainPane.getWidth()-25);
                }

                balleActuelle.circle.setLayoutY(balleActuelle.circle.getLayoutY()+balleActuelle.y_mv);

                if (balleActuelle.circle.getLayoutY() <= 25){
                    balleActuelle.y_mv = -balleActuelle.y_mv;
                    balleActuelle.circle.setLayoutY(25);
                }
                else if (balleActuelle.circle.getLayoutY() >= MainPane.getHeight()-25){
                    balleActuelle.y_mv = -balleActuelle.y_mv;
                    balleActuelle.circle.setLayoutY(MainPane.getHeight()-25);
                }

                for (int j = i+1; j < balleList.size(); j++) {
                    balleVerif = balleList.get(j);

                    if (balleActuelle.circle.getBoundsInParent().intersects(balleVerif.circle.getBoundsInParent()))
                        LancerShifumi(balleActuelle, balleVerif);
                }

            }
        }
    };

    private void LancerShifumi(Balle balleActuelle, Balle balleVerif) {
//        switch (balleActuelle.circle.getStyleClass().getFirst()){
//            case "balle_pierre":
//                if (balleVerif.circle.getStyleClass().getFirst() == "balle_papier") {
//                    balleActuelle.circle.getStyleClass().clear();
//                    balleActuelle.circle.getStyleClass().clear();
//                }
//
//            case "balle_papier":
//            case "balle_ciseau":
//        }
    }
}
