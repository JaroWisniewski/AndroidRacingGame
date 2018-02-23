package n0277988.jazz.platformgame;

/**
 * Created by Jaroslaw on 23/02/2018.
 */

public class Finish extends Wall {

    public Finish(int LeftSide, int Thickness, int Top, int gap, int Color) {
        super(LeftSide, Thickness, Top, gap, Color);
    }

    @Override
    public boolean isFinish() {
        return true;
    }


}
