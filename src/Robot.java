
public class Robot{

    int numberStrategy = 1;
    int number;
    boolean active = true;
    volatile int energy = 50;
    volatile boolean leftHand = false;
    volatile boolean rightHand = false;

    public Robot(int number, int numberStrategy) {
        this.number = number;
        this.numberStrategy = numberStrategy;
    }

    public int getEnergy() {
        return energy;
    }

    public void minusEnergy() {
        this.energy = this.energy - 10;
    }

    public void plusEnergy() {
        this.energy = this.energy + 10;
    }

    public boolean isLeftHand() {
        return leftHand;
    }

    public void setLeftHand(boolean leftHand) {
        this.leftHand = leftHand;
    }

    public boolean isRightHand() {
        return rightHand;
    }

    public void setRightHand(boolean rightHand) {
        this.rightHand = rightHand;
    }

    public int getNumberStrategy() {
        return numberStrategy;
    }

    public int getNumber() {
        return number;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Robot #" + number +
                " Energy = "+energy+" (LHand-"+leftHand +"; RHand-"+rightHand+")";
    }
}
