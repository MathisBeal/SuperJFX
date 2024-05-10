package org.xen.superjfx;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 @author Mathis Béal
 @Date: 12/05/2024
 @Info: Activité du Jukebox
 */
public class JukeboxController implements Initializable {

    final static String SUPER_SMASH_BROS = "musics/SSBU Main Theme.mp3";
    final static String CRABE_RAVE = "musics/Crab Rave.mp3";
    final static String FINAL_FANTASY_VII = "musics/FFVII Main Theme.mp3";
    final static String MORTAL_KOMBAT = "musics/Mortal Kombat Theme.mp3";
    final static String THE_LEGEND_OF_ZELDA = "musics/BOTW.mp3";
    final static String CHAINE_MII = "musics/Mii.mp3";
    final static String THE_LAST_OF_US = "musics/TLOU.mp3";
    final static String MARIO = "musics/Mario.mp3";

    @FXML
    public Circle disque;

    @FXML
    public StackPane disque_global;

    double vitesseRotation = 0;
    final double vitesseRotationMax = 2.5;
    final double vitesseChangementVitesse = 0.01;
    double directionVitesse = 1;

    boolean musiqueEnCours;

    Duration tempsAvantPause = new Duration(0);

    MediaPlayer mediaPlayer;

    /**
     * Fonctionne comme le onUpdate() de Unity
     */
    AnimationTimer animationTimer = new AnimationTimer() {

        /**
         * Donne un effet de vitesse de rotation au disque, en ajoutant de la valeur à l'angle de rotation
         * @param now
         *            The timestamp of the current frame given in nanoseconds. This
         *            value will be the same for all {@code AnimationTimers} called
         *            during one frame.
         */
        @Override
        public void handle(long now) {
            ChangerVitesse();

            ((Rotate) disque_global.getTransforms().getFirst()).setAngle((((Rotate) disque_global.getTransforms().getLast()).getAngle() + vitesseRotation)%360);
        }

    };

    /**
     * Fait augmenter la vitesse de rotation jusqu'à la vitesse maximale, si la direction est positive,
     * Ou fait ralentir la vitesse jusqu'à 0, puis stop le animationTimer
     */
    void ChangerVitesse() {
        vitesseRotation += vitesseChangementVitesse * directionVitesse;
        if (vitesseRotation > vitesseRotationMax)
            vitesseRotation = vitesseRotationMax;

        if (vitesseRotation < 0) {
            vitesseRotation = 0;
            animationTimer.stop();
        }
    }

    /**
     * Permet de démarrer l'animation de tourne-disque depuis du code
     */
    public void DemarrerJukeboxAnim() {
        DemarrerJukeboxAnim(null);
    }

    /**
     * Permet de démarrer l'animation de tourne-disque depuis le FXML
     * @param event Source
     */
    public void DemarrerJukeboxAnim(ActionEvent event) {
        directionVitesse = 1;
        animationTimer.start();
    }

    /**
     * Permet de demander l'arrêt de l'animation de tourne-disque depuis du code
     */
    public void StopJukeboxAnim() {
        StopJukeboxAnim(null);
    }

    /**
     * Permet de demander l'arrêt de l'animation de tourne-disque depuis le FXML
     * @param event Source
     */
    public void StopJukeboxAnim(ActionEvent event) {
        directionVitesse = -1;
        mediaPlayer.stop();
    }

    /**
     * Initialise une rotation avec un certain point de pivot, pour faire l'effet de tourne-disque
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        disque_global.getTransforms().add(new Rotate(0, 330/2, 300/2));

        // Pour ne pas génerer d'erreur au tout début
        musiqueEnCours = true;
    }

    /**
     * Change la musique à jouer
     * @param Nom Nom de la musique à jouer
     */
    void ChangerMusique(String Nom) {

        // Libère les ressources utilisées par le média
        if (mediaPlayer != null)
            mediaPlayer.dispose();

        // Change la ressource media
        mediaPlayer = new MediaPlayer(new Media(getClass().getResource(Nom).toString()));

        // Ajoute du code déclenché à la fin de la musique
        mediaPlayer.setOnEndOfMedia(() -> {
            StopJukeboxAnim();
            musiqueEnCours = false;
        });
    }

