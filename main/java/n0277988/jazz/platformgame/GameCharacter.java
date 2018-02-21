package n0277988.jazz.platformgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by jaros on 16/02/2018.
 */

public class GameCharacter implements GameObject {

    private Rect Character;
    private int color;

    public GameCharacter(Rect Rect, int Color)
    {
        this.Character = Rect;
        this.color = Color;
    }

    public Rect getCharacter(){
        return Character;
    }

    public void setColor(int clr){
        this.color = clr;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint CharColor = new Paint();
        CharColor.setColor(color);
        canvas.drawRect(Character, CharColor);
    }

    @Override
    public void update() {

    }

    public void update (Point point)    {
        Character.set(point.x - Character.width()/2, point.y - Character.height()/2, point.x + Character.width()/2, point.y + Character.height()/2);
    }

}
