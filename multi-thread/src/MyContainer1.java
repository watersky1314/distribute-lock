import java.util.LinkedList;

/**
 * 写一个固定容量同步容器，拥有put和get方法，以及getCount方法，能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * @param <T>
 */
public class MyContainer1<T> {
    private final LinkedList<T> list = new LinkedList<>();
    private final int MAX = 10;

    public int getCount() {
        return list.size();
    }

    // 生产者
    public synchronized void put(T t) {
        try {
            while (list.size() == MAX) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list.add(t);
        this.notifyAll();
    }

    // 消费者
    public synchronized T get() {
        try {
            while (list.size() == 0) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        T t = list.removeFirst();
        this.notifyAll();
        return t;
    }

    public static void main(String[] args) {
        MyContainer1<String> c = new MyContainer1<>();

        while(true) {
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
