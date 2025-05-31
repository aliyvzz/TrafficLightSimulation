package edu.erciyes.trafficlightsimulation;

import javafx.scene.shape.Rectangle;

public abstract class Vehicle  {
    public float xDirection,yDirection;
    public String direction;
    public boolean isSpawn;
    public float bodyX, bodyY;
    public  float speed;
    protected boolean isPaused;  // pause flag


    Vehicle(float xDirection,float yDirection){
        this.xDirection=xDirection;
        this.yDirection=yDirection;
    }

    //Burada methodlara parametre olarak ışık konumlarını verebiliriz belki. Ona göre davranır daha rahat olur !!!
    public abstract void spawnVehicle(float x, float y);
    public abstract void moveForward();
    public abstract Rectangle getView();
    public abstract void stop();//buraya arac konumuna bağlı durma işlemi yapmamız gerek !!!
}
