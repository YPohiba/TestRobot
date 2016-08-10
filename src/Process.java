import java.util.ArrayList;

public class Process implements Runnable {

    MinusEnergy currentMinus;
    PlusEnergy currentPlus;
    Position position;
    String nameCurrent;
    ArrayList positionList;

    public Process(Position position, String nameCurrent, ArrayList positionList){
        this.position = position;
        this.nameCurrent = nameCurrent;
        this.positionList = positionList;
    }


    @Override
    public void run() {

        //synchronized (this){
        //Запуск потока, который отвечает за энергозатратность
        currentMinus = new MinusEnergy(position.robot);
        currentMinus.start();

        //Случайная стратегия
        if (position.robot.getNumberStrategy() == 1) {
            while (position.robot.isActive()) {
                if (position.robot.getEnergy() > 0 & position.robot.getEnergy() < 100) {
                    takeDevice();
                    if (position.robot.isLeftHand() & position.robot.isRightHand()) {
                        currentPlus = new PlusEnergy(position.robot);
                        currentPlus.start();
                        do {
                            try {
                                currentPlus.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } while (currentPlus.isAlive());
                        giveDevice("all");
                        try {
                            Thread.sleep(randomSleep(100, 300));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (position.robot.getEnergy() == 0) {
                            giveDevice("all");
                            return;
                        }
                    }
                } else if (position.robot.getEnergy() == 0) {
                    giveDevice("all");
                    return;
                }
            }
        //Жадная стратегия
        } else if (position.robot.getNumberStrategy() == 2) {
            while (position.robot.isActive()) {
                if (position.robot.getEnergy() > 0 & position.robot.getEnergy() < 60) {
                    takeDevice();
                    if (position.robot.isLeftHand() & position.robot.isRightHand()) {
                        while (position.robot.isActive()) {
                            currentPlus = new PlusEnergy(position.robot);
                            currentPlus.start();
                            do {
                                try {
                                    currentPlus.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } while (currentPlus.isAlive());
                        }
                    }
                } else if (position.robot.getEnergy() > 60) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (position.robot.getEnergy() == 0) {
                    giveDevice("all");
                    break;
                }
            }
        //Джентльменская стратегия
        } else {
            while (position.robot.isActive()) {
                takeDevice();
                if (position.robot.isLeftHand() & position.robot.isRightHand()) {
                    currentPlus = new PlusEnergy(position.robot);
                    currentPlus.start();
                    do {
                        try {
                            currentPlus.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } while (currentPlus.isAlive());
                }
                giveDevice(neighborEnergy());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int randomSleep(int min, int max) {

        max -= min;
        return (int) (Math.random() * ++max) + min;

    }

    public void takeDevice() {

        if (!position.left.isBusy()) {
            position.robot.setLeftHand(true);
            position.left.setBusy(true);
        }

        if (!position.right.isBusy()) {
            position.robot.setRightHand(true);
            position.right.setBusy(true);
        }
    }

    public void giveDevice(String amountDevice) {

        if (amountDevice.equals("all")) {
            if (position.robot.isLeftHand()) {
                position.robot.setLeftHand(false);
                position.left.setBusy(false);
            }
            if (position.robot.isRightHand()) {
                position.robot.setRightHand(false);
                position.right.setBusy(false);
            }

        } else if (amountDevice.equals("left")) {
            if (position.robot.isLeftHand()) {
                position.robot.setLeftHand(false);
                position.left.setBusy(false);
            }

        } else if (amountDevice.equals("right")) {
            if (position.robot.isRightHand()) {
                position.robot.setRightHand(false);
                position.right.setBusy(false);
            }
        }
    }

    public String neighborEnergy() {

        int left, right;

        if (position.numberPosition == 6) {
            left = 1;
            right = 5;
        } else {
            left = position.getNumberPosition() + 1;
            right = position.getNumberPosition() - 1;
        }

        for (int i = 0; i < positionList.size(); i++) {
            Position neighborPos = (Position) positionList.get(i);
            if (neighborPos.getNumberPosition() == left) {
                if (neighborPos.robot.getEnergy() < position.robot.getEnergy())
                    left = 1;
                else
                    left = 0;
            } else if (neighborPos.getNumberPosition() == right) {
                if (neighborPos.robot.getEnergy() < position.robot.getEnergy())
                    right = 1;
                else
                    right = 0;
            }
        }
        if (left + right == 2)
            return "all";
        else if (left == 1 & right == 0)
            return "left";
        else if (right == 1 & left == 0)
            return "right";
        else
            return "nobody";
    }
}