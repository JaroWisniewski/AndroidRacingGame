package n0277988.jazz.platformgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by jaros on 16/02/2018.
 */

public class Obstacle implements GameObject {
    private Rect Obstacle;
    private int color;

    public Obstacle(Rect Rect, int Color) {
        this.Obstacle = Rect;
        this.color = Color;
    }

    public boolean playerCollision(GameCharacter player){
        if(Obstacle.contains(player.getCharacter().left, player.getCharacter().top) ||
                Obstacle.contains(player.getCharacter().right, player.getCharacter().top) ||
                Obstacle.contains(player.getCharacter().left, player.getCharacter().bottom) ||
                Obstacle.contains(player.getCharacter().right, player.getCharacter().bottom))
        {
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint ObstacleColor = new Paint();
        ObstacleColor.setColor(color);
        canvas.drawRect(Obstacle, ObstacleColor);
    }

    @Override
    public void update() {

    }

    public void update(Point point){
        Obstacle.set(point.x - Obstacle.width()/2, point.y - Obstacle.height()/2, point.x + Obstacle.width()/2, point.y + Obstacle.height()/2);
    }
}
