package edu.erciyes.trafficlightsimulation;
import javafx.application.Platform;
import java.util.ArrayList;

public class TrafficManager {
    public ArrayList<TrafficLight> trafficLights = new ArrayList<>();//ışıkları tutan listemiz.

    public static void addVehicles(Road road, int vehicleCount) {
        for (int i = 0; i < vehicleCount; i++){
            //ileride farklı sınıf araçlar eklersek buraya random ekleyen bir algoritma yazabiliriz. !!!
            //Şimdilik sadece car sınıfı kullandık.
            road.vehiclesList.add(i, new Car(0,0));
        }
    }
    //araçları yön yön ekrana takip mesafesi koyarak basan method.
    public static void spawnVehicles(Road road, float xLight, float yLight) {
        int northIndex = 0;//arac sayısı arttıkça boşluk açan indexler
        int eastIndex = 0;
        int westIndex = 0;
        int southIndex = 0;

        for (int i = 0; i < road.vehiclesList.size(); i++) {
            if (road.vehiclesList.get(i).isSpawn) continue;

            float spawnX = xLight;
            float spawnY = yLight;

            switch (road.direction) {
                case "north":
                    spawnX = xLight - (northIndex * (road.vehiclesList.get(i).bodyX+25));//+25 takip mesafesi
                    /*if (spawnX >= 0) {
                        northIndex++;
                        road.vehiclesList.get(i).spawnVehicle(265, spawnX);
                        road.vehiclesList.get(i).getView().setRotate(90);
                        road.vehiclesList.get(i).isSpawn = true;
                    } else {
                        continue;
                    }*/
                    northIndex++;
                    road.vehiclesList.get(i).spawnVehicle(265, spawnX);
                    road.vehiclesList.get(i).getView().setRotate(90);
                    road.vehiclesList.get(i).isSpawn = true;
                    break;

                case "south":
                    spawnX = xLight + (southIndex * (road.vehiclesList.get(i).bodyX+25));
                    /*if (spawnX <= 600) {
                        southIndex++;
                        road.vehiclesList.get(i).spawnVehicle(310, spawnX);
                        road.vehiclesList.get(i).getView().setRotate(-90);
                        road.vehiclesList.get(i).isSpawn = true;
                    } else {
                        continue;
                    }*/
                    southIndex++;
                    road.vehiclesList.get(i).spawnVehicle(310, spawnX);
                    road.vehiclesList.get(i).getView().setRotate(-90);
                    road.vehiclesList.get(i).isSpawn = true;
                    break;

                case "west":
                    spawnX = xLight - (westIndex * (road.vehiclesList.get(i).bodyX+25));
                    /*if (spawnX >= 0) {
                        westIndex++;
                        road.vehiclesList.get(i).spawnVehicle(spawnX, 310);
                        road.vehiclesList.get(i).getView().setRotate(180);
                        road.vehiclesList.get(i).isSpawn = true;
                    } else {
                        continue;
                    }*/
                    westIndex++;
                    road.vehiclesList.get(i).spawnVehicle(spawnX-10, 310);
                    road.vehiclesList.get(i).getView().setRotate(180);
                    road.vehiclesList.get(i).isSpawn = true;
                    break;

                case "east":
                    spawnX = xLight + (eastIndex * (road.vehiclesList.get(i).bodyX+25));
                    /*if (spawnX <= 570) {
                        eastIndex++;
                        road.vehiclesList.get(i).spawnVehicle(spawnX, 270);
                        road.vehiclesList.get(i).getView().setRotate(0);
                        road.vehiclesList.get(i).isSpawn = true;
                    } else {
                        continue;
                    }*/
                    eastIndex++;
                    road.vehiclesList.get(i).spawnVehicle(spawnX, 270);
                    road.vehiclesList.get(i).getView().setRotate(0);
                    road.vehiclesList.get(i).isSpawn = true;
                    break;
            }

            road.vehiclesList.get(i).direction = road.direction;
        }
    }





    /* ŞU AN ALGORİTMADAKİ EKSİKLER =>  pause tuşu.
    */

    public void putLights(TrafficLight trafficLight) {//ışıkları listeye ekleyen method.
        this.trafficLights.add(trafficLight);
    }

