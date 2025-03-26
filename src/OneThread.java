public class OneThread {
    private int evenCount = 0;
    private int oddCount = 0;
    private final int start;
    private final int end;
    private final int N;

    OneThread(int start, int end, int N) {
        this.start = start;
        this.end = end;
        this.N = N;
    }

    public String executeOneThread() {
        for (int i = start; i <= end; i++) {
            if (isConsecutiveDigits(i, N)) {
                if (i % 2 == 0) {
                    evenCount++;
                } else {
                    oddCount++;
                }
            }
        }
        return "For single-threaded program with N = " + N +  " Number of even: " + evenCount + " Number of odd: " + oddCount;
    };

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
}
