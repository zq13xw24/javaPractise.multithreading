package multithreading.practise.myContainerII;

/**
 * дһ���̶�����ͬ��������ӵ��put��get�������Լ�getCount����
 * �ܹ�֧��2���������߳��Լ�10���������̵߳���������
 * 
 * ʹ��wait��notify/notifyAll��ʵ��
 * 
 * @author 60480
 *
 */


import java.util.*;
import java.util.concurrent.TimeUnit;

public class myContainer1<T> {
	
	final private LinkedList<T> lists = new LinkedList<>();
	final private int MAX = 10;    //���10��Ԫ��
	private int count = 0;
	
	
	public synchronized void put(T t) {
		//�ǵ�wait 99.9%���Ǻ�whileһ��ʹ�ã�������if
		while(lists.size() == MAX) {     //������while����if��һ�����ɡ���Ϊwhile���Ա�֤�����Ѻ���ȥ�Ƚ�һ�Σ�if�Ļ���ֻ��Ƚ�һ�Ρ�
			try {                        //��һ���߳�������wait()֮�������ͷŵ��������If�Ļ�������������֮����ֻ�����ִ���������䡣
				this.wait();             //�������������֮ǰ������߳�����������������һ�������ֻص���max״̬������������������ȥ�ж��Ƿ�Ϊmax
			} catch (InterruptedException e) {    //����ִ����add���������������⡣�����while�Ļ��ͻ��ȥ�ټ��һ��
				e.printStackTrace();
			}
		}
		
		lists.add(t);
		++count;
		//֪ͨ�����߽�������
		this.notifyAll();      //���ﲻ��ʹ��notify��ԭ������Ϊ����һ���������һ���������̵߳Ļ����Ǹ��߳̾ͻ�������wait������Ϳ����ˡ�
	}                          //�Ƽ���Զʹ��notifyAll
	
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
		//֪ͨ�����߽���������
		this.notifyAll();    
		return t;
	}
	
	public static void main(String[] args) {
		myContainer1<String> c = new myContainer1<>();
		//�����������߳�
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
		//�����������߳�
		for(int i=0; i<2; i++) {
			new Thread(() ->{
				for(int j=0; j<25; j++) c.put(Thread.currentThread().getName() + " " + j);
			}, "p" + i).start();
		}
		
	}

}
