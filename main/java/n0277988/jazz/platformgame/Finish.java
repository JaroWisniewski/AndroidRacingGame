package n0277988.jazz.platformgame;

import android.graphics.Bitmap;

/**
 * Created by Jaroslaw on 23/02/2018.
 */

public class Finish extends Wall {

    public Finish(int LeftSide, int Thickness, int Top, int gap, int Color, Bitmap map) {
        super(LeftSide, Thickness, Top, gap, Color, map);
    }

    @Override
    public boolean isFinish() {
        return true;
    }


}
