/**
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * 这样给lists添加volatile之后，t2能够接到通知，但是这样的话t2写死循环很浪费CPU
 */

package multithreading.practise;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class myContainer1 {
	
	//添加volatile使得t2能够得到通知
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
			System.out.println("t2 结束");
		}, "t2").start();
}
}
