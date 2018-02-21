package n0277988.jazz.platformgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

import java.util.ArrayList;

import n0277988.jazz.platformgame.Constants;
import n0277988.jazz.platformgame.Wall;

/**
 * Created by jaros on 20/02/2018.
 */

public class Wall_Manager {
    private ArrayList <Wall> Level;
    private int gap;
    private int thickness;
    private int speed;


    public Wall_Manager(int gap, int thickness, int speed){
        this.gap = gap;
        this.thickness = thickness;
        this.speed = speed;

        Level = new ArrayList<>();

        CreateLevel();
    }

    public void CreateLevel(){
        int CreateY = -2*Constants.Screen_Height;
        while (CreateY < 0){
            int NewLeft = (int)(Math.random()* Constants.Screen_Width/4);
            int NewGap = 0;
            while (NewGap < 3*gap/4){
                NewGap = (int) (Math.random()*gap);
            }
            Level.add(new Wall(NewLeft, thickness, CreateY, NewGap, Color.YELLOW));
            CreateY += thickness;
        }
    }

    public void update(){
        for(Wall El : Level){
            El.move(speed);
        }
        if(Level.get(Level.size()-1).getTop() >= Constants.Screen_Height )
        {
            Level.remove(Level.size()-1);
            int NewLeft = (int)(Math.random()* Constants.Screen_Width/4);
            int NewGap = 0;
            while (NewGap < 3*gap/4){
                NewGap = (int) (Math.random()*gap);
            }
            Level.add(0, new Wall(NewLeft, thickness, Level.get(0).getTop() - thickness, NewGap, Color.YELLOW));
        }
    }

    public void draw(Canvas canvas){
        for(Wall El : Level)
        {
            El.draw(canvas);
        }
    }

    public boolean collisionCheck(GameCharacter Player){
        for(Wall El : Level)
        {
            if (El.playerCollision(Player))
            return true;
        }
        return false;
    }
}