    /**
     * Lance la musique ainsi que l'animation de tourne-disque
     */
    void LancerMusique() {
        DemarrerJukeboxAnim();

        if (musiqueEnCours) {
            vitesseRotation = 0;
            ((Rotate) disque_global.getTransforms().getFirst()).setAngle(0);
        }

        directionVitesse = 1;
        mediaPlayer.play();
        musiqueEnCours = true;
    }

    public void LancerCR(ActionEvent event) {
        disque.getStyleClass().clear();
        disque.getStyleClass().add("disque_CR");
        ChangerMusique(CRABE_RAVE);
        LancerMusique();
    }

    public void LancerVII(ActionEvent event) {
        disque.getStyleClass().clear();
        disque.getStyleClass().add("disque_FFVII");
        ChangerMusique(FINAL_FANTASY_VII);
        LancerMusique();
    }

    public void LancerSSBU(ActionEvent event) {
        disque.getStyleClass().clear();
        disque.getStyleClass().add("disque_SSBU");
        ChangerMusique(SUPER_SMASH_BROS);
        LancerMusique();
    }

    public void LancerMK(ActionEvent event) {
        disque.getStyleClass().clear();
        disque.getStyleClass().add("disque_MK");
        ChangerMusique(MORTAL_KOMBAT);
        LancerMusique();
    }

    public void LancerBOTW(ActionEvent event) {
        disque.getStyleClass().clear();
        disque.getStyleClass().add("disque_BOTW");
        ChangerMusique(THE_LEGEND_OF_ZELDA);
        LancerMusique();
    }

    public void LancerMII(ActionEvent event) {
        disque.getStyleClass().clear();
        disque.getStyleClass().add("disque_MII");
        ChangerMusique(CHAINE_MII);
        LancerMusique();
    }

    public void LancerTLOU(ActionEvent event) {
        disque.getStyleClass().clear();
        disque.getStyleClass().add("disque_TLOU");
        ChangerMusique(THE_LAST_OF_US);
        LancerMusique();
    }

    public void LancerMario(ActionEvent event) {
        disque.getStyleClass().clear();
        disque.getStyleClass().add("disque_Mario");
        ChangerMusique(MARIO);
        LancerMusique();
    }

    /**
     * Relance la musique à partir de l'endroit où elle avait été arrêtée<br/>Au début si elle s'était terminée
     * @param event
     */
    public void Lecture(ActionEvent event) {

        if (musiqueEnCours)
            return;

        mediaPlayer.setStartTime(tempsAvantPause);
        mediaPlayer.play();

        DemarrerJukeboxAnim();
        musiqueEnCours = true;
    }

    /**
     * Met la musique en pause et enregistre l'endroit où elle a été arrêtée
     * @param event
     */
    public void Pause(ActionEvent event) {

        if (!musiqueEnCours || mediaPlayer==null)
            return;

        tempsAvantPause = mediaPlayer.getCurrentTime();

        mediaPlayer.pause();
        StopJukeboxAnim();
        musiqueEnCours = false;
    }

    /**
     * Arrête la musique en cours, et remet au début l'endroit elle repartira en cas d'appui sur "Lecture"
     * @param event
     */
    public void Stop(ActionEvent event) {

        if (mediaPlayer==null)
            return;

        mediaPlayer.stop();
        StopJukeboxAnim();
        musiqueEnCours = false;
        tempsAvantPause = new Duration(0);
    }

    /**
     * Gère les actions menu :
     * <li>Menu principal</li>
     * <li>Balles</li>
     * <li>3D</li>
     * et emmène au scène correspondantes
     * @param event Source ayant appelé la fonction
     * @throws IOException
     */
    public void actionMenu(ActionEvent event) throws IOException {
        Stop(null);
        if (mediaPlayer!=null)
            mediaPlayer.dispose();
        switch (((Button) event.getSource()).getText().toLowerCase()) {
            case "menu principal":
                MainApplication.ChangerScene(MainApplication.FXML_Menu, "SuperJFX");
                break;
            case "balles":
                MainApplication.ChangerScene(MainApplication.FXML_Balles, "Balles rebondissantes");
                break;
            case "3d":
                MainApplication.ChangerScene(MainApplication.FXML_3D, "Animation 3D");
                break;
        }
    }
}
