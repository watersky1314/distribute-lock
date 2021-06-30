public class ThreadPrintTest1 {

    private final static int[] nums = new int[]
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    private final static char[] chars = new char[]
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'G', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static void main(String[] args) {
        final Object o = new Object();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < nums.length; i++) {
                synchronized (o) {
                    System.out.println(Thread.currentThread().getName() + ":" + nums[i]);
                    o.notify();
                    try {
                        if (i < nums.length - 1)
                            o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < chars.length; i++) {
                synchronized (o) {
                    System.out.println(Thread.currentThread().getName() + ":" + chars[i]);
                    o.notify();
                    try {
                        if (i < nums.length - 1)
                            o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t2");

        t1.start();
        t2.start();

    }

}
