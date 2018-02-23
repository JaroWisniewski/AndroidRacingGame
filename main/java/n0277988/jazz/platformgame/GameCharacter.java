package n0277988.jazz.platformgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by jaros on 16/02/2018.
 */

public class GameCharacter implements GameObject {

    private Bitmap character_bitmap;
    private Rect Character;
    private int color;

    public GameCharacter(Rect Rect, int Color, Bitmap bitmap)
    {
        character_bitmap = bitmap;
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
       // Paint CharColor = new Paint();
       // CharColor.setColor(color);
       // canvas.drawRect(Character, CharColor);
        canvas.drawBitmap(character_bitmap, Character.left, Character.top, null);
    }

    @Override
    public void update() {

    }

    public void update (Point point)    {
        Character.set(point.x - Character.width()/2, point.y - Character.height()/2, point.x + Character.width()/2, point.y + Character.height()/2);
    }
}
