public class extendsThread extends Thread {
    private int evenCount = 0;
    private int oddCount = 0;
    private final int start;
    private final int end;
    private final int N;

    // Глобальные переменные для суммирования результатов потоков
    private static int totalEven = 0;
    private static int totalOdd = 0;

    public extendsThread(int start, int end, int N) {
        this.start = start;
        this.end = end;
        this.N = N;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            if (isConsecutiveDigits(i, N)) {
                if (i % 2 == 0) {
                    evenCount++;
                } else {
                    oddCount++;
                }
            }
        }

        // Синхронизируем доступ к общим переменным
        synchronized (OneThread.class) {
            totalEven += evenCount;
            totalOdd += oddCount;
        }
    }

    private static boolean isConsecutiveDigits(int number, int N) {
        int[] digits = new int[N];
        int tempNumber = number;

        for (int i = N - 1; i >= 0; i--) {
            digits[i] = tempNumber % 10;
            tempNumber /= 10;
        }

        for (int i = 0; i < N - 1; i++) {
            if (Math.abs(digits[i] - digits[i + 1]) != 1) {
                return false;
            }
        }
        return true;
    }

    public static int getTotalEven() {
        return totalEven;
    }

    public static int getTotalOdd() {
        return totalOdd;
    }
}
