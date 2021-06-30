import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    volatile List<Object> list = new ArrayList<>();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    static Thread t1 = null;
    static Thread t2 = null;

    public static void main(String[] args) {
        LockSupportTest test = new LockSupportTest();

        t2 = new Thread(() -> {
            System.out.println("t2启动。。。");
            if (test.size() != 5) {
                LockSupport.park();
            }
            System.out.println("t2结束。。。");
            LockSupport.unpark(t1);
        }, "t2");

//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        t1 = new Thread(() -> {
            System.out.println("t1启动。。。");
            for (int i = 0; i < 10; i++) {
                test.add(new Object());
                System.out.println("list add:" + i);
                if (test.size() == 5) {
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
            System.out.println("t1结束。。。");
        }, "t1");

        t2.start();
        t1.start();
    }

}
