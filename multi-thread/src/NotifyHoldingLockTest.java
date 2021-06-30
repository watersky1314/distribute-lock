import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * volatile一定尽量去修饰基本类型变量值，不要去修饰引用值。因为对于volatile修饰的引用类型，这个引用对象指向的是另外一个new出来的对象，
 * 如果这个对象里边的成员变量的值改变了，是无法观察到的
 */
public class NotifyHoldingLockTest {
    volatile List<Object> list = new ArrayList<>();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        NotifyHoldingLockTest test = new NotifyHoldingLockTest();
        final Object lock = new Object();

        new Thread(() -> {
            System.out.println("t2启动。。。");
            synchronized (lock) {
                if (test.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2结束。。。");
                lock.notify();
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t1启动。。。");
                for (int i = 0; i < 10; i++) {
                    test.add(new Object());
                    System.out.println("list add:" + i);
                    if(test.size()==5) {
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t1结束。。。");
            }
        }, "t1").start();
    }


}
