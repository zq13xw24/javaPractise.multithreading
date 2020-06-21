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
		System.out.println(service.isTerminated());     //false����Ȼ�Ѿ�shutdown�ˣ���������û��ɣ������ڹر�״̬��
		System.out.println(service.isShutdown());       //true����Ȼʵ��û�йرգ������Ѿ�֪��Ҫ�ر��ˣ����ڹر���
		System.out.println(service);
		
		TimeUnit.SECONDS.sleep(5);
		System.out.println(service.isTerminated());     //true������ִ������
		System.out.println(service.isShutdown());       //true���Ѿ��ر���
		System.out.println(service);
		
		}
	}
