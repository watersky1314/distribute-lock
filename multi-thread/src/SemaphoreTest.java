import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 *    void acquire():从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被中断。
 * 　　void release():释放一个许可，将其返回给信号量。
 * 　　int availablePermits():返回此信号量中当前可用的许可数。
 * 　　boolean hasQueuedThreads():查询是否有线程正在等待获取。
 */
public class SemaphoreTest {
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
        SemaphoreTest test = new SemaphoreTest();
        Semaphore semaphore = new Semaphore(1);

        t2 = new Thread(() -> {
            System.out.println("t2启动。。。");
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (test.size() == 5) {
                System.out.println("t2结束。。。");
                semaphore.release();
            }
        }, "t2");

//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        t1 = new Thread(() -> {
            System.out.println("t1启动。。。");
            try {
                semaphore.acquire();
                System.out.println(semaphore.availablePermits() + ":"+ semaphore.hasQueuedThreads());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 10; i++) {
                test.add(new Object());
                System.out.println("list add:" + i);
                if (test.size() == 5) {
                    semaphore.release();
//                    System.out.println(semaphore.availablePermits() + ":"+ semaphore.hasQueuedThreads());
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
            System.out.println("t1结束。。。");
        }, "t1");

        t1.start();
        t2.start();
    }

}
