package me.jack.LD30.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Jack on 23/08/2014.
 */
public class LevelGenerator {



    public static float[][] generateWhiteNoise(int width, int height){

        Random r = new Random();

        float[][] noise = new float[width][height];

        for(int i = 0;i < width;i++){
            for(int j = 0;j < height;j++){
                noise[i][j] = (float)r.nextDouble() % 1;
            }
        }

        return noise;
    }



    public static float[][] generateSmoothNoise(float[][] noise, int octave){

        int w = noise.length;
        int h = noise[0].length;

        float[][] smoothNoise = new float[w][h];

        int sP = 1 << octave;
        float sF = 1.0f/sP;

        for(int j = 0;j < w;j++){

            int s_j0 = (j/sP)*sP;
            int s_j1 = (s_j0 + sP) % w;

            float vB = (j- s_j0) * sF;

            float top =
        }

    }







    public static void main(String[] args) {
        while (true) {
            int[] noise = null;

            BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);

            int[] pixels = new int[20 * 20];

            for (int i = 0; i != noise.length; i++) {
                int n = noise[i];
                if (n == 0) {
                    pixels[i] = 0xffffff;
                } else {
                    pixels[i] = 0x000080;
                }
            }

            image.setRGB(0, 0, 20, 20, pixels, 0, 20);

            JOptionPane.showMessageDialog(null, null, "Test", JOptionPane.YES_NO_OPTION, new ImageIcon(image.getScaledInstance(20 * 4, 20 * 4, Image.SCALE_AREA_AVERAGING)));
        }

    }

}
