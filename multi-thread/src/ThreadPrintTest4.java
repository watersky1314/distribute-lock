import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPrintTest4 {

    private final static int[] nums = new int[]
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    private final static char[] chars = new char[]
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'G', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);

        Thread t1 = new Thread(() -> {
            try {
                for (int num : nums) {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + ":" + num);
                    semaphore.release();
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "t1");

        Thread t2 = new Thread(() -> {
            try {
                for (char aChar : chars) {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + ":" + aChar);
                    semaphore.release();
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2");

        t1.start();
        t2.start();

    }

}
