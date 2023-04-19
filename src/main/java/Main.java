
    /*
    Напишите программу, которая создает два потока,
    каждый из которых выводит на экран числа от 1 до 10.
    Но при этом первый поток должен выводить только четные числа, а второй - только нечетные.
     */
    public class Main {
        public static void main(String[] args) throws InterruptedException {
            Thread oneThread = new Thread(new OnePrinter());
            Thread twoThread = new Thread(new TwoPrinter());

            oneThread.start();
//            twoThread.join(); // (задание 2) Если нужно сначала из одного, потом из другого
            twoThread.start();
        }
    }

    class OnePrinter implements Runnable {
        @Override
        public void run() {
            for (int i = 2; i <= 10; i += 2) {
                System.out.println("Первый поток: " + i);
            }
        }
    }

    class TwoPrinter implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 9; i += 2) {
                System.out.println("Второй поток: " + i);
            }
        }
    }
