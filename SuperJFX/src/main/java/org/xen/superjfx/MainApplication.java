package org.xen.superjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    public static final String FXML_Menu = "Menu.fxml";
    public static final String FXML_Balles = "Balles.fxml";
    public static final String FXML_Animations = "Jukebox.fxml";
    public static final String FXML_3D = "Menu.fxml";

    static Stage m_Stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(FXML_Menu));
        Scene scene = new Scene(fxmlLoader.load());//, 900, 600);

        m_Stage = stage;

        m_Stage.setResizable(false);

        stage.setTitle("SuperJFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void ChangerScene(String FXML_file, String WindowName) throws IOException {

        Scene scene = new Scene(new FXMLLoader(MainApplication.class.getResource(FXML_file)).load());

        m_Stage.setScene(scene);
        m_Stage.setTitle(WindowName);
    }


        public static void ChangerScene(String FXML_file, String WindowName, int Width, int Height) throws IOException {

        m_Stage.setWidth(Width);
        m_Stage.setHeight(Height);

        ChangerScene(FXML_file, WindowName);
    }

//    AnimationTimer animationTimer;
//
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        animationTimer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                btnTest.getTransforms().add(new Rotate(1));
//
//
//                if (btnTest.getTransforms().stream().count()>=360){
//                    animationTimer.stop();
//                }
//            }
//        };
//
//        animationTimer.start();
//    }

    public static void main(String[] args) {
        launch();
    }
}