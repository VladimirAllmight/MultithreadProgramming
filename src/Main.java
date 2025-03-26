import java.util.*;

public class Main {
    private static final int[] threadCounts = {2, 4, 8, 16, 32, 64, 128};
    public static void main(String[] args) {
        try {
            // Ввод N
            System.out.print("Введите разрядность чисел: ");
            Scanner sc = new Scanner(System.in);
            int n = sc.nextInt();
            sc.close();

            // Определение границ нужных чисел
            int startOfRange = (int) Math.pow(10, n - 1);
            int endOfRange = (int) Math.pow(10, n) - 1;

            //#######################################################################################
            //#################### Запуск для одного потока #########################################

            OneThread oneThread = new OneThread(startOfRange, endOfRange, n);
            long startTime = System.nanoTime();
            String firstResult = oneThread.executeOneThread();
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1_000_000_000.0;
            System.out.printf("Время выполнения (1 поток): %.4f сек.%n", duration);
            System.out.println(firstResult);

            //######################################################################################
            //################################ Класс extendsThread #################################
            //######################################################################################


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

                    threads[i] = new extendsThread(threadStart, threadEnd, n);
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

                System.out.printf("Потоков: %d, Время выполнения: %.4f сек.%n", currThreadCount, duration);
                System.out.println("Total Even: " + extendsThread.getTotalEven());
                System.out.println("Total Odd: " + extendsThread.getTotalOdd());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
