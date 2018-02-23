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
    private ArrayList <Boost> BoostLevel;
    private ArrayList <Wall> Level;
    private int Gap;
    private int LastGap;
    private int thickness;
    private int speed;
    private int Checkpoint;
    private int LastLeft;
    private int playerWidth;
    private boolean start; // Level time counter start
    private long lvlTime;
    private long startTime;


    public Wall_Manager(int gap, int thickness, int speed, GameCharacter player){
        this.Gap = gap;
        this.LastGap = gap;
        this.thickness = thickness;
        this.speed = speed;
        this.Checkpoint = 0;
        this.LastLeft = 10;
        this.playerWidth = player.getCharacter().width();
        this.start = false;
        this.lvlTime = 0;
        this.startTime = 0;

        Level = new ArrayList<>();

        BoostLevel = new ArrayList<>();

        CreateLevel();
    }


    public void CreateLevel(){
        int NewLeft = 0;
        int NewGap = 0;
        int CreateY = -2*Constants.Screen_Height;
        while (CreateY < 0){
            NewLeft = (int)(Math.random()* Constants.Screen_Width/4);
            NewGap = 0;
            while (NewGap < 3*Gap/4)
            {
                NewGap = (int) (Math.random()*Gap);
            }
            Level.add(new Wall(NewLeft, thickness, CreateY, NewGap, Color.YELLOW));

            Checkpoint += 1;
            CreateY += thickness;
        }
        BoostLevel.add(0, new Boost(NewLeft + 20, CreateY - 100, Color.BLUE));
    }

    public void update(){
        if(Level.get(Level.size()-1).getTop() >= Constants.Screen_Height )
        {
            if(Level.get(Level.size()-1).isFinish())
            {
                if(start) {
                    lvlTime = System.nanoTime() - startTime;
                    double Seconds = (double) lvlTime / 1000000000.0;
                    Log.d("Time-------------", Double.toString(Seconds));
                    start = false;
                }
            }
            else
            {
                if(!start) // Start timer
                    {
                        start = true;
                        startTime = System.nanoTime();
                    }

                Level.remove(Level.size() - 1);

                int NewLeft = -1;
                int NewGap = 0;

                // making sure that Left wall won't go outside the Screen bounds
                while (NewLeft < 0)
                {
                    NewLeft = LastLeft + (int) (Math.random() * playerWidth + 1) - (int) (playerWidth / 2);
                }

                // making Gap big enough for player
                while (NewGap < playerWidth * 2)
                {
                    NewGap = (int) (Math.random() * Gap);
                }

                if (Checkpoint == Constants.Finish)
                {
                    Level.add(0, new Finish(NewLeft, thickness, Level.get(0).getTop() - thickness, 0, Color.RED));
                    Checkpoint += 1;
                }
                else
                {
                    Level.add(0, new Wall(NewLeft, thickness, Level.get(0).getTop() - thickness, NewGap, Color.YELLOW));
                    Checkpoint += 1;
                }

                // ---------------  Decrease Gap ---------------------------
                if (Gap > (playerWidth * 2) + 10)
                {
                    Gap -= 10;
                }

                LastLeft = NewLeft;
                LastGap = NewGap;
            }
        }
        if (BoostLevel.get(0).getTop() >= Constants.Screen_Height )
        {
            BoostLevel.remove(BoostLevel.size()-1);
            BoostLevel.add(0, new Boost(LastLeft + 10 + (int) (Math.random() * (LastGap - 100)), Level.get(0).getTop(), Color.BLUE));
        }
        for(Wall El : Level)
        {
            El.move(speed);
        }
        for (Boost X : BoostLevel)
        {
            X.move(speed);
        }
    }

    public double getTime(){
        return lvlTime;
    }

    public void increaseSpeed()
    {
        speed += 3;
    }

    public void decreaseSpeed()
    {
        if(speed > 5)
        {
            speed -= 2;
        }
    }

    public void draw(Canvas canvas){
        for(Wall El : Level)
        {
            El.draw(canvas);
        }
        for (Boost X : BoostLevel)
        {
            X.draw(canvas);
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

    public boolean boostCheckO(GameCharacter Player){
        for(Boost El : BoostLevel)
        {
            if (El.playerCollision(Player))
                return true;
        }
        return false;
    }
}
