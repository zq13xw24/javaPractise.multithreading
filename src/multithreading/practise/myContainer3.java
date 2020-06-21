package multithreading.practise;

/**
 * 使用latch（门闩）代替wait，notify来进行通知
 * 好处是通信方式简单，同时可以指定等待时间
 * 使用await和coutdown方法代替wait和notify
 * CountDownLatch不涉及锁定，当count的值为零时当前线程继续运行
 * 当不涉及同步，只是涉及进程之间通信的时候，使用synchronized+wait/notify就显得太重了
 * 这时应该考虑countdownlatch/cyclicbarrier/semaphore
 * @author 60480
 *
 */

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class myContainer3 {
	
	volatile List lists = new ArrayList();
	
	public void add(Object o) {
		lists.add(o);
	}
	
	public int size() {
		return lists.size();
	}


	public static void main(String[] args) {
		
		myContainer3 c = new myContainer3();
		
		CountDownLatch latch = new CountDownLatch(1);
		
		new Thread(() ->{
			System.out.println("t2启动");
			if(c.size()!=5) {
				try {
					latch.await();
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			System.out.println("t2结束");
		}, "t2").start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		new Thread(() -> {
			System.out.println("t1启动");
			for (int i=0; i<10; i++) {
				c.add(new Object());
				System.out.println("add " + i);
				
				if(c.size()==5) {
					latch.countDown();
				}
				try {
					TimeUnit.SECONDS.sleep(1);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t1").start();

	}

}
