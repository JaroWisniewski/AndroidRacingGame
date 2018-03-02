package n0277988.jazz.platformgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by jaros on 16/02/2018.
 *
 * Game Character Object. Responsible for collision control, character texture, movement, and drawing
 */

public class GameCharacter implements GameObject {

    private Bitmap character_bitmap;
    private Rect Character;

    public GameCharacter(Rect Rect, Context context)
    {
        Bitmap CharTextSmall = BitmapFactory.decodeResource(context.getResources(), R.drawable.car);
        character_bitmap = Bitmap.createScaledBitmap(CharTextSmall, 180, 180, true);
        this.Character = Rect;
    }

    public Rect getCharacter(){
        return Character;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(character_bitmap, Character.left, Character.top, null);
    }

    @Override
    public void update() {

    }

    public void update (Point point)    {
        Character.set(point.x - Character.width()/2, point.y - Character.height()/2, point.x + Character.width()/2, point.y + Character.height()/2);
    }
}
