import java.util.*;

public class Main {
    private static final int[] threadCounts = {2, 4, 8, 16, 32, 64, 128};
    private static int N = 0;
    public static void main(String[] args) {
        try {
            setN();
            // Определение границ нужных чисел
            int startOfRange = (int) Math.pow(10, N - 1);
            int endOfRange = (int) Math.pow(10, N) - 1;
            //Класс с одним потоком исполнения
            double oneThreadResult = execOneThread(startOfRange, endOfRange);
            //Класс Thread
            Map<Integer, Double> extendsThreadResultMap = getThreadResultMap(startOfRange, endOfRange);
            //Класс Runnable
            Map<Integer, Double> implementsRunnableResultMap = getRunnableResultMap(startOfRange, endOfRange);
            //Вывод результатов
           printResultTable(oneThreadResult, extendsThreadResultMap, implementsRunnableResultMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printResultTable(double oneThread, Map<Integer, Double> extendsThreadResultMap, Map<Integer, Double> implementsRunnableResultMap) {
        // Заголовок таблицы
        System.out.println("+-------------------------------------------------+");
        System.out.println("|       Сравнительный анализ результатов          |");
        System.out.println("+--------+------------------+---------------------+");
        System.out.println("| Потоки |   Extends Thread | Implements Runnable |");
        System.out.println("|        |   Время (мс)     |   Время (мс)        |");
        System.out.println("+--------+------------------+---------------------+");

        System.out.printf("| 1      | %-16.4f | %-19.4f |\n", oneThread, oneThread);

        for (int threads : threadCounts) {
            double extendsTime = extendsThreadResultMap.getOrDefault(threads, 0.0);
            double runnableTime = implementsRunnableResultMap.getOrDefault(threads, 0.0);

            System.out.printf("| %-6d | %-16.4f | %-19.4f |\n", threads, extendsTime, runnableTime);
        }
        System.out.println("+--------+------------------+---------------------+");
        System.out.println("\nВывод:");
        System.out.println("На основании проведенных тестов можно сделать следующие выводы:\n" +
                "1. Многопоточность действительно значительно повышает производительность программы\n" +
                "2. Оптимальное количество потоков для данной задачи - около 8\n" +
                "3. Дальнейшее увеличение числа потоков не дает существенного прироста скорости,\n" +
                "   так как накладные расходы на создание и управление потоками начинают\n" +
                "   превышать выигрыш от параллелизации");

    }

    private static void setN() throws Exception {
        // Ввод N
        System.out.print("Введите разрядность чисел: ");
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        if (N > 10) {
            throw new Exception("Значение N больше 10! Выполнение программы невозможно! ");
        }
        if(N < 1) {
            throw new Exception("Значение N допустимо только в диапазоне [1;10]!");
        }
        sc.close();
    }
    private static double execOneThread(int startOfRange, int endOfRange) {
        //#######################################################################################
        //#################### Запуск для одного потока #########################################
        //######################################################################################
        OneThread oneThread = new OneThread(startOfRange, endOfRange, N);
        long startTime = System.nanoTime();
        oneThread.executeOneThread();
        long endTime = System.nanoTime();
        double durationOneThread = (endTime - startTime) / 1_000_000_000.0;
        //System.out.printf("Время выполнения (1 поток): %.4f сек.", durationOneThread);
        return durationOneThread;
    }
    private static Map<Integer, Double> getThreadResultMap(int startOfRange, int endOfRange) {
        //######################################################################################
        //################################ Класс extendsThread #################################
        //######################################################################################
        double startTime;
        double endTime;
        double duration;
        Map<Integer, Double> extendsThreadResultMap = new HashMap<>();
        double[] timeResults = new double[threadCounts.length];

        for (int t = 0; t < threadCounts.length; t++) {
            int currThreadCount = threadCounts[t];
            extendsThread[] threads = new extendsThread[currThreadCount];
            int step = (endOfRange - startOfRange + 1) / currThreadCount;

            // Сброс общих счетчиков перед запуском
            extendsThread.resetCounters();

            startTime = System.nanoTime();

            for (int i = 0; i < currThreadCount; i++) {
                int threadStart = startOfRange + i * step;
                int threadEnd = (i == currThreadCount - 1) ? endOfRange : threadStart + step - 1;

                threads[i] = new extendsThread(threadStart, threadEnd, N);
                threads[i].start();
            }

            for (extendsThread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            endTime = System.nanoTime();
            duration = (endTime - startTime) / 1_000_000_000.0;
            timeResults[t] = duration;
            extendsThreadResultMap.put(threadCounts[t], duration);

//            System.out.printf("\n Потоков: %d, Время выполнения: %.4f сек.", currThreadCount, duration);
//            System.out.println("Total Even: " + extendsThread.getTotalEven());
//            System.out.println("Total Odd: " + extendsThread.getTotalOdd());
//            System.out.println(extendsThreadResultMap);
        }
        return extendsThreadResultMap;
    }
    private static Map<Integer, Double> getRunnableResultMap(int startOfRange, int endOfRange) {
        //######################################################################################
        //################################ Класс implementsRunnable#################################
        //######################################################################################
        double startTime;
        double endTime;
        double duration;
        Map<Integer, Double> implementsRunnableRusultsMap = new HashMap<>();
        double[] timeResultsRunnable = new double[threadCounts.length];

        for (int t = 0; t < threadCounts.length; t++) {
            int currThreadCount = threadCounts[t];
            implementsRunnable[] tasks = new implementsRunnable[currThreadCount];
            Thread[] threads = new Thread[currThreadCount]; // Создаем массив потоков
            int step = (endOfRange - startOfRange + 1) / currThreadCount;

            // Сброс общих счетчиков перед запуском
            implementsRunnable.resetCounters();

            startTime = System.nanoTime();

            for (int i = 0; i < currThreadCount; i++) {
                int threadStart = startOfRange + i * step;
                int threadEnd = (i == currThreadCount - 1) ? endOfRange : threadStart + step - 1;

                tasks[i] = new implementsRunnable(threadStart, threadEnd, Main.N);
                threads[i] = new Thread(tasks[i]); // Оборачиваем в Thread
                threads[i].start(); // Запускаем поток
            }

            for (Thread thread : threads) {
                try {
                    thread.join(); // Ожидаем завершения всех потоков
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            endTime = System.nanoTime();
            duration = (endTime - startTime) / 1_000_000_000.0;
            timeResultsRunnable[t] = duration;
            implementsRunnableRusultsMap.put(threadCounts[t], duration);
//            System.out.println("Runnable");
//            System.out.printf("Потоков: %d, Время выполнения: %.4f сек.", currThreadCount, duration);
//            System.out.println("Total Even: " + implementsRunnable.getTotalEven());
//            System.out.println("Total Odd: " + implementsRunnable.getTotalOdd());
        }
        return  implementsRunnableRusultsMap;
    }
}
