package multithreading.practise;

/**
 * 使用wait和notify做到，wait会释放锁，而notify不会释放锁
 * 需要注意的是，运用这种方法，必须要保证t2先执行，也就是让t2先监听才可以。
 * 还是比较繁琐，因为使用wait和notify必须要加锁
 * @author 60480
 * 
 */

import java.util.*;
import java.util.concurrent.TimeUnit;

public class myContainer2 {
	
	volatile List lists = new ArrayList();
	
	public void add(Object o) {
		lists.add(o);
	}
	
	public int size() {
		return lists.size();
	}

	public static void main(String[] args) {
		
		myContainer2 c = new myContainer2();
		
		final Object lock = new Object();
		
		new Thread(() ->{
			synchronized(lock) {
				System.out.println("t2启动");
				if(c.size() != 5) {
					try {
						lock.wait();
					} catch(InterruptedException e) {
						e.printStackTrace();
			}
		}
					System.out.println("t2结束");
					lock.notify();   //t2执行完毕后再通知t1执行
			}
		}, "t2").start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		}catch(InterruptedException e1) {
			e1.printStackTrace();
		}
		
		new Thread(() ->{
			System.out.println("t1 启动");
			synchronized(lock) {
				for (int i=0; i<10; i++) {
					c.add(new Object());
					System.out.println("add " + i);
					
					if(c.size() == 5) {
						lock.notify();
						try {
							lock.wait();   // 因为notify不会释放锁，单纯在这里notify，t2也不能运行，因为被锁住了，所以这里t1释放
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					try {
						TimeUnit.SECONDS.sleep(1);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		}, "t1").start();
		
		
	}

}
