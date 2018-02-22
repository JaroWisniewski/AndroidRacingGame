package n0277988.jazz.platformgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

import n0277988.jazz.platformgame.Constants;
import n0277988.jazz.platformgame.Wall;

/**
 * Created by jaros on 20/02/2018.
 */

public class Wall_Manager {
    private ArrayList <Wall> Level;
    private int Gap;
    private int thickness;
    private int speed;
    private int LastGap;
    private int LastLeft;
    private int playerWidth;


    public Wall_Manager(int gap, int thickness, int speed, GameCharacter player){
        this.Gap = gap;
        this.thickness = thickness;
        this.speed = speed;
        this.LastGap = Gap;
        this.LastLeft = 10;
        this.playerWidth = player.getCharacter().width();

        Level = new ArrayList<>();

        CreateLevel();
    }


    public void CreateLevel(){
        int CreateY = -2*Constants.Screen_Height;
        int NewGap = LastGap + (int) (Math.random()*101 )- 50; //TODO Check wall spawning Left Side
        int NewLeft = LastLeft + (int) (Math.random()*101 )- 50;

        while (CreateY < 0) {
            while (NewGap < 3 * Gap / 4) {
                NewGap = LastGap + (int) (Math.random() * 50) - 50;
            }
            while (NewLeft < 0 || NewLeft + NewGap > Constants.Screen_Width){
                NewLeft = LastLeft + (int) (Math.random() * 101) - 50;}
            Log.d("New Left", Integer.toString(NewLeft));//TODO Remove all Logs
            Log.d("New Gap", Integer.toString(NewGap));
            Level.add(new Wall(NewLeft, thickness, CreateY, NewGap, Color.YELLOW));
            CreateY += thickness;
        }
        LastLeft = NewLeft;
        LastGap = NewGap;
    }

    public void update() {
        int NewLeft = LastLeft;
        int NewGap = LastGap;
        for (Wall El : Level) {
            El.move(speed);
        }
        if (Level.get(Level.size() - 1).getTop() >= Constants.Screen_Height) {
            Level.remove(Level.size() - 1);
            if (Math.random() > 0.2) {
                if (LastGap <= playerWidth*2){
                LastGap = Gap;
                }
                NewGap = LastGap + (int) (Math.random() * 101) - 50;
                while (NewGap < 3 * Gap / 4) {
                    NewGap = LastGap + (int) (Math.random() * 101) - 50;
                }
                while (NewLeft < 0 || NewLeft + NewGap > Constants.Screen_Width) {
                    NewLeft = LastLeft + (int) (Math.random() * 101) - 50;
                }
                Log.d("New Left", Integer.toString(NewLeft));
                Log.d("New Gap", Integer.toString(NewGap));
                Level.add(0, new Wall(NewLeft, thickness, Level.get(0).getTop() - thickness, NewGap, Color.YELLOW));
            }
            else
            {

                while (NewGap < playerWidth || NewGap > (playerWidth * 2) ) { // TODO Widen the gap
                    NewGap = (int) (Math.random() * Gap);
                    Log.d("New Gap Hole", Integer.toString(NewGap));
                }
                while (NewLeft < 0 || NewLeft + NewGap > Constants.Screen_Width) {
                    NewLeft = LastLeft + (int) (Math.random() * 101) - 50;
                }
                Log.d("New Left Hole", Integer.toString(NewLeft));

                Level.add(0, new Wall(NewLeft, thickness, Level.get(0).getTop() - thickness, NewGap, Color.YELLOW));
            }
        }
        LastGap = NewGap;
        LastLeft = NewLeft;
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
