package n0277988.jazz.platformgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Jaroslaw on 23/02/2018.
 *
 * Finish Child Class representing finish Line
 */

public class Finish extends Wall {

    public Finish(int LeftSide, int Thickness, int Top, int gap, int Color, Bitmap map) {
        super(LeftSide, Thickness, Top, gap, map);

    }

    @Override
    public void draw(Canvas canvas) {
        int x;
        int y = Left.top;
        while (y < Left.bottom - wall_texture.getHeight())
        {
            x = Left.right;
            while (x >= (0-wall_texture.getWidth()))
            {
                canvas.drawBitmap(wall_texture, x - wall_texture.getWidth(), y, null);
                x -= wall_texture.getWidth();
            }
            y += wall_texture.getHeight();
        }


        }

    @Override
    public boolean isFinish() {
        return true;
    }

    //No collision
    @Override
    public boolean playerCollision(GameCharacter player) {
        return false;
    }

}
