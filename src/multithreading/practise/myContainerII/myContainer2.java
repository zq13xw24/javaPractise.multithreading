package multithreading.practise.myContainerII;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用lock和Condition来实现，Condition可以更加精确的指定那些线程被唤醒
 * @author 60480
 *
 */


public class myContainer2<T> {
	final private LinkedList<T> lists = new LinkedList<>();
	final private int MAX = 10;
	private int count = 0;

	private Lock lock = new ReentrantLock();
	private Condition producer = lock.newCondition();
	private Condition consumer = lock.newCondition();

	public void put(T t) {
		try {
			lock.lock();
			while (lists.size() == MAX) {
				producer.await();
			}

			lists.add(t);
			++count;
			consumer.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public T get() {
		T t = null;
		try {
			lock.lock();
			while (lists.size() == 0) {
				consumer.await();
			}
			t = lists.removeFirst();
			count--;
			producer.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return t;
	}

	public static void main(String[] args) {
		myContainer2<String> c = new myContainer2<>();
		// 启动消费者进程
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				for (int j = 0; j < 5; j++)
					System.out.println(c.get());
			}, "c" + i).start();
		}

		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 启动生产者线程
		for (int i = 0; i < 2; i++) {
			new Thread(() -> {
				for (int j = 0; j < 25; j++)
					c.put(Thread.currentThread().getName() + " " + j);
			}, "p" + i).start();
		}

	}
}
