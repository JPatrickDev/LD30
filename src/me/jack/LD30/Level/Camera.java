package me.jack.LD30.Level;

/**
 * Created by Jack on 23/08/2014.
 */
public class Camera {


    private int offsetMaxX;
    private int offsetMaxY;
    private int offsetMinX;
    private int offsetMinY;

    private int w,h;

    public int x = 0;
    public int y = 0;


    public Camera(int worldWidth,int worldHeight,int tileSize,int viewport_w,int viewport_h){
        this.offsetMaxX = (worldWidth*tileSize) - viewport_w;
        this.offsetMaxY = (worldHeight*tileSize) - viewport_h;
        offsetMinX = 0;
        offsetMinY = 0;
        this.w = viewport_w;
        this.h = viewport_h;
    }


    public void calculate(int tx,int ty){
        x = tx - w/2;
        y = ty - h/2;

        if(x > offsetMaxX)x = offsetMaxX;
        else if(x < offsetMinX)x = offsetMinX;

        if(y > offsetMaxX)y = offsetMaxY;
        else if(y < offsetMinY)y = offsetMinY;
    }




}
