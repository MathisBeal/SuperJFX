package org.xen.superjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

import static javafx.application.Platform.exit;

public class MenuController {

    @FXML
    void Animations(ActionEvent event) throws IOException {
//        System.out.println("Go to Animations");
        MainApplication.ChangerScene(MainApplication.FXML_Animations, "Animations");
    }

    @FXML
    void BallesRebondissantes(ActionEvent event) throws IOException {
//        System.out.println("Go to BallesRebondissantes");
        MainApplication.ChangerScene(MainApplication.FXML_Balles, "Balles rebondissantes");
    }

    @FXML
    void D3(ActionEvent event) throws IOException {
//        System.out.println("Go to D3");
        MainApplication.ChangerScene(MainApplication.FXML_3D, "Animation 3D");
    }

    @FXML
    void Quitter(ActionEvent event) {
        System.out.println("Quitter l'application");
        exit();
    }

}
