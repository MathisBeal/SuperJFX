module org.xen.superjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens org.xen.superjfx to javafx.fxml;
    exports org.xen.superjfx;
}