package mutilthreading.practise.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelComputing {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		long start = System.currentTimeMillis();
		List<Integer> results = getPrime(1, 200000);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		
		
		final int cpuCoreNum = 8;
		
		ExecutorService service = Executors.newFixedThreadPool(cpuCoreNum);
		
		myTask t1 = new myTask(1, 50000);
		myTask t2 = new myTask(50000, 80000);
		myTask t3 = new myTask(80000, 110000);
		myTask t4 = new myTask(110000, 130000);
		myTask t5 = new myTask(130000, 160000);
		myTask t6 = new myTask(160000, 170000);
		myTask t7 = new myTask(170000, 190000);
		myTask t8 = new myTask(190000, 200000);
		
		Future<List<Integer>> f1 = service.submit(t1);
		Future<List<Integer>> f2 = service.submit(t2);
		Future<List<Integer>> f3 = service.submit(t3);
		Future<List<Integer>> f4 = service.submit(t4);
		Future<List<Integer>> f5 = service.submit(t5);
		Future<List<Integer>> f6 = service.submit(t6);
		Future<List<Integer>> f7 = service.submit(t7);
		Future<List<Integer>> f8 = service.submit(t8);
		
		start = System.currentTimeMillis();
		f1.get();
		f2.get();
		f3.get();
		f4.get();
		f5.get();
		f6.get();
		f7.get();
		f8.get();
		end = System.currentTimeMillis();
		System.out.println(end-start);
		
		
	
		
	}
	
	
	static boolean isPrime(int num) {
		for (int i=2; i<=num/2; i++) {
			if (num%i==0) return false;
		}return true;
	}
	
	
	static List<Integer> getPrime(int start, int end){
		List<Integer> results = new ArrayList<>();
		for (int i=start; i<=end; i++) {
			if (isPrime(i)) results.add(i);
		}
		return results;
	}
	
	
	static class myTask implements Callable<List<Integer>>{

		int startPos, endPos;
		
		public myTask(int s, int e) {
			this.startPos = s;
			this.endPos = e;
		}
		@Override
		public List<Integer> call() throws Exception {
			List<Integer> r = getPrime(startPos, endPos);
			return r;
		}

	}
}
