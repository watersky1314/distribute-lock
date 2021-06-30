
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写一个固定容量同步容器，拥有put和get方法，以及getCount方法，能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 * @param <T>
 */
public class MyContainer2<T> {
    private final LinkedList<T> list = new LinkedList<>();
    private final int MAX = 10;
    private final Lock lock = new ReentrantLock();
    private final Condition producer = lock.newCondition();
    private final Condition consumer = lock.newCondition();

    public int getCount() {
        return list.size();
    }

    // 生产者
    public void put(T t) {
        try {
            lock.lock();
            while (list.size() == MAX) {
                producer.wait();
            }
            list.add(t);
            consumer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // 消费者
    public T get() {
        T t = null;
        try {
            lock.lock();
            while (list.size() == 0) {
                consumer.await();
            }
            t = list.removeFirst();
            producer.signal();
            producer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }  finally {
            lock.unlock();
        }
        return t;
    }

    public static void main(String[] args) {
        MyContainer2<String> c = new MyContainer2<>();

        while (true) {
            // 生产者线程
            for (int i = 0; i < 2; i++) {
                new Thread(() -> {
                    c.put(Thread.currentThread().getName());
                    System.out.println("生产者生产数据，此时容器中数量：" + c.getCount());
                }, "producer-" + i).start();
            }

            // 消费者线程
            for (int i = 0; i < 10; i++) {
                new Thread(() -> {
                    c.get();
                    System.out.println("消费者消费数据，此时容器中数量：" + c.getCount());
                }, "consumer-" + i).start();
            }
        }
    }
}
