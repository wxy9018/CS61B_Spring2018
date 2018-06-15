package byog.Core;

public class Position {
    int xPos;
    int yPos;
    public Position(int x, int y) {
        xPos = x;
        yPos = y;
    }
    public boolean equal(Position compare) {
        if (this.xPos == compare.xPos && this.yPos == compare.yPos) {
            return true;
        }
        return false;
    }
}
