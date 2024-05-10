package org.xen.superjfx;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 @author Mathis Béal
 @Date: 12/05/2024
 @Info: Activité des balles rebondissantes
 */
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

    /**
     * Ajoute 3 balles
     * @param event
     */
    public void ajouter3Balles(ActionEvent event) {
        ajouterBalle(null);
        ajouterBalle(null);
        ajouterBalle(null);
    }

    /**
     * Retire toutes les balles
     * @param event
     */
    public void retirerToutesLesBalles(ActionEvent event) {
        MainPane.getChildren().clear();
        balleList.clear();
    }

    /**
     * Met le jeu en pause
     * @param mouseEvent
     */
    public void PausePane(MouseEvent mouseEvent) {
        onUpdate.stop();
    }

    /**
     * Remet le jeu en lecture
     * @param mouseEvent
     */
    public void JouerPane(MouseEvent mouseEvent) {
        onUpdate.start();
    }

    /**
     * Lance le jeu dès le lancement de l'activité
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onUpdate.start();
    }

    /**
     * Classe servant à stocker l'objet visuel qui sert de balle et les données utiles au déroulement du jeu
     */
    class Balle {
        int x_mouvement;
        int y_mouvement;
        Circle cercle;

        /**
         * Crée une balle, et instancie une vitesse/direction aléatoire<br>
         * La fait afficher et change le type de la prochaine balle
         */
        Balle() {
            do {
                x_mouvement = randint(-vitesse_max, vitesse_max);
            } while (x_mouvement == 0);
            do {
                y_mouvement = randint(-vitesse_max, vitesse_max);
            } while (y_mouvement == 0);

            cercle = new Circle(25);
            cercle.getStyleClass().add(styles_balles[prochaine_balle]);
            cercle.setMouseTransparent(true);

            MainPane.getChildren().add(cercle);

            cercle.setLayoutX(randint(25, (int) (MainPane.getWidth() - 25)));
            cercle.setLayoutY(randint(25, (int) (MainPane.getHeight() - 25)));

            prochaine_balle = ++prochaine_balle % 3;
        }
    }

    /**
     * Ajoute une balle
     * @param event
     */
    public void ajouterBalle(ActionEvent event) {
        balleList.add(new Balle());
    }

    /**
     * Fonctionne presque comme la fonction onUpdate() de Unity<br/>
     * Fait les déplacements des balles, puis le jeu entre les balles en collisions
     */
    AnimationTimer onUpdate = new AnimationTimer() {
        @Override
        public void handle(long now) {
            Balle balleActuelle, balleVerif;
            for (int i = 0; i < balleList.size(); i++) {
                balleActuelle = balleList.get(i);

                balleActuelle.cercle.setLayoutX(balleActuelle.cercle.getLayoutX()+balleActuelle.x_mouvement);

                if (balleActuelle.cercle.getLayoutX() <= 25){
                    balleActuelle.x_mouvement = -balleActuelle.x_mouvement;
                    balleActuelle.cercle.setLayoutX(25);
                }
                else if (balleActuelle.cercle.getLayoutX() >= MainPane.getWidth()-25){
                    balleActuelle.x_mouvement = -balleActuelle.x_mouvement;
                    balleActuelle.cercle.setLayoutX(MainPane.getWidth()-25);
                }

                balleActuelle.cercle.setLayoutY(balleActuelle.cercle.getLayoutY()+balleActuelle.y_mouvement);

                if (balleActuelle.cercle.getLayoutY() <= 25){
                    balleActuelle.y_mouvement = -balleActuelle.y_mouvement;
                    balleActuelle.cercle.setLayoutY(25);
                }
                else if (balleActuelle.cercle.getLayoutY() >= MainPane.getHeight()-25){
                    balleActuelle.y_mouvement = -balleActuelle.y_mouvement;
                    balleActuelle.cercle.setLayoutY(MainPane.getHeight()-25);
                }

                for (int j = i+1; j < balleList.size(); j++) {
                    balleVerif = balleList.get(j);

                    if (balleActuelle.cercle.getBoundsInParent().intersects(balleVerif.cercle.getBoundsInParent())) {
                        LancerShifumi(balleActuelle, balleVerif);
                    }
                }

            }
        }
    };

    /**
     * Change le style de classe CSS du Node n
     * @param n Node auquel changer le style
     * @param styleClasse Nom de la classe à appliquer
     */
    private void ChangerStyleClasse(Node n, String styleClasse){
        n.getStyleClass().clear();
        n.getStyleClass().add(styleClasse);
    }

    /**
     * Fait le jeu du shifumi entre 2 balles
     * @param balleActuelle
     * @param balleVerif
     */
    private void LancerShifumi(Balle balleActuelle, Balle balleVerif) {
        switch (balleActuelle.cercle.getStyleClass().getFirst()){
            case BALLE_PIERRE:
                if (balleVerif.cercle.getStyleClass().getFirst() == BALLE_PAPIER) {
                    ChangerStyleClasse(balleActuelle.cercle, BALLE_PAPIER);
                }
                else if (balleVerif.cercle.getStyleClass().getFirst() == BALLE_CISEAU) {
                    ChangerStyleClasse(balleVerif.cercle, BALLE_PIERRE);
                }
                break;

            case BALLE_PAPIER:
                if (balleVerif.cercle.getStyleClass().getFirst() == BALLE_CISEAU) {
                    ChangerStyleClasse(balleActuelle.cercle, BALLE_CISEAU);
                }
                else if (balleVerif.cercle.getStyleClass().getFirst() == BALLE_PIERRE) {
                    ChangerStyleClasse(balleVerif.cercle, BALLE_PAPIER);
                }
                break;

            case BALLE_CISEAU:
                if (balleVerif.cercle.getStyleClass().getFirst() == BALLE_PIERRE) {
                    ChangerStyleClasse(balleActuelle.cercle, BALLE_PIERRE);
                }
                else if (balleVerif.cercle.getStyleClass().getFirst() == BALLE_PAPIER) {
                    ChangerStyleClasse(balleVerif.cercle, BALLE_CISEAU);
                }
                break;
        }
    }

    /**
     * Retire la dernière balle ajoutée
     */
    @FXML
    void retirerDerniereBalle() {

        if (balleList.isEmpty())
            return;

        MainPane.getChildren().removeLast();
        balleList.removeLast();
    }
}
