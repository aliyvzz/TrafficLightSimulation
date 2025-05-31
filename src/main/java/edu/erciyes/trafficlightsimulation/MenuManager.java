package edu.erciyes.trafficlightsimulation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuManager {
    //butonları oluşturduk.
    @javafx.fxml.FXML
    private javafx.scene.control.Button startButton;
    @javafx.fxml.FXML
    private javafx.scene.control.Button autoButton;
    @javafx.fxml.FXML
    private javafx.scene.control.Button manualButton;
    @javafx.fxml.FXML
    private javafx.scene.control.Button continueButton;

    @javafx.fxml.FXML
    private javafx.scene.control.TextField northField;
    @javafx.fxml.FXML
    private javafx.scene.control.TextField southField;
    @javafx.fxml.FXML
    private javafx.scene.control.TextField westField;
    @javafx.fxml.FXML
    private javafx.scene.control.TextField eastField;

    // Start butonuna tıklandığında
    @javafx.fxml.FXML
    private void handleStartButton() {
        System.out.println("Start basıldı.");
        autoButton.setVisible(true);
        manualButton.setVisible(true);
    }

    // Oto butonuna tıklandığında
    @javafx.fxml.FXML
    private void handleAutoButton(javafx.event.ActionEvent event) {
        System.out.println("Oto seçildi. Simülasyon ekranına geçilecek.");
        InputData.randomValues(); // oto input atma methodu
        Stage stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        mapManager.showSimulation(stage); // sahne geçişi
    }

    // Manuel butonuna tıklandığında
    @javafx.fxml.FXML
    private void handleManualButton() {
        System.out.println("Manuel seçildi. Inputlar görünecek.");
        //gizli butonlar görünür oluyor.
        northField.setVisible(true);
        southField.setVisible(true);
        westField.setVisible(true);
        eastField.setVisible(true);
        continueButton.setVisible(true);
    }

    //Play butonuna tıklandığında
    @javafx.fxml.FXML
    private void handleContinue(javafx.event.ActionEvent event) {
        //burda try catch ile doğru atama yapıp yapmadığına bakıyor eğer true dönmez ise hata mesajı fırlatıyoruz
        try {
            int n = Integer.parseInt(northField.getText());
            int s = Integer.parseInt(southField.getText());
            int w = Integer.parseInt(westField.getText());
            int e = Integer.parseInt(eastField.getText());

            boolean valid = InputData.setValues(n, s, w, e);

            if (valid) {
                Stage stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
                mapManager.showSimulation(stage); // sahne geçişi
            } else {
                showAlert("Hata", "Toplam değer 100'yi geçemez!", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Hata", "Lütfen geçerli sayılar girin.", Alert.AlertType.ERROR);
        }
    }
    //hata mesajı methodumuz
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //menü sahnesini gösteren statik metot
    public static void showMenu(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(MenuManager.class.getResource("menu.fxml"));
            Scene menuScene = new Scene(loader.load());
            stage.setScene(menuScene);
            stage.setTitle("Traffic Simulation Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Menü yüklenemedi!");
            alert.showAndWait();
        }
    }
}
