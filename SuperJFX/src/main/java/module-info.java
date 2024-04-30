module org.xen.superjfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.xen.superjfx to javafx.fxml;
    exports org.xen.superjfx;
}