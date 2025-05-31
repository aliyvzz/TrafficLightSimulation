package edu.erciyes.trafficlightsimulation;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Comparator;

public class mapManager {//bütün olayı buraya gömücez.
    public static Pane root;
    public static void showSimulation(Stage stage) {
         root = new Pane();

        // Reset butonumuz.
        Button resetButton = new Button("Reset");
        resetButton.setLayoutX(10); // sol üst
        resetButton.setLayoutY(10);

        resetButton.setOnAction(e -> {
            InputData.reset(); // İnputları sıfırla
            Stage stage_ = (Stage) resetButton.getScene().getWindow();
            MenuManager.showMenu(stage_); // Menüye geri dön
        });
        root.getChildren().add(resetButton);

        //lamba ve yönlerini ve konumlarını bastırdık(çizdirme işlemi constructorun içinde olcak)
        TrafficLight northLight = new TrafficLight(210,210,InputData.north,InputData.getTotal(),"north");
        TrafficLight southLight = new TrafficLight(370,330,InputData.south,InputData.getTotal(),"south");
        TrafficLight westLight = new TrafficLight(230,350,InputData.west,InputData.getTotal(),"west");
        TrafficLight eastLight = new TrafficLight(350,190,InputData.east,InputData.getTotal(),"east");
        //Yönler ayarlanıyor...
        southLight.getView().setRotate(-90);
        eastLight.getView().setRotate(-180);
        northLight.getView().setRotate(90);
        // lambalari ekrana basma işlemi
        root.getChildren().addAll(northLight.getView(),southLight.getView(),westLight.getView(),eastLight.getView());
        //önce ışıkların hepsini kırmızıya çekicez
        northLight.turnRed();
        southLight.turnRed();
        westLight.turnRed();
        eastLight.turnRed();
        //yol ve yönlerini oluşturduk (constructorun içinde çizdirme işlemi)
        Road northRoad = new Road(260,0,40,600); northRoad.direction = "north";//yönlerini atadık
        Road southRoad = new Road(300,0,40,600); southRoad.direction = "south";
        Road westRoad = new Road(0,260,600,40);  westRoad.direction = "west";
        Road eastRoad = new Road(0,300,600,40);  eastRoad.direction = "east";
        Road kavsak = new Road(260,260,80,80);
        root.getChildren().addAll(northRoad.shape,southRoad.shape,westRoad.shape,eastRoad.shape,kavsak.shape);
        //araçları ekliyoruz...
        TrafficManager.addVehicles(northRoad,InputData.north);
        TrafficManager.addVehicles(southRoad,InputData.south);
        TrafficManager.addVehicles(westRoad,InputData.west);
        TrafficManager.addVehicles(eastRoad,InputData.east);

        TrafficManager manager = new TrafficManager();

        //Lambaları listeye ekliyoruz...
        manager.putLights(northLight);
        manager.putLights(southLight);
        manager.putLights(westLight);
        manager.putLights(eastLight);
        //yeşil ışık süresine göre sıralama yaptık !!!
        manager.trafficLights.sort(Comparator.comparingDouble((TrafficLight light) -> light.greenTime).reversed());
        //araçlar spawn oluyor...
        TrafficManager.spawnVehicles(northRoad,northLight.x,northLight.y);
        TrafficManager.spawnVehicles(southRoad,southLight.x,southLight.y);
        TrafficManager.spawnVehicles(westRoad,westLight.x,westLight.y);
        TrafficManager.spawnVehicles(eastRoad,eastLight.x,eastLight.y);
        //Trafik akışını yönetiyoruz...
        //4 kere dönerek 4 ışığıda doğru sırada çalıştırcak.
        new Thread(() -> {
            for (TrafficLight trafficLight : manager.trafficLights) {
                System.out.println("Yeni Döngü Başladı");
                System.out.println("Şimdi: " + trafficLight.direction + " yeşil ışık süresi: " + trafficLight.greenTime);
                if (northRoad.direction.equals(trafficLight.direction)) {
                    manager.manageTraffic(northRoad);
                } else if (southRoad.direction.equals(trafficLight.direction)) {
                    manager.manageTraffic(southRoad);
                } else if (westRoad.direction.equals(trafficLight.direction)) {
                    manager.manageTraffic(westRoad);
                } else if (eastRoad.direction.equals(trafficLight.direction)) {
                    manager.manageTraffic(eastRoad);
                }

                long sleepTime = (long) ((trafficLight.greenTime + trafficLight.yellowTime) * 1000);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Scene scene = new Scene(root,600,600);
        stage.setScene(scene);
        stage.setTitle("Traffic Simulation Screen");
        stage.show();
    }
}
