package multithreading.practise.myContainerII;

/**
 * 写一个固定容量同步容器，拥有put和get方法，以及getCount方法
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * 
 * 使用wait和notify/notifyAll来实现
 * 
 * @author 60480
 *
 */


import java.util.*;
import java.util.concurrent.TimeUnit;

public class myContainer1<T> {
	
	final private LinkedList<T> lists = new LinkedList<>();
	final private int MAX = 10;    //最多10个元素
	private int count = 0;
	
	
	public synchronized void put(T t) {
		//记得wait 99.9%都是和while一起使用，而不是if
		while(lists.size() == MAX) {     //这里用while不用if是一个技巧。因为while可以保证被唤醒后再去比较一次，if的话就只会比较一次。
			try {                        //当一个线程在这里wait()之后，锁被释放掉。如果是If的话，当他抢到锁之后，它只会接着执行下面的语句。
				this.wait();             //如果在他抢到锁之前，别的线程抢到锁往里面扔了一个，便又回到了max状态，等它抢到锁，不会去判断是否为max
			} catch (InterruptedException e) {    //依旧执行了add操作，便会出现问题。如果是while的话就会回去再检查一次
				e.printStackTrace();
			}
		}
		
		lists.add(t);
		++count;
		//通知消费者进行消费
		this.notifyAll();      //这里不是使用notify的原因是因为叫醒一个如果又是一个消费者线程的话，那个线程就会在上面wait，程序就卡死了。
	}                          //推荐永远使用notifyAll
	
	public synchronized T get() {
		T t = null;
		while(lists.size() == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		t = lists.removeFirst();
		count --;
		//通知生产者进行生产。
		this.notifyAll();    
		return t;
	}
	
	public static void main(String[] args) {
		myContainer1<String> c = new myContainer1<>();
		//启动消费者线程
		for(int i=0; i<10; i++) {
			new Thread(() ->{
				for(int j=0; j<5; j++) System.out.println(c.get());
			}, "c"+i).start();
		}
		try {
			TimeUnit.SECONDS.sleep(2);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		//启动生产者线程
		for(int i=0; i<2; i++) {
			new Thread(() ->{
				for(int j=0; j<25; j++) c.put(Thread.currentThread().getName() + " " + j);
			}, "p" + i).start();
		}
		
	}

}
