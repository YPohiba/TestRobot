
public class Position {

    int numberPosition;
    Device left;
    Device right;
    Robot robot;

    public Position(int numberPosition, Device left, Device right, Robot robot) {
        this.numberPosition = numberPosition;
        this.left = left;
        this.right = right;
        this.robot = robot;
    }

    public int getNumberPosition() {
        return numberPosition;
    }
}
