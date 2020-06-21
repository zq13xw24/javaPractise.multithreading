package mutilthreading.practise.threadpool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class workStealingPool {

	public static void main(String[] args) throws IOException {
		ExecutorService service = Executors.newWorkStealingPool();
//		ExecutorService service = Executors.newFixedThreadPool(5); // ��ָ��n=8ʱ��Ч����workstealingЧ��һ�����������С��8�Ļ���ʱ���Ͼͻ������𡣿��Կ���woekstealing�Ǹ����ġ�
		System.out.println(Runtime.getRuntime().availableProcessors());    // 8��8��
		
		service.execute(new R(1000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		
		//���ڲ������Ǿ���(daemon)�̣߳��ػ��̡߳���̨�̣߳������̲߳������Ļ������������
//		System.in.read();
	}
	
	
	static class R implements Runnable{
		 int time;
		 
		 public R(int t) {
			 this.time = t;
		 }
		@Override
		public void run() {
			try {
				TimeUnit.MILLISECONDS.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(time + " " + Thread.currentThread().getName());
			
		}
		
	}

}
