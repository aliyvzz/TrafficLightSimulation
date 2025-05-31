package edu.erciyes.trafficlightsimulation;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Road {
    public ArrayList<Vehicle> vehiclesList = new ArrayList<>();//araçları içinde tutan listemiz.
    public String direction;//yönümüz
    //private Pane pane;
    public Rectangle shape;
    //yolu görselize ediyoruz...
    public Road(int x, int x2,int y, int y2) {
        shape = new Rectangle(x,x2,y,y2);
        shape.setFill(Color.LIGHTGRAY);
        shape.setStroke(Color.BLACK);
    }
}
