import java.util.*;

public class Main {
    private static int N = 0;
    public static void main(String[] args) {
        try {
            //Ввод N
            System.out.print("Введите разрядность чисел: ");
            Scanner sc = new Scanner(System.in);
            N = sc.nextInt();
            sc.close();
            //Определение границ нужных чисел
            int startOfRange = (int) Math.pow(10, N - 1);
            int endOfRange = (int) Math.pow(10, N) - 1;

            //#######################################################################################
            //#################### Запуск для одного потока #########################################

            OneThread oneThread = new OneThread(startOfRange, endOfRange, N);
            long startTime = System.nanoTime();
            String firstResult = oneThread.executeOneThread();
            long endTime = System.nanoTime();
            double duration = (endTime - startTime)/1_000_000_000.0;
            System.out.println(String.format("Время выполнения: %.4f сек.", duration));
            System.out.println(firstResult);

            //######################################################################################
            //################################ Класс extendsThread #################################
            //######################################################################################

            int[] threadCounts = {2, 4, 8, 16, 32, 64, 128};
            double[] timeResults = new double[threadCounts.length];
            extendsThread[] threads = new extendsThread[threadCounts.length];
            int step = (endOfRange - startOfRange + 1) / N;
            for (int currThreadCount : threadCounts) {
                startTime = System.nanoTime();
                for (int i = 0; i < currThreadCount; i++) {
                    int threadStart = startOfRange + i * step;
                    int threadEnd = (i == N - 1) ? endOfRange: threadStart + step - 1;
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
                duration = (endTime - startTime)/1_000_000_000.0;
                timeResults[currThreadCount] = duration;
                System.out.println("Total Even: " + extendsThread.getTotalEven());
                System.out.println("Total Odd: " + extendsThread.getTotalOdd());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}