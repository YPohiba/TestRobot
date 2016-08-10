
public class PlusEnergy extends Thread {

    Robot robot;

    public PlusEnergy(Robot robot){
        this.robot = robot;
    }

    @Override
    public void run() {
        //synchronized (this){
            if (robot.isLeftHand() & robot.isRightHand()
                    & robot.getEnergy() > 0 & robot.isActive()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (robot.getEnergy() > 0 & robot.getEnergy() < 100) {
                    robot.plusEnergy();
                    System.out.println(robot.toString() + " (+)");
                }
            }
        //}
    }
}
