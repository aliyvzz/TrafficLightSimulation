package edu.erciyes.trafficlightsimulation;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class TrafficLight {
    public String situation;
    public float greenTime;
    public final long yellowTime = 3;//sarı ışık süresi sabit !!!
    public String direction;
    public float x,y;
    private final Rectangle box;
    private final Circle red;
    private final Circle yellow;
    private final Circle green;
    private final Group view;


    private Text timerText;//lambadki sayaç için
    private AnimationTimer animationTimer;


    TrafficLight(int x, int y,int vehicleCount, int totalCount,String direction){// + AÇI PARAMETRESİ ???
        this.x = x;
        this.y = y;
        this.direction = direction;
        if (totalCount > 0) {
            this.greenTime = ((float) vehicleCount / (float)totalCount) * 108;//(120- 12(4*3 sarı ışık süresi) )
        } else {
            this.greenTime = 1; // varsayılan değer
        }

        //çizim işlemleri...
        box = new Rectangle(20, 60);
        box.setFill(Color.BLACK);
        box.setX(x);
        box.setY(y);

        red = createLight(Color.RED, x + 10, y + 10);
        yellow = createLight(Color.YELLOW, x + 10, y + 30);
        green = createLight(Color.GREEN, x + 10, y + 50);

        view = new Group(box, red, yellow, green);

        timerText = new Text(x, y + 30, "");
        switch (direction) { //ışık süreleri yöne göre ayarlandı.
            case "north":
                timerText.setRotate(-90);
                break;
            case "south":
                timerText.setRotate(90);
                break;
            case "east":
                timerText.setRotate(180);
                break;
            case "west":
                break;
        }
        timerText.setFill(Color.GREEN);
        timerText.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        view.getChildren().add(timerText);
    }

    private Circle createLight(Color color, double centerX, double centerY) {
        Circle light = new Circle(7);
        light.setFill(color);
        light.setCenterX(centerX);
        light.setCenterY(centerY);
        return light;
    }

    public Group getView() {
        return view;
    }

    public void turnRed(){
        situation = "red";
        red.setOpacity(1.0);      // Tam parlak
        yellow.setOpacity(0.1);   // Daha sönük
        green.setOpacity(0.1);    // Daha sönük
        System.out.println("Turning red " + this.direction);
    }

    public void turnYellow(){
        situation = "yellow";
        red.setOpacity(0.1);
        yellow.setOpacity(1.0);
        green.setOpacity(0.1);
        System.out.println("Turning yellow " + this.direction);
    }

    public void turnGreen(long durationMillis) {
        situation = "green";
        red.setOpacity(0.1);
        yellow.setOpacity(0.1);
        green.setOpacity(1.0);
        System.out.println("Turning green " + this.direction);

        if (animationTimer != null) {
            animationTimer.stop();
        }

        long startTime = System.nanoTime();

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedNanos = now - startTime;
                long elapsedMillis = elapsedNanos / 1_000_000;
                long remainingMillis = durationMillis - elapsedMillis;

                if (remainingMillis <= 0) {
                    timerText.setText("0");
                    stop();
                } else {
                    timerText.setText(String.valueOf(remainingMillis / 1000)); //saniye olarak gösteriyoruz.
                }
            }
        };
        animationTimer.start();
    }
}
