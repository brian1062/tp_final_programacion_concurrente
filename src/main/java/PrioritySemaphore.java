import java.util.concurrent.Semaphore;

public class PrioritySemaphore {
    private final Semaphore mutex = new Semaphore(1); 
    private final Semaphore prioritySemaphore = new Semaphore(0); 
    private int priorityCount = 0; 

    
    public void acquire(boolean isPrioritized) throws InterruptedException {
        if (isPrioritized) {
            synchronized (this) {
                priorityCount++; 
            }
            prioritySemaphore.acquire(); 
        } else {
            mutex.acquire();
        }
    }

    
    public void release() {
        synchronized (this) {
            if (priorityCount > 0) {
                priorityCount--; 
                prioritySemaphore.release(); 
            }else{
                mutex.release();
            }
        }
    }
}
