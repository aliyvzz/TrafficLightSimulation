package edu.erciyes.trafficlightsimulation;

import java.util.Random;

public class InputData {
    public static int north, south, west, east;//erişim kolaylığı için static bir şekilde çektik
    private static final int MAX_TOTAL = 100;
    //manuel ekranımızdan gelen verileri set eden method.
    public static boolean setValues(int n, int s, int w, int e) {
        int total = n + s + w + e;
        if (total <= MAX_TOTAL) {
            north = n;
            south = s;
            west = w;
            east = e;
            return true;
        } else {
            return false;
        }
    }
    //otoyu seçince random atama yapıp set eden method.
    public static void randomValues() {
        Random rand = new Random();
        int maxTotal = MAX_TOTAL;  // örn 50
        int total = rand.nextInt(maxTotal + 1); // Toplam 0 ile 50 arasında rastgele

        int remaining = total;

        north = rand.nextInt(remaining + 1);
        remaining -= north;

        south = rand.nextInt(remaining + 1);
        remaining -= south;

        west = rand.nextInt(remaining + 1);
        remaining -= west;

        east = remaining;
    }
    //reset butonumuz için verileri resetleyen method.
    public static void reset() {
        north = 0;
        south = 0;
        west = 0;
        east = 0;
    }

    //total aracımızı veren method
    public static int getTotal() {
        return north + south + west + east;
    }
}
