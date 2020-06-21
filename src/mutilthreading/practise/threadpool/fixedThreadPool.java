package mutilthreading.practise.threadpool;



import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class fixedThreadPool {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(5);
		for (int i=0; i<6; i++) {
			service.execute(() -> {
				try {
					TimeUnit.MILLISECONDS.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
			});
		}
		System.out.println(service);
		
		
		service.shutdown();
		System.out.println(service.isTerminated());     //false。虽然已经shutdown了，但是任务没完成，在正在关闭状态中
		System.out.println(service.isShutdown());       //true，虽然实际没有关闭，但是已经知道要关闭了，正在关闭中
		System.out.println(service);
		
		TimeUnit.SECONDS.sleep(5);
		System.out.println(service.isTerminated());     //true，任务都执行完了
		System.out.println(service.isShutdown());       //true，已经关闭了
		System.out.println(service);
		
		}
	}
