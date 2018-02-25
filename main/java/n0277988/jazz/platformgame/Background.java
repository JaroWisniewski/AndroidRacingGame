package n0277988.jazz.platformgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by Jaroslaw on 24/02/2018.
 */

public class Background implements GameObject {
    private Bitmap Background;
    private int y = 0;

    public Background (Bitmap map)
    {
        this.Background = map;
    }

    @Override
    public void draw(Canvas canvas) {
        if (y > Constants.Screen_Height)
        {
            y -= Background.getHeight();
        }
        int x = y;
        while (x > 0 - Background.getHeight()) {
            canvas.drawBitmap(Background, 0, x, null);
            x -= Background.getHeight();
       }
    }

    @Override
    public void update() {
    }

    public void move(int speed)
    {
        y += speed;
    }
}
