/**
 * ʵ��һ���������ṩ����������add��size
 * д�����̣߳��߳�1���10��Ԫ�ص������У��߳�2ʵ�ּ��Ԫ�صĸ�������������5��ʱ���߳�2������ʾ������
 * ������lists���volatile֮��t2�ܹ��ӵ�֪ͨ�����������Ļ�t2д��ѭ�����˷�CPU
 */

package multithreading.practise;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class myContainer1 {
	
	//���volatileʹ��t2�ܹ��õ�֪ͨ
	volatile List lists = new ArrayList();
	
	public void add(Object o) {
		lists.add(o);
	}
	
	public int size() {
		return lists.size();
	}
	
	public static void main(String[] args) {
		myContainer1 c = new myContainer1();
		
		new Thread(() ->{
			for(int i=0; i<10; i++) {
				c.add(new Object());
				System.out.println("add " + i);
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t1").start();
		
		new Thread(() ->{
			while(true) {
				if (c.size()==5) {
					break;
				}
			}
			System.out.println("t2 ����");
		}, "t2").start();
}
}
