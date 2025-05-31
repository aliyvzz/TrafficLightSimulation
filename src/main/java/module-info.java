module edu.erciyes.trafficlightsimulation {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.erciyes.trafficlightsimulation to javafx.fxml;
    exports edu.erciyes.trafficlightsimulation;
}