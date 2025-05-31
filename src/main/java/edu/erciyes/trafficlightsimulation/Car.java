package edu.erciyes.trafficlightsimulation;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Car extends Vehicle {

    private Rectangle body;
    private AnimationTimer animationTimer;
    public Car(float xDirection, float yDirection) {
        super(xDirection, yDirection); // Vehicle constructor'ı çağrılıyor...
        bodyX = 30; //******
        bodyY = 15;
        speed = 0.6f;
    }

    //arabayı ekrana bastırma/oluşturma methodu
    @Override
    public void spawnVehicle(float x, float y) {
        this.xDirection=x;
        this.yDirection=y;
        System.out.println("arac spawn edildii");
        //görselize et
        body = new Rectangle(xDirection, yDirection,bodyX,bodyY);
        body.setFill(Color.BLUE);
        mapManager.root.getChildren().add(body);
    }
    @Override
    public Rectangle getView() {
        return body;
    }


    //Rastgele bir şekilde ileri hareket etme methodu
    public void moveForward() {
        if (animationTimer != null) return;

        Random rand = new Random();
        int randomMove = rand.nextInt(3); // 0,1,2

        switch (direction) {
            case "north":
                if (isPaused) return; // pause kontrolü
                System.out.println("Araç hareket ediyor... (north) - randomMove: " + randomMove);
                animationTimer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        if (randomMove == 0) {
                            // İleri hareket: y artıyor sürekli (yukarıdan aşağıya)
                            body.setY(body.getY() + speed);
                        } else if (randomMove == 1) {
                            // Sağa dönüş: önce y 270'ye kadar artacak, sonra x azalacak
                            if (body.getY() < 270) {
                                body.setY(body.getY() + speed);
                            } else {
                                // Sağa dönüş (rotate 0 derece)
                                body.setRotate(0);
                                body.setX(body.getX() - speed);
                            }
                        } else if (randomMove == 2) {
                            // Sola dönüş: önce y 310'a kadar artacak, sonra x artacak
                            if (body.getY() < 310) {
                                body.setY(body.getY() + speed);
                            } else {
                                // Sola dönüş (rotate 180 derece)
                                body.setRotate(180);
                                body.setX(body.getX() + speed);
                            }
                        }
                    }
                };
                animationTimer.start();
                break;

            case "south":
                if (isPaused) return; // pause kontrolü
                System.out.println("Araç hareket ediyor... (south) - randomMove: " + randomMove);
                animationTimer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        if (randomMove == 0) {
                            // İleri hareket: y azalıyor sürekli (aşağıdan yukarıya)
                            body.setY(body.getY() - speed);
                        } else if (randomMove == 1) {
                            // Sağa dönüş: önce y 330'a kadar azalacak, sonra x artacak
                            if (body.getY() > 310) {
                                body.setY(body.getY() - speed);
                            } else {
                                // Sağa dönüş (rotate 180 derece)
                                body.setRotate(180);
                                body.setX(body.getX() + speed);
                            }
                        } else if (randomMove == 2) {
                            // Sola dönüş: önce y 290'a kadar azalacak, sonra x azalacak
                            if (body.getY() > 270) {
                                body.setY(body.getY() - speed);
                            } else {
                                // Sola dönüş (rotate 0 derece)
                                body.setRotate(0);
                                body.setX(body.getX() - speed);
                            }
                        }
                    }
                };
                animationTimer.start();
                break;

            case "east":
                if (isPaused) return; // pause kontrolü
                System.out.println("Araç hareket ediyor... (east) - randomMove: " + randomMove);
                animationTimer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        if (randomMove == 0) {
                            // İleri hareket: x azalıyor sürekli (sağdan sola)
                            body.setX(body.getX() - speed);
                        } else if (randomMove == 1) {
                            // Sağa dönüş: önce x 330'a kadar azalacak, sonra y azalacak
                            if (body.getX() > 310) {
                                body.setX(body.getX() - speed);
                            } else {
                                // Sağa dönüş (rotate -90 derece)
                                body.setRotate(-90);
                                body.setY(body.getY() - speed);
                            }
                        } else if (randomMove == 2) {
                            // Sola dönüş: önce x 290'a kadar azalacak, sonra y artacak
                            if (body.getX() > 265) {
                                body.setX(body.getX() - speed);
                            } else {
                                // Sola dönüş (rotate +90 derece)
                                body.setRotate(90);
                                body.setY(body.getY() + speed);
                            }
                        }
                    }
                };
                animationTimer.start();
                break;

            case "west":
                if (isPaused) return; // pause kontrolü
                System.out.println("Araç hareket ediyor... (west) - randomMove: " + randomMove);
                animationTimer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        if (randomMove == 0) {
                            // İleri hareket: x artıyor sürekli (soldan sağa)
                            body.setX(body.getX() + speed);
                        } else if (randomMove == 1) {
                            // Sağa dönüş: önce x 270'ye kadar artacak, sonra y artacak
                            if (body.getX() < 265) {
                                body.setX(body.getX() + speed);
                            } else {
                                // Sağa dönüş (rotate +90 derece)
                                body.setRotate(90);
                                body.setY(body.getY() + speed);
                            }
                        } else if (randomMove == 2) {
                            // Sola dönüş: önce x 310'a kadar artacak, sonra y azalacak
                            if (body.getX() < 310) {
                                body.setX(body.getX() + speed);
                            } else {
                                // Sola dönüş (rotate -90 derece)
                                body.setRotate(-90);
                                body.setY(body.getY() - speed);
                            }
                        }
                    }
                };
                animationTimer.start();
                break;

            default:
                System.out.println("Geçersiz yön: " + direction);
                break;
        }
    }



    //araçları durduran method(ışık hizasına bakarak)
    @Override
    public void stop() {
        System.out.println("Araç durma isteği aldı");

        if (animationTimer == null) return;

        double stopLineX = 0;
        double stopLineY = 0;

        boolean passedStopLine = false;

        switch (direction) {
            case "north":
                stopLineY = 210;
                if (body.getY() > stopLineY) passedStopLine = true;
                break;
            case "south":
                stopLineY = 370;
                if (body.getY() < stopLineY) passedStopLine = true;
                System.out.println(body.getY());
                break;
            case "east":
                stopLineX = 350;
                if (body.getX() < stopLineX) passedStopLine = true;
                break;
            case "west":
                stopLineX = 230;
                if (body.getX() > stopLineX) passedStopLine = true;
                break;
        }

        if (passedStopLine) {
            System.out.println("Araç ışık hizasını geçti, durmayacak.");
            // Timer'ı durdurma, devam et
        } else {
            System.out.println("Araç ışık hizasını geçmedi, duruyor.");
            animationTimer.stop();
            animationTimer = null;
        }
    }
}

