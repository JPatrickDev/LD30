package me.jack.LD30.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Jack on 23/08/2014.
 */
public class LevelGenerator {


    public static float[][] generateWhiteNoise(int width, int height, Random r) {


        float[][] noise = new float[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                noise[i][j] = (float) r.nextDouble();
            }
        }

        return noise;
    }


    public static float[][] generateSmoothNoise(float[][] noise, int octave) {

        int w = noise.length;
        int h = noise[0].length;

        float[][] smoothNoise = new float[w][h];

        int sP = 1 << octave;
        float sF = 1.0f / sP;

        for (int i = 0; i < w; i++) {

            int s_i0 = (i / sP) * sP;
            int s_i1 = (s_i0 + sP) % w;
            float hB = (i - s_i0) * sF;

            for (int j = 0; j < h; j++) {

                int s_j0 = (j / sP) * sP;
                int s_j1 = (s_j0 + sP) % h;
                float vB = (j - s_j0) * sF;

                float top = interpolate(noise[s_i0][s_j0], noise[s_i1][s_j0], hB);

                float bottom = interpolate(noise[s_i0][s_j1], noise[s_i1][s_j1], hB);

                smoothNoise[i][j] = interpolate(top, bottom, vB);
            }
        }
        return smoothNoise;
    }


    public static float[][] generatePerlinNoise(float[][] noise,GenerationOptions options) {
        int oC = options.octave_count;
        float persistence = options.persistence;
        int w = noise.length;
        int h = noise[0].length;

        float[][][] smoothNoise = new float[oC][][];


        for (int i = 0; i < oC; i++) {
            smoothNoise[i] = generateSmoothNoise(noise, i);
        }

        float[][] perlinNoise = new float[w][h];

        float amplitude = 0.5f;
        float totalAmplitude = 0.0f;

        for (int octave = oC - 1; octave > 0; octave--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
                }
            }
        }


        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                perlinNoise[i][j] /= totalAmplitude;
            }
        }
        return perlinNoise;
    }


    public static float interpolate(float x0, float x1, float alpha) {
        return x0 * (1 - alpha) + alpha * x1;
    }


    public static float[][] group(float[][] noise,GenerationOptions options) {
        float[][] grouped = new float[noise.length][noise[0].length];

        for (int x = 0; x != noise.length; x++) {
            for (int y = 0; y != noise[0].length; y++) {
                float t = noise[x][y];
                if (t > options.water_start) t = 1;
                else if (t < options.sand_end&& t > options.sand_start) t = 0.5f;
                else t = 0;

                grouped[x][y] = t;
            }
        }
        return grouped;
    }

    public static Level generateLevel(int width, int height,boolean test,GenerationOptions options) {

        long start = System.currentTimeMillis();
        boolean valid = false;
        Level l = null;
        while (!valid) {
            Random r = new Random();
            float[][] whitenoise = generateWhiteNoise(width, height, r);
            float[][] perlinNoise = generatePerlinNoise(whitenoise,options);
            float[][] tiled = group(perlinNoise,options);

            float[][] trees = trees(width,height);

            for (int x = 0; x != width; x++) {
                for (int y = 0; y != height; y++) {
                    float t = tiled[x][y];

                    float treeT = trees[x][y];
                    if (treeT != 1) continue;

                    if (t != 0) trees[x][y] = 0;
                }
            }

            l = new Level(width, height);
            l.setTrees(trees);
            l.setTiles(tiled);
            valid = validate(l);
            //valid = true;
        }
        long end = System.currentTimeMillis();

        l.postGeneration(test);
        System.out.println("Level generation took: " + (end - start));


        return l;
    }


    public static float[][] trees(int w, int h) {
        float[][] white = generateWhiteNoise(w, h, new Random());
       // float[][] perlin = generatePerlinNoise(white, 3, 0.7f);
        float[][] perlin = white;
        for (int x = 0; x != perlin.length; x++) {
            for (int y = 0; y != perlin[0].length; y++) {
                float p = perlin[x][y];
                if (p > 0.6) perlin[x][y] = 1;
                else perlin[x][y] = 0;
            }
        }
        return perlin;
    }

    public static float[][] createForrests(int lw, int lh) {

        Random r = new Random();

        int i = r.nextInt(5) + 1;
        System.out.println("Going to create " + i + " forrests");

        float[][] trees = new float[lw][lh];

        for (int j = 0; j != i; j++) {
            float[][] forrest = generateForrest();

            int x = r.nextInt(lw);
            int y = r.nextInt(lh);

            int xf = 0;
            int yf = 0;


        }

        return trees;

    }

    public static float[][] generateForrest() {
/*
        boolean valid = false;
        while (!valid) {

            Random r = new Random();
            int width = r.nextInt(7) + 3;
            int height = r.nextInt(7) + 3;

            float[][] white = generateWhiteNoise(width, height, new Random());
            float[][] perlin = generatePerlinNoise(white, 4, 0.8f);


            int trees = 0;
            for (int x = 0; x != perlin.length; x++) {
                for (int y = 0; y != perlin[0].length; y++) {
                    float p = perlin[x][y];
                    if (p > 0.45) {
                        perlin[x][y] = 0;
                        trees++;
                    } else perlin[x][y] = 1;
                }
            }

            if (trees > (width * height) / 2) {
                System.out.println("Valid");
                valid = true;
                return perlin;
            }
        }
        return null;
        */

        return null;
    }



    public static Point findSpawnLocation(Level l){
        Random r = new Random();


        float[][] tiles = l.getTiles();
        float[][] trees = l.getTrees();

        boolean spawnFound = false;
        int tries =0;

        while(!spawnFound){
            tries++;

            int x = r.nextInt(l.getWidth());
            int y = r.nextInt(l.getHeight());


            float tileAttempt = tiles[x][y];

            if(tileAttempt != 0)continue;

            if(!surrounded(0,tiles,x,y,l.getWidth(),l.getHeight()))continue;

            if(trees[x][y] != 0)continue;

            System.out.println("Spawn point found");
            spawnFound = true;
            return new Point(x,y);
        }

        return null;
    }


    public static boolean validate(Level l) {

        float[][] trees = l.getTrees();
        int found = 0;
        for (int x = 0; x != trees.length; x++) {
            for (int y = 0; y != trees[0].length; y++) {
                if (trees[x][y] == 1) found++;
            }
        }

        if (found < 10) return false;


        float[][] tiles = l.getTiles();
        for (int x = 0; x != tiles.length; x++) {
            for (int y = 0; y != tiles[0].length; y++) {
                float t = tiles[x][y];
                if (t == 0.5) {
                    if (!touching(1, tiles, x, y, tiles.length, tiles[0].length) || !touching((float) 0.5, tiles, x, y, tiles.length, tiles[0].length)) {
                        if (surrounded(1, tiles, x, y, tiles.length, tiles[0].length)) {
                            tiles[x][y] = 1;
                        } else {
                            tiles[x][y] = 0;
                        }
                    }
                }
            }
        }
        l.setTiles(tiles);
        return true;
    }

    public static boolean touching(float tileToTouch, float[][] tiles, int x, int y, int w, int h) {
        float t1 = 0, t2 = 0, t3 = 0, t4 = 0;
        if (x + 1 < w)
            t1 = tiles[x + 1][y];
        if (x - 1 >= 0)
            t2 = tiles[x - 1][y];
        if (y + 1 < h)
            t3 = tiles[x][y + 1];
        if (y - 1 >= 0)
            t4 = tiles[x][y - 1];
        if (t1 != tileToTouch && t2 != tileToTouch && t3 != tileToTouch && t4 != tileToTouch) {
            return false;
        }
        return true;
    }

    public static boolean surrounded(float tileToTouch, float[][] tiles, int x, int y, int w, int h) {
        float t1 = 0, t2 = 0, t3 = 0, t4 = 0;
        if (x + 1 < w)
            t1 = tiles[x + 1][y];
        if (x - 1 >= 0)
            t2 = tiles[x - 1][y];
        if (y + 1 < h)
            t3 = tiles[x][y + 1];
        if (y - 1 >= 0)
            t4 = tiles[x][y - 1];
        if (t1 == tileToTouch && t2 == tileToTouch && t3 == tileToTouch && t4 == tileToTouch) {
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        Random r = new Random();


        while (true) {

         /*   float[][] whiteNoise = generateWhiteNoise(50,50,r);

            float[][] perlin = generatePerlinNoise(whiteNoise,4);

            perlin = group(perlin);
*/

            int w = 50;
            int h = 50;

            BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            int[] pixels = new int[w * h];

            Level l = generateLevel(w, h,true,GenerationOptions.MIXED);


               float[][] perlin = l.getTiles();
               float[][] trees = l.getTrees();


            //float[][] perlin = generateForrest();
            for (int x = 0; x != perlin.length; x++) {
                for (int y = 0; y != perlin[0].length; y++) {
                    float p = perlin[x][y];

                    Color c = null;
                    if (p == 1) c = Color.blue;
                    if (p == 0.5) c = Color.yellow;
                    if (p == 0) c = Color.green;
                    pixels[x + y * w] = c.hashCode();
                      if(trees[x][y] == 1 && p == 0){
                         pixels[x+y*w] = Color.ORANGE.hashCode();
                     }
                }
            }

            pixels[l.spawn.x + l.spawn.y * w] = Color.black.hashCode();

            image.setRGB(0, 0, w, h, pixels, 0, w);

            JOptionPane.showMessageDialog(null, null, "Test", JOptionPane.YES_NO_OPTION, new ImageIcon(image.getScaledInstance(w * 10, h * 10, 0)));
        }

    }

    public static boolean corner(float[][] map, int x,int y){
        return true;
    }


    public static Color getColor(Color s, Color e, float t) {
        float u = 1 - t;


        int r = (int) (s.getRed() * u + e.getRed() * t);
        int g = (int) (s.getGreen() * u + e.getGreen() * t);
        int b = (int) (s.getBlue() * u + e.getBlue() * t);


        Color c = new Color(r, g, b);
        return c;
    }

}
