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
    private int Checkpoint;
    private int LastLeft;
    private int playerWidth;
    private boolean start;
    private long Time;


    public Wall_Manager(int gap, int thickness, int speed, GameCharacter player){
        this.Gap = gap;
        this.thickness = thickness;
        this.speed = speed;
        this.Checkpoint = 0;
        this.LastLeft = 10;
        this.playerWidth = player.getCharacter().width();
        this.start = false;
        this.Time = 0;

        Level = new ArrayList<>();

        CreateLevel();
    }


    public void CreateLevel(){
        int CreateY = -2*Constants.Screen_Height;
        while (CreateY < 0){
            int NewLeft = (int)(Math.random()* Constants.Screen_Width/4);
            int NewGap = 0;
            while (NewGap < 3*Gap/4){
                NewGap = (int) (Math.random()*Gap);
            }
            Level.add(new Wall(NewLeft, thickness, CreateY, NewGap, Color.YELLOW));
            Checkpoint += 1;
            CreateY += thickness;
        }
    }

    public void update(){
        if(Level.get(Level.size()-1).getTop() >= Constants.Screen_Height )
        {
            if(Level.get(Level.size()-1).isFinish())
            {
                if(start) {
                    Time = System.nanoTime() - Time;
                    double Seconds = (double) Time / 1000000000.0;
                    Log.d("Time-------------", Double.toString(Seconds));
                    start = false;
                }
            }
            else
            {
                if(!start)
                {
                start = true;
                Time = System.nanoTime();
                }

                Level.remove(Level.size() - 1);
                int NewLeft = LastLeft + (int) (Math.random() * playerWidth + 1) - (int) (playerWidth / 2);
                while (NewLeft < 0) {
                    NewLeft = LastLeft + (int) (Math.random() * playerWidth + 1) - (int) (playerWidth / 2);
                }
                int NewGap = 0;
                while (NewGap < playerWidth * 2) {
                    NewGap = (int) (Math.random() * Gap);
                }
                if (Checkpoint >= Constants.Finish) {
                    Level.add(0, new Finish(NewLeft, thickness, Level.get(0).getTop() - thickness, 0, Color.RED));
                }
                else
                {
                    Level.add(0, new Wall(NewLeft, thickness, Level.get(0).getTop() - thickness, NewGap, Color.YELLOW));
                    Checkpoint += 1;
                }
                Log.d("Screen", Integer.toString(Constants.Screen_Width));
                if (Gap > (playerWidth * 2) + 10) {
                    Gap -= 10;
                }
                LastLeft = NewLeft;
                Log.d("D", Integer.toString(LastLeft));
            }
        }
        for(Wall El : Level){
            El.move(speed);
        }
    }

    public void increaseSpeed()
    {
        speed += 2;
    }

    public void decreaseSpeed()
    {
        if(speed > 5){
        speed -= 2;
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
