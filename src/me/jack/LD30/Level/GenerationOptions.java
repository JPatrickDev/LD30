package me.jack.LD30.Level;

/**
 * Created by Jack on 24/08/2014.
 */
public class GenerationOptions {


    public boolean generateTrees;
    public int octave_count;
    public float persistence;


    public float water_start = 0.5f;
    public float water_end = 1.0f;

    public float sand_start = 0.45f;
    public float sand_end = 0.49f;

    public float grass_start = 0.44f;
    public float grass_end = 0.0f;

    public static GenerationOptions WATER = new GenerationOptions(false,4, 0.5f,0.3f,1.0f,0.25f,0.29f,0.0f,0.24999f);

    public static GenerationOptions LARGE_ISLANDS = new GenerationOptions(false,5,0.5f,0.7f,1.0f, 0.6f,0.69f,0.0f,0.59f);

    public static GenerationOptions BEACHES = new GenerationOptions(false,4,0.5f,0.5f,1f,0.25f,0.49f,0.0f,0.25f);

    public static GenerationOptions MIXED= new GenerationOptions(false,4,0.5f,0.55f,1f, 0.45f,0.549f,0.0f,0.449f);

    public GenerationOptions(boolean generateTrees, int octave_count, float persistence, float water_start, float water_end,
                             float sand_start, float sand_end, float grass_start, float grass_end) {
        this.generateTrees = generateTrees;
        this.octave_count = octave_count;
        this.persistence = persistence;
        this.water_start = water_start;
        this.water_end = water_end;
        this.sand_start = sand_start;
        this.sand_end = sand_end;
        this.grass_start = grass_start;
        this.grass_end = grass_end;
    }
}
