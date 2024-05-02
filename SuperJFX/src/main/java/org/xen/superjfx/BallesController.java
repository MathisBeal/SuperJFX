package org.xen.superjfx;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class BallesController implements Initializable {

    public static final String BALLE_PIERRE = "balle_pierre";
    public static final String BALLE_PAPIER = "balle_papier";
    public static final String BALLE_CISEAU = "balle_ciseau";
    @FXML
    private Pane MainPane;

    List<Balle> balleList = new ArrayList<>();

    int prochaine_balle = 0;

    final String[] styles_balles = {BALLE_PIERRE, BALLE_PAPIER, BALLE_CISEAU};

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

    public void ajouter3Balles(ActionEvent event) {
        ajouterBalle(null);
        ajouterBalle(null);
        ajouterBalle(null);
    }

    public void retirerToutesLesBalles(ActionEvent event) {
        MainPane.getChildren().clear();
        balleList.clear();
    }

    public void PausePane(MouseEvent mouseEvent) {
        onUpdate.stop();
    }

    public void JouerPane(MouseEvent mouseEvent) {
        onUpdate.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onUpdate.start();
    }

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
            circle.setMouseTransparent(true);

            MainPane.getChildren().add(circle);

            circle.setLayoutX(randint(25, (int) (MainPane.getWidth() - 25)));
            circle.setLayoutY(randint(25, (int) (MainPane.getHeight() - 25)));

            prochaine_balle = ++prochaine_balle % 3;
        }
    }

    public void ajouterBalle(ActionEvent event) {
        balleList.add(new Balle());
    }

    /**
     * Fonctionne presque comme la fonction onUpdate() de Unity
     */
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

                    if (balleActuelle.circle.getBoundsInParent().intersects(balleVerif.circle.getBoundsInParent())) {
                        LancerShifumi(balleActuelle, balleVerif);
                    }
                }

            }
        }
    };

    private void ChangerStyleClasse(Node n, String styleClasse){
        n.getStyleClass().clear();
        n.getStyleClass().add(styleClasse);
    }

    private void LancerShifumi(Balle balleActuelle, Balle balleVerif) {
        switch (balleActuelle.circle.getStyleClass().getFirst()){
            case BALLE_PIERRE:
                if (balleVerif.circle.getStyleClass().getFirst() == BALLE_PAPIER) {
                    ChangerStyleClasse(balleActuelle.circle, BALLE_PAPIER);
                }
                else if (balleVerif.circle.getStyleClass().getFirst() == BALLE_CISEAU) {
                    ChangerStyleClasse(balleVerif.circle, BALLE_PIERRE);
                }
                break;

            case BALLE_PAPIER:
                if (balleVerif.circle.getStyleClass().getFirst() == BALLE_CISEAU) {
                    ChangerStyleClasse(balleActuelle.circle, BALLE_CISEAU);
                }
                else if (balleVerif.circle.getStyleClass().getFirst() == BALLE_PIERRE) {
                    ChangerStyleClasse(balleVerif.circle, BALLE_PAPIER);
                }
                break;

            case BALLE_CISEAU:
                if (balleVerif.circle.getStyleClass().getFirst() == BALLE_PIERRE) {
                    ChangerStyleClasse(balleActuelle.circle, BALLE_PIERRE);
                }
                else if (balleVerif.circle.getStyleClass().getFirst() == BALLE_PAPIER) {
                    ChangerStyleClasse(balleVerif.circle, BALLE_CISEAU);
                }
                break;
        }
    }

    @FXML
    void retirerDerniereBalle() {

        if (balleList.isEmpty())
            return;

        MainPane.getChildren().removeLast();
        balleList.removeLast();
    }
}
