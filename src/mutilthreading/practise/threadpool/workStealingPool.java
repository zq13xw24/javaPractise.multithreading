package mutilthreading.practise.threadpool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class workStealingPool {

	public static void main(String[] args) throws IOException {
		ExecutorService service = Executors.newWorkStealingPool();
//		ExecutorService service = Executors.newFixedThreadPool(5); // 当指定n=8时，效果和workstealing效果一样，但是如果小于8的话，时间上就会有区别。可以看作woekstealing是更灵活的。
		System.out.println(Runtime.getRuntime().availableProcessors());    // 8：8核
		
		service.execute(new R(1000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		
		//由于产生的是精灵(daemon)线程（守护线程、后台线程），主线程不阻塞的话看不到输出。
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
