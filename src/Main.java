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

            //#######################################################################################
            //#################### Запуск для одного потока #########################################
            //######################################################################################
            OneThread oneThread = new OneThread(startOfRange, endOfRange, N);
            long startTime = System.nanoTime();
            oneThread.executeOneThread();
            long endTime = System.nanoTime();
            double durationOneThread = (endTime - startTime) / 1_000_000_000.0;
            System.out.printf("Время выполнения (1 поток): %.4f сек.", durationOneThread);

            //######################################################################################
            //################################ Класс extendsThread #################################
            //######################################################################################
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
                double duration = (endTime - startTime) / 1_000_000_000.0;
                timeResults[t] = duration;
                extendsThreadResultMap.put(threadCounts[t], duration);

                System.out.printf("\n Потоков: %d, Время выполнения: %.4f сек.", currThreadCount, duration);
                System.out.println("Total Even: " + extendsThread.getTotalEven());
                System.out.println("Total Odd: " + extendsThread.getTotalOdd());
                System.out.println(extendsThreadResultMap);
            }

            //######################################################################################
            //################################ Класс implementsRunnable#################################
            //######################################################################################

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
                double duration = (endTime - startTime) / 1_000_000_000.0;
                Map<Integer, Double> implementsRunnableRusultsMap = new HashMap<>();
                timeResultsRunnable[t] = duration;
                implementsRunnableRusultsMap.put(threadCounts[t], duration);
                System.out.println("Runnable");
                System.out.printf("Потоков: %d, Время выполнения: %.4f сек.", currThreadCount, duration);
                System.out.println("Total Even: " + implementsRunnable.getTotalEven());
                System.out.println("Total Odd: " + implementsRunnable.getTotalOdd());
            }

           //printResultTable(durationOneThread, extendsThreadResultMap, im);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printResultTable(int oneThread, Map<Integer, Double> extendsThreadResultMap, Map<Integer, Double> implementsRunnableResultMap) {
        System.out.println(String.format("#%15Сравнительный анализ результатов%15#"));

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
}
