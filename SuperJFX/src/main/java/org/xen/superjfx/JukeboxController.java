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

    double rotateSpeed = 0;
    final double maxRotateSpeed = 2;
    final double speedChangeSpeed = 0.01;
    double speedDirection = 1;

    boolean musiqueEnCours;

    Duration tempsAvantPause = new Duration(0);

    MediaPlayer mediaPlayer;

    AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            ChangeSpeed();

            ((Rotate) disque_global.getTransforms().getFirst()).setAngle((((Rotate) disque_global.getTransforms().getLast()).getAngle() + rotateSpeed)%360);
        }

    };

    void ChangeSpeed() {
        rotateSpeed += speedChangeSpeed * speedDirection;
        if (rotateSpeed > maxRotateSpeed)
            rotateSpeed = maxRotateSpeed;

        if (rotateSpeed < 0) {
            rotateSpeed = 0;
            animationTimer.stop();
        }
    }

    public void startJukeboxAnim() {
        startJukeboxAnim(null);
    }

    public void startJukeboxAnim(ActionEvent event) {
        speedDirection = 1;
        animationTimer.start();
    }

    public void stopJukeboxAnim() {
        stopJukeboxAnim(null);
    }

    public void stopJukeboxAnim(ActionEvent event) {
        speedDirection = -1;
        mediaPlayer.stop();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        disque_global.getTransforms().add(new Rotate(0, 330/2, 300/2));

        musiqueEnCours = true;
    }





    void ChangerMusique(String Nom) {

        if (mediaPlayer != null)
            mediaPlayer.dispose();

        mediaPlayer = new MediaPlayer(new Media(getClass().getResource(Nom).toString()));

        mediaPlayer.setOnEndOfMedia(() -> {
            stopJukeboxAnim();
            mediaPlayer.dispose();
            musiqueEnCours = false;
        });
    }

    void LancerMusique() {
        startJukeboxAnim();

        if (musiqueEnCours) {
            rotateSpeed = 0;
            ((Rotate) disque_global.getTransforms().getFirst()).setAngle(0);
        }

        speedDirection = 1;
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

    public void Lecture(ActionEvent event) {

        if (musiqueEnCours)
            return;

        mediaPlayer.setStartTime(tempsAvantPause);
        mediaPlayer.play();

        startJukeboxAnim();
        musiqueEnCours = true;
    }

    public void Pause(ActionEvent event) {

        if (!musiqueEnCours || mediaPlayer==null)
            return;

        tempsAvantPause = mediaPlayer.getCurrentTime();

        mediaPlayer.pause();
        stopJukeboxAnim();
        musiqueEnCours = false;
    }

    public void Stop(ActionEvent event) {

        if (mediaPlayer==null)
            return;

        mediaPlayer.stop();
        stopJukeboxAnim();
        musiqueEnCours = false;
        tempsAvantPause = new Duration(0);
    }

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
