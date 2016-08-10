
public class MinusEnergy extends Thread {

    Robot robot;

    public MinusEnergy(Robot robot){
        this.robot = robot;
    }

    @Override
    public void run() {
        //synchronized (this) {
            while (robot.isActive()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (robot.getEnergy() > 0 & robot.isActive()) {
                    robot.minusEnergy();
                    System.out.println(robot.toString() + " (-)");
                } else
                    break;
            }
            if (robot.getEnergy() == 0)
                System.out.println("Robot #"+robot.getNumber() + ": Energy ended. Robot off.");
        //}
    }
}
