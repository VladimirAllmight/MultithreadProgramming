public class implementsRunnable implements Runnable {

    @Override
    public void run() {
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
}
