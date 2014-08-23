package me.jack.LD30.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Jack on 23/08/2014.
 */
public class LevelGenerator {



    public static float[][] generateWhiteNoise(int width, int height,Random r){



        float[][] noise = new float[width][height];

        for(int i = 0;i < width;i++){
            for(int j = 0;j < height;j++){
                noise[i][j] = (float)r.nextDouble();
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

        for(int i = 0;i < w;i++){

            int s_i0 = (i/sP)*sP;
            int s_i1 = (s_i0 + sP) % w;
            float hB = (i-s_i0) * sF;

            for(int j = 0; j < h;j++){

                int s_j0 = (j/sP) * sP;
                int s_j1 = (s_j0 + sP) % h;
                float vB = (j-s_j0) *sF;

                float top = interpolate(noise[s_i0][s_j0],noise[s_i1][s_j0],hB);

                float bottom = interpolate(noise[s_i0][s_j1],noise[s_i1][s_j1],hB);

                smoothNoise[i][j] = interpolate(top,bottom,vB);
            }
        }
        return smoothNoise;
    }


    public static float[][] generatePerlinNoise(float[][] noise, int oC){
        int w = noise.length;
        int h = noise[0].length;

        float[][][] smoothNoise = new float[oC][][];

        float persistence = 0.5f;

        for(int i = 0;i < oC;i++){
            smoothNoise[i] = generateSmoothNoise(noise,i);
        }

        float[][] perlinNoise = new float[w][h];

        float amplitude = 1.0f;
        float totalAmplitude = 0.0f;

        for(int octave = oC - 1;octave >0;octave--){
            amplitude *=persistence;
            totalAmplitude+=amplitude;

            for(int i = 0;i < w;i++){
                for(int j = 0;j < h;j++){
                    perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
                }
            }
        }


        for(int i = 0;i < w;i++){
            for(int j = 0;j < h;j++){
                perlinNoise[i][j] /=totalAmplitude;
            }
        }
    return perlinNoise;
    }



    public static float interpolate(float x0,float x1,float alpha){
        return x0*(1-alpha) + alpha * x1;
    }


    public static float[][] group(float[][] noise){
        float[][] grouped = new float[noise.length][noise[0].length];

        for(int x = 0;x!= noise.length;x++){
            for(int y = 0; y!= noise[0].length;y++){
                float t = noise[x][y];
                if(t > 0.5) t = 1;
                else t = 0;

                grouped[x][y] = t;
            }
        }
        return grouped;
    }


    public static void main(String[] args) {
        Random r = new Random();















        while (true) {

            float[][] whiteNoise = generateWhiteNoise(50,50,r);

            float[][] perlin = generatePerlinNoise(whiteNoise,4);

            perlin = group(perlin);

            int[] noise = null;

            BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);

            int[] pixels = new int[50*50];

            for(int x = 0;x!=perlin.length;x++){
                for(int y = 0;y!= perlin[0].length;y++){
                    float p = perlin[x][y];

                    Color c = getColor(Color.black,Color.DARK_GRAY,p);
                    pixels[x+y*50] = c.hashCode();
                }
            }

            image.setRGB(0, 0, 50, 50, pixels, 0, 50);

            JOptionPane.showMessageDialog(null, null, "Test", JOptionPane.YES_NO_OPTION, new ImageIcon(image.getScaledInstance(50*4,50*4,0)));
        }

    }



    public static Color getColor(Color s,Color e, float t){
        float u = 1-t;


        int r = (int) (s.getRed() * u + e.getRed() * t);
        int g =  (int) (s.getGreen() * u + e.getGreen() * t);
        int b =  (int) (s.getBlue() * u + e.getBlue() * t);


        Color c = new Color(r,g,b);
        return c;
    }

}
