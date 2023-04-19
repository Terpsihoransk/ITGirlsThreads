import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
Создайте программу, которая считает сумму квадратов первых N натуральных чисел, используя многопоточность.
 */

    /*
     SumOfSquares создает пул потоков с помощью класса ExecutorService, используя метод newFixedThreadPool().
     Затем программа разбивает задачу на несколько частей, где каждый поток вычисляет сумму квадратов чисел в своем диапазоне.
     Каждый поток вычисляет локальную сумму и добавляет ее к общей сумме с помощью синхронизации. После того, как все потоки завершены, программа выводит общую сумму квадратов на экран.
     В этой программе мы используем статическую переменную sum, чтобы хранить общую сумму квадратов.
     Мы синхронизируем доступ к этой переменной, чтобы избежать гонок данных между потоками.
     */

public class SumOfSquares {


    public static void main(String[] args) throws InterruptedException {
        int n = 10; // количество натуральных чисел, для которых нужно вычислить сумму квадратов
        int threads = 4; // сколько потоков будет использоваться в пуле потоков

        ExecutorService executor = Executors.newFixedThreadPool(threads); // создаем экзекьютор с потоками

        int chunkSize = n / threads; // сколько чисел будет обрабатываться каждым потоком
        int start = 1; // начальное значение для первого блока
        int end = start + chunkSize - 1; // конечное

        long sum = 0;

        for (int i = 0; i < threads; i++) { // цикл запускает потоки
            if (i == threads - 1) {
                end = n;
            }

            SumOfSquaresTask task = new SumOfSquaresTask(start, end); // реализует Rannable и принимает значения блока
            executor.execute(task); // старт задачи

            start = end + 1;
            end = start + chunkSize - 1;
        }

        executor.shutdown(); // после того, как потоки завершают работу - закрываем потоки
        executor.awaitTermination(1, TimeUnit.MINUTES); // чтобы дождаться окончания работы всех потоков

        sum = SumOfSquaresTask.getSum(); // получение результата

        System.out.println("Сумма квадратов первых " + n + " натуральных чисел " + sum);
    }
}

class SumOfSquaresTask implements Runnable { // а тут логика вычисления
    private int start;
    private int end;
    private static long sum = 0;

    public SumOfSquaresTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() { // вычисление суммы для блока чисел, которые переданы в конструктор
        long localSum = 0;

        for (int i = start; i <= end; i++) {
            localSum += i * i; // результат сохраним в переменной
        }

        synchronized (SumOfSquaresTask.class) {
            sum += localSum; // затем добавим к общей сумме, которая объявлена как static и синхронизирована по классу SumOfSquaresTask
        }
    }

    public static long getSum() { // возвращение общей суммы
        return sum;
    }
}
