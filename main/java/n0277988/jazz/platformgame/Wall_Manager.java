package n0277988.jazz.platformgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

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
    private boolean start;// Level time counter start
    private boolean levelFinished;// levelFinished (go to score Screen
    private long lvlTime;
    private long startTime;
    private Bitmap wall;
    private Bitmap finishMap;
    private Bitmap arrow;
    private boolean pause;
    private SoundPlayer Sp;


    public Wall_Manager(int gap, int thickness, int speed, GameCharacter player, Bitmap wall, Bitmap finish, Bitmap boost, SoundPlayer sp){
        this.wall = wall;
        this.finishMap = finish;
        this.arrow = boost;
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
        this.levelFinished = false;
        this.pause = false;
        this.Sp = sp;

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
            Level.add(new Wall(NewLeft, thickness, CreateY, NewGap, wall));

            Checkpoint += 1;
            CreateY += thickness;
        }
        BoostLevel.add(0, new Boost(NewLeft + 20, CreateY - 100, arrow));
    }

    public void update(){
        if(Level.get(Level.size()-1).isFinish() && Level.get(Level.size()-1).getTop() > 3*Constants.Screen_Height/4 && start)
        {
            lvlTime = System.nanoTime() - startTime;
            double Seconds = (double) lvlTime / 1000000000.0;
            Log.d("Time-------------", Double.toString(Seconds));
            start = false;
            levelFinished = true;
            Sp.playCheer();
        }
        if(Level.get(Level.size()-1).getTop() >= Constants.Screen_Height )
        {
               if(!start && !levelFinished) // Start timer
                    {
                        start = true;
                        startTime = System.nanoTime();
                    }

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
                    Level.add(0, new Finish(Constants.Screen_Width, thickness, Level.get(0).getTop() - thickness, 0, Color.RED, finishMap));
                    Checkpoint += 1;
                }
                else if(Level.get(Level.size() - 1).isFinish())
                {
                    Level.remove((Level.size() - 1));
                    Level.add(new Wall(NewLeft, thickness, Level.get(0).getTop(), NewGap, wall));
                }
                else
                {
                    Level.get(Level.size() - 1).modify(NewLeft, thickness, Level.get(0).getTop() - thickness, NewGap);
                    Collections.rotate(Level, 1);
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

        if (BoostLevel.get(0).getTop() >= Constants.Screen_Height )
        {
            BoostLevel.get(BoostLevel.size()-1).modify(LastLeft + 10 + (int) (Math.random() * (LastGap - 100)), Level.get(0).getTop() + thickness/2);

            Collections.rotate(BoostLevel, 1);
        }
        for(Wall El : Level)
        {
            El.move(speed);
        }
        for (Boost X : BoostLevel)
        {
            X.move(speed);
        }
        if (levelFinished)
        {
            speed -= speed > 10 ? 10 : 0;
        }
        else if (speed < 50)
        {speed++;}
    }

    public double getTime(){
        return lvlTime;
    }


    public void increaseSpeed()
    {
        speed += 10;
        Sp.playBoost();
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

    public boolean boostCheck(GameCharacter Player){
        for(Boost El : BoostLevel)
        {
            if (El.playerCollision(Player))
                return true;
        }
        return false;
    }

    public int getSpeed()
    {
        return speed;
    }

    public double getLvlTime(){
        lvlTime = System.nanoTime() - startTime;
        double Seconds =  Math.round((double) lvlTime / 10000000.0)/100.0d;
        return Seconds;
    }

    public boolean isStart(){
        return start;
    }

    public boolean isLevelFinished(){
        return levelFinished;
    }
}
