package org.xen.superjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

import static javafx.application.Platform.exit;

/**
 @author Mathis Béal
 @Date: 12/05/2024
 @Info: Activité du menu principal
 */
public class MenuController {

    @FXML
    void Animations(ActionEvent event) throws IOException {
        MainApplication.ChangerScene(MainApplication.FXML_Animations, "Animations");
    }

    @FXML
    void BallesRebondissantes(ActionEvent event) throws IOException {
        MainApplication.ChangerScene(MainApplication.FXML_Balles, "Balles rebondissantes");
    }

    @FXML
    void D3(ActionEvent event) throws IOException {
        MainApplication.ChangerScene(MainApplication.FXML_3D, "Animation 3D");
    }

    @FXML
    void Quitter(ActionEvent event) {
        System.out.println("Quitter l'application");
        exit();
    }
}