    //SPAWN ETMELİ METHOD (İPTAL ETTİK)
    //---------------------------------
    /*public void manageTraffic(Road road) {
        System.out.println("Araç sayısı: " + road.vehiclesList.size());
        System.out.println(road.direction);
        for (TrafficLight trafficLight : this.trafficLights) {
            if (trafficLight.direction.equals(road.direction)) {
                trafficLight.turnGreen();
                new Thread(() -> {
                    long greenDuration = (long) (trafficLight.greenTime * 1000);
                    long yellowDuration = (long) (trafficLight.yellowTime * 1000);
                    long startTime = System.currentTimeMillis();

                    // Spawn ve hareket thread'i
                    Thread spawnThread = new Thread(() -> {
                        for (Vehicle vehicle : road.vehiclesList) {
                            // Yeşil ışık süresi boyunca spawn işlemini durdurmamız gerekiyor
                            long elapsed = System.currentTimeMillis() - startTime;
                            if (elapsed >= greenDuration) {
                                // Yeşil ışık süresi bitti, döngüyü kır
                                break;
                            }

                            if (vehicle.isSpawn) {
                                // Spawn olmuşsa hemen yürüt
                                Platform.runLater(vehicle::moveForward);
                            } else {
                                // Spawn edilmemişse 2 saniye bekle, sonra spawn et ve yürüt
                                try {
                                    long timeLeft = greenDuration - (System.currentTimeMillis() - startTime);
                                    if (timeLeft <= 0) break; // Yeşil ışık süresi bitti ise spawn yapma

                                    if (timeLeft < 2000) {
                                        // Kalan süre 2 saniyeden azsa o kadar bekle spawn yapma
                                        Thread.sleep(timeLeft);
                                        break;
                                    } else {
                                        Thread.sleep(1000);
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                // spawn işlemini Platform.runLater ile yap
                                Platform.runLater(() -> {
                                    int spawnX = 0, spawnY = 0;
                                    int rotation = 0;
                                    switch (trafficLight.direction) {
                                        case "north":
                                            spawnX = 265; spawnY = 10; rotation = 90;
                                            break;
                                        case "south":
                                            spawnX = 310; spawnY = 570; rotation = -90;
                                            break;
                                        case "east":
                                            spawnX = 570; spawnY = 270; rotation = 0;
                                            break;
                                        case "west":
                                            spawnX = 30; spawnY = 310; rotation = 180;
                                            break;
                                    }
                                    vehicle.spawnVehicle(spawnX, spawnY);
                                    vehicle.getView().setRotate(rotation);
                                    vehicle.isSpawn = true;
                                    vehicle.direction = road.direction;
                                    vehicle.moveForward();
                                });
                            }
                        }
                    });
                    spawnThread.start();

                    // Yeşil ışık süresini bekle
                    try {
                        long elapsed = System.currentTimeMillis() - startTime;
                        long remaining = greenDuration - elapsed;
                        if (remaining > 0) Thread.sleep(remaining);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Yeşil ışık bitince araçları durdur, sarıya geç
                    Platform.runLater(() -> {
                        for (Vehicle v : road.vehiclesList) {
                            if (v.isSpawn) v.stop();
                        }
                        trafficLight.turnYellow();

                        new Thread(() -> {
                            try {
                                Thread.sleep(yellowDuration);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Platform.runLater(trafficLight::turnRed);
                        }).start();
                    });

                    // Spawn thread hala çalışıyor olabilir, yeşil süre bitince döngüden çıkacak zaten.
                    try {
                        spawnThread.join(); // spawn işleminin bitmesini bekle
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }*/
    //yön yön araç trafiğini yöneten methodumuz.
    public void manageTraffic(Road road) {
        System.out.println("Araç sayısı: " + road.vehiclesList.size());
        System.out.println(road.direction);
        for (TrafficLight trafficLight : this.trafficLights) {//ışık süresine göre sıraya dizdiğimiz listemizi sıra sıra geziyor...
            if (trafficLight.direction.equals(road.direction)) {//gönderilen road directionu ile eşleşiyor mu diye kontrol ediyor...
                switch (trafficLight.direction) {
                    case "north":
                        new Thread(() -> {
                            //süreler ayarlanıyor ve yeşil ışığı yaktık.
                            long greenDuration = (long)(trafficLight.greenTime * 1000);
                            long yellowDuration = (trafficLight.yellowTime * 1000);
                            long startTime = System.currentTimeMillis();
                            trafficLight.turnGreen(greenDuration);
                            //araç hareketleri başladı.
                            new Thread(() -> {
                                for (Vehicle vehicle : road.vehiclesList) {
                                    if (vehicle.isSpawn) {
                                        Platform.runLater(vehicle::moveForward);
                                    }
                                }
                            }).start();
                            //yeşil ışık süresi kadar bekliyoruz
                            try {
                                long elapsed = System.currentTimeMillis() - startTime;
                                long remaining = greenDuration - elapsed;
                                if (remaining > 0) Thread.sleep(remaining);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //bütün araçların stop(); methodu tetikleniyor ve sarı ışığımız yanıyor...
                            Platform.runLater(() -> {
                                for (Vehicle v : road.vehiclesList) {
                                    if (v.isSpawn) v.stop();
                                }
                                trafficLight.turnYellow();
                                //sarı ışık süresi kadar bekliyoruz ve kırmızı ışığımız yanıyor
                                new Thread(() -> {
                                    try {
                                        Thread.sleep(yellowDuration);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Platform.runLater(trafficLight::turnRed);
                                }).start();
                            });
                        }).start();
                        break;
                    //diğer caseler için olay hep aynı
                    case "south":
                        new Thread(() -> {
                            long greenDuration = (long) (trafficLight.greenTime * 1000);
                            long yellowDuration = (long) (trafficLight.yellowTime * 1000);
                            long startTime = System.currentTimeMillis();
                            trafficLight.turnGreen(greenDuration);

                            new Thread(() -> {
                                for (Vehicle vehicle : road.vehiclesList) {
                                    if (vehicle.isSpawn) {
                                        Platform.runLater(vehicle::moveForward);
                                    }
                                }
                            }).start();

                            try {
                                long elapsed = System.currentTimeMillis() - startTime;
                                long remaining = greenDuration - elapsed;
                                if (remaining > 0) Thread.sleep(remaining);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Platform.runLater(() -> {
                                for (Vehicle v : road.vehiclesList) {
                                    if (v.isSpawn) v.stop();
                                }
                                trafficLight.turnYellow();

                                new Thread(() -> {
                                    try {
                                        Thread.sleep(yellowDuration);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Platform.runLater(trafficLight::turnRed);
                                }).start();
                            });
                        }).start();
                        break;

                    case "east":
                        new Thread(() -> {
                            long greenDuration = (long) (trafficLight.greenTime * 1000);
                            long yellowDuration = (long) (trafficLight.yellowTime * 1000);
                            long startTime = System.currentTimeMillis();
                            trafficLight.turnGreen(greenDuration);

                            new Thread(() -> {
                                for (Vehicle vehicle : road.vehiclesList) {
                                    if (vehicle.isSpawn) {
                                        Platform.runLater(vehicle::moveForward);
                                    }
                                }
                            }).start();

                            try {
                                long elapsed = System.currentTimeMillis() - startTime;
                                long remaining = greenDuration - elapsed;
                                if (remaining > 0) Thread.sleep(remaining);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Platform.runLater(() -> {
                                for (Vehicle v : road.vehiclesList) {
                                    if (v.isSpawn) v.stop();
                                }
                                trafficLight.turnYellow();

                                new Thread(() -> {
                                    try {
                                        Thread.sleep(yellowDuration);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Platform.runLater(trafficLight::turnRed);
                                }).start();
                            });
                        }).start();
                        break;

                    case "west":
                        new Thread(() -> {
                            long greenDuration = (long) (trafficLight.greenTime * 1000);
                            long yellowDuration = (long) (trafficLight.yellowTime * 1000);
                            long startTime = System.currentTimeMillis();
                            trafficLight.turnGreen(greenDuration);
                            new Thread(() -> {
                                for (Vehicle vehicle : road.vehiclesList) {
                                    if (vehicle.isSpawn) {
                                        Platform.runLater(vehicle::moveForward);
                                    }
                                }
                            }).start();

                            try {
                                long elapsed = System.currentTimeMillis() - startTime;
                                long remaining = greenDuration - elapsed;
                                if (remaining > 0) Thread.sleep(remaining);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Platform.runLater(() -> {
                                for (Vehicle v : road.vehiclesList) {
                                    if (v.isSpawn) v.stop();
                                }
                                trafficLight.turnYellow();

                                new Thread(() -> {
                                    try {
                                        Thread.sleep(yellowDuration);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Platform.runLater(trafficLight::turnRed);
                                }).start();
                            });
                        }).start();
                        break;
                }
            }
        }
    }
}
