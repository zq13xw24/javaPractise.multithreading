package multithreading.practise;

/**
 * ʹ��wait��notify������wait���ͷ�������notify�����ͷ���
 * ��Ҫע����ǣ��������ַ���������Ҫ��֤t2��ִ�У�Ҳ������t2�ȼ����ſ��ԡ�
 * ���ǱȽϷ�������Ϊʹ��wait��notify����Ҫ����
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
				System.out.println("t2����");
				if(c.size() != 5) {
					try {
						lock.wait();
					} catch(InterruptedException e) {
						e.printStackTrace();
			}
		}
					System.out.println("t2����");
					lock.notify();   //t2ִ����Ϻ���֪ͨt1ִ��
			}
		}, "t2").start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		}catch(InterruptedException e1) {
			e1.printStackTrace();
		}
		
		new Thread(() ->{
			System.out.println("t1 ����");
			synchronized(lock) {
				for (int i=0; i<10; i++) {
					c.add(new Object());
					System.out.println("add " + i);
					
					if(c.size() == 5) {
						lock.notify();
						try {
							lock.wait();   // ��Ϊnotify�����ͷ���������������notify��t2Ҳ�������У���Ϊ����ס�ˣ���������t1�ͷ�
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
