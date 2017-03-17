import java.util.*;
import java.util.concurrent.Semaphore;

public class BlockingQueue<T> {

	private Queue<T> queue;
	private Semaphore mutex;
	private Semaphore empty;
	private Semaphore filled;
	
	public BlockingQueue(int size) {
		queue = new LinkedList<>();
		mutex = new Semaphore(1, true);
		empty = new Semaphore(size);
		filled = new Semaphore(0, true);
	}

	public void enqueue(T t) throws InterruptedException {
		empty.acquire();
		mutex.acquire();
		queue.offer(t);
		mutex.release();
		filled.release();
	}

	public T dequeue() throws InterruptedException {
		filled.acquire();
		mutex.acquire();
		T result = queue.poll();
		mutex.release();
		empty.release();

		return result;
	}

	public static void main(String args[]) throws InterruptedException {
		BlockingQueue<Integer> queue = new BlockingQueue<>(100);
		Runnable r = new Runnable() {
			public void run() {
				for (int i = 0; i < 200; i++) {
					try {
						int n = queue.dequeue();
						System.out.println(n + " Removed");
						Thread.sleep(500);
					} catch (Exception e) {}
				}
			}

		};
		Thread t = new Thread(r);
		t.start();
		for (int i = 0; i < 200; i++) {
			System.out.println("Adding " + i);
			queue.enqueue(i);
		}
	}
}