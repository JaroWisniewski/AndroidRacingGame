package n0277988.jazz.platformgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Jaroslaw on 23/02/2018.
 */

public class Finish extends Wall {

    public Finish(int LeftSide, int Thickness, int Top, int gap, int Color, Bitmap map) {
        super(LeftSide, Thickness, Top, gap, Color, map);

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
    }/*
    }

}


    public void move(float speed) {
        Left.top += speed;
        Left.bottom += speed;
        Right.top += speed;
        Right.bottom += speed;


    @Override
    public boolean isFinish() {
        return true;
    }

*/
}
