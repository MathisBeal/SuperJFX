package org.xen.superjfx;


import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Cylinder;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class D3Controller implements Initializable {

    public AnchorPane pane;
    Node objet;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        objet = new Cylinder(250, 100);

        pane.getChildren().add(objet);

        objet.setLayoutX(450);
        objet.setLayoutY(300);

        RotateTransition rotateTransition1 = new RotateTransition();

        rotateTransition1.setFromAngle(0);
        rotateTransition1.setToAngle(180);
        rotateTransition1.setAxis(new Point3D(1, 0, 0));
        rotateTransition1.setDuration(Duration.seconds(1));
        rotateTransition1.setNode(objet);

        RotateTransition rotateTransition2 = new RotateTransition();

        rotateTransition2.setFromAngle(180);
        rotateTransition2.setToAngle(360);
        rotateTransition2.setAxis(new Point3D(1, 0, 0));
        rotateTransition2.setDuration(Duration.seconds(5));
        rotateTransition2.setNode(objet);

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(
                rotateTransition1,
                rotateTransition2
        );
        sequentialTransition.setCycleCount(Timeline.INDEFINITE);
        sequentialTransition.setAutoReverse(true);

        sequentialTransition.play();
    }
}
