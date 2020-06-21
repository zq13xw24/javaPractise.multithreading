package multithreading.practise;

/**
 * ʹ��latch�����ţ�����wait��notify������֪ͨ
 * �ô���ͨ�ŷ�ʽ�򵥣�ͬʱ����ָ���ȴ�ʱ��
 * ʹ��await��coutdown��������wait��notify
 * CountDownLatch���漰��������count��ֵΪ��ʱ��ǰ�̼߳�������
 * �����漰ͬ����ֻ���漰����֮��ͨ�ŵ�ʱ��ʹ��synchronized+wait/notify���Ե�̫����
 * ��ʱӦ�ÿ���countdownlatch/cyclicbarrier/semaphore
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
			System.out.println("t2����");
			if(c.size()!=5) {
				try {
					latch.await();
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			System.out.println("t2����");
		}, "t2").start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		new Thread(() -> {
			System.out.println("t1����");
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
