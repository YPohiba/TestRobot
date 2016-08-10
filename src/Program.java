import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Program {

    static int strategy = 0;

    public static void main(String[] args) throws IOException {

        //Собираем входящие данные (номер стратегии), который будем хранить в роботе.
        int[] arr = new int[6];

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < 6;i++) {
            System.out.print("Select a strategy for the robot #" + (i+1) +" (1/2/3): ");
            while (true) {
                try {
                    strategy = Integer.parseInt(reader.readLine());
                }catch (NumberFormatException e){}
                if (strategy == 1 || strategy == 2 || strategy == 3) {
                    arr[i] = strategy;
                    strategy = 0;
                    break;
                }
                System.out.println("Wrong!");
                System.out.print("Select a strategy for the robot #" + (i+1) + " (1/2/3): ");
            }
        }

        //Лист для позиций (место робота за столом)
        ArrayList<Position> positionList = new ArrayList<Position>();

        //Создаем части зарядного устройства, которые будем хранить на позициях.
        Device fork1 = new Device("Fork1");
        Device cable1 = new Device("Cable1");
        Device fork2 = new Device("Fork2");
        Device cable2 = new Device("Cable2");
        Device fork3 = new Device("Fork3");
        Device cable3 = new Device("Cable3");

        //Создаем позиции и роботов. Садим роботов на свое место. Кладем части зар. устройства.
        //(Расположение мест использовалось как на картинке в задании. По часовой стрелке.(Самый вверхний первый.))
        positionList.add(new Position(1, fork1, cable1, new Robot(1, arr[0])));
        positionList.add(new Position(2, cable2, fork1, new Robot(2, arr[1])));
        positionList.add(new Position(3, fork2, cable2, new Robot(3, arr[2])));
        positionList.add(new Position(4, cable3, fork2, new Robot(4, arr[3])));
        positionList.add(new Position(5, fork3, cable3, new Robot(5, arr[4])));
        positionList.add(new Position(6, cable1, fork3, new Robot(6, arr[5])));

        //Лист для копии листа с позициями, для контроля завершения моделирования
        ArrayList<Position> controlEnergyList = new ArrayList<Position>();
        controlEnergyList.addAll(positionList);

        //Два листа, для хранения роботов с полным зарядом и наоборот
        ArrayList<Robot> fullEnergyList = new ArrayList<Robot>();
        ArrayList<Robot> endedEnergyList = new ArrayList<Robot>();

        //Запуск потоков
        for (Position list: positionList){
            Thread current = new Thread(new Process(list,"Current "+list.numberPosition, positionList));
            current.start();
        }

        //Контроль завершения моделирования в главном потоке
        while (true){
            int i = 0;
            String result = "";
            for ( ; i < controlEnergyList.size(); i++){
                if (controlEnergyList.get(i).robot.getEnergy() == 0) {
                    endedEnergyList.add(controlEnergyList.get(i).robot);
                    controlEnergyList.remove(controlEnergyList.get(i));
                }else if (controlEnergyList.get(i).robot.getEnergy() == 100){
                    fullEnergyList.add(controlEnergyList.get(i).robot);
                    controlEnergyList.remove(controlEnergyList.get(i));
                }
            }
            if (controlEnergyList.isEmpty()) {
                if (endedEnergyList.size() == 3 & fullEnergyList.size() == 3)
                    result = "3+/3-";
                else if (endedEnergyList.size() == 6)
                    result = "6-";
                else if (fullEnergyList.size() == 6)
                    result = "6+";

                if (!result.isEmpty()) {
                    for (i = 0; i < fullEnergyList.size(); i++)
                        fullEnergyList.get(i).setActive(false);
                    for (i = 0; i < endedEnergyList.size(); i++)
                        endedEnergyList.get(i).setActive(false);

                    System.out.println("The end." + " (" + result + ")");
                    break;
                }
            }
        }
    }
}
