import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/*
Создайте программу, которая будет имитировать гонки на машине.

У вас есть две машины, каждая из которых может двигаться со случайной скоростью в диапазоне от 1 до 5 метров в секунду.

Заезд должен начинаться одновременно для обеих машин, и победитель определяется той машиной, которая пройдет заранее заданное расстояние.
 */

public class CarRacing {

    /*
    В этом коде создаются два потока, каждый из которых выполняет бесконечный цикл,
    пока обе машины не достигнут заданной дистанции.
    Каждый поток отвечает за движение своей машины.
    В конце программа выводит сообщение о победителе, как и в предыдущем примере.
     */

    public static void main(String[] args) {
        final int DISTANCE = 60; // заданное расстояние
        final int MAX_SPEED = 5; // максимальная скорость машины
        final int MIN_SPEED = 1; // минимальная скорость машины



        AtomicReference<Integer> car1Distance = new AtomicReference<>(0); // расстояние, которое пройдет первая машина !Используем атомик!
        AtomicReference<Integer> car2Distance = new AtomicReference<>(0); // расстояние, которое пройдет вторая машина

        Random rand = new Random();

        int car1Speed = rand.nextInt(MAX_SPEED - MIN_SPEED + 1) + MIN_SPEED; // скорость первой машины
        int car2Speed = rand.nextInt(MAX_SPEED - MIN_SPEED + 1) + MIN_SPEED; // скорость второй машины

        System.out.println("Гонка началась!");

        Thread car1Thread = new Thread(() -> {
            while (car1Distance.get() < DISTANCE && car2Distance.get() < DISTANCE) {
                car1Distance.updateAndGet(v -> v + car1Speed);

                System.out.println("Машина 1 преодолела расстояние " + car1Distance + " метров со скоростью " + car1Speed + " м/с");

                try {
                    Thread.sleep(500); // задержка в пол секунды для наглядности
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread car2Thread = new Thread(() -> {
            while (car1Distance.get() < DISTANCE && car2Distance.get() < DISTANCE) {
                car2Distance.updateAndGet(v -> v + car2Speed);

                System.out.println("Машина 2 преодолела расстояние " + car2Distance + " метров со скоростью " + car2Speed + " м/с");

                try {
                    Thread.sleep(1000); // задержка в 1 секунду для наглядности
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        car1Thread.start();
        car2Thread.start();

        try {
            car1Thread.join();
            car2Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (car1Distance.get() >= DISTANCE && car2Distance.get() >= DISTANCE) {
            System.out.println("Гонка окончилась вничью!");
        } else if (car1Distance.get() >= DISTANCE) {
            System.out.println("Машина 1 победила!");
        } else {
            System.out.println("Машина 2 победила!");
        }
    }
}