import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ThreadPrintTest2 {

    private final static int[] nums = new int[]
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    private final static char[] chars = new char[]
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'G', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static Thread t1 = null;
    private static Thread t2 = null;

    public static void main(String[] args) {

        t1 = new Thread(() -> {
            for (int i = 0; i < nums.length; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + nums[i]);
                LockSupport.unpark(t2);
                if (i < nums.length - 1){
                    LockSupport.park();
                }
            }
        }, "t1");

        t2 = new Thread(() -> {
            for (int i=0;i< chars.length;i++) {
                System.out.println(Thread.currentThread().getName() + ":" + chars[i]);
                LockSupport.unpark(t1);
                if(i< chars.length-1){
                    LockSupport.park();
                }
            }
        }, "t2");

        t1.start();
        t2.start();

    }

}
