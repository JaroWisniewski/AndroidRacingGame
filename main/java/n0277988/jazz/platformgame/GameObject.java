package n0277988.jazz.platformgame;

import android.graphics.Canvas;

/**
 * Created by jaros on 16/02/2018.
 *
 * Interface for Game Objects
 */

public interface GameObject {
    public void draw(Canvas canvas);

    public void update();
}
