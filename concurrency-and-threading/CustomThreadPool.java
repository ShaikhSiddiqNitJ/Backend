//Implement a Fixed-Size Thread Pool from scratch that fulfills the following requirements:
//
//Task Acceptance: It should accept Runnable tasks from multiple producer threads.
//
//Thread Reuse: It must maintain a constant number of worker threads that stay alive and execute tasks from a central queue.
//
//Shutdown Support: It should provide a mechanism to stop accepting new tasks and gracefully shut down after completing pending tasks.
//
//Blocking Backpressure: If the internal task queue is full, the producer thread must wait (block) before adding more tasks."

import java.util.*;

public class CustomThreadPool {

    private final int capacity;
    private final Queue<Runnable> queue = new LinkedList<>();
    private final List<Worker> workers = new ArrayList<>();
    private boolean shutdown = false;

    public CustomThreadPool(int size, int capacity) {
        this.capacity = capacity;
        for (int i = 0; i < size; i++) {
            Worker w = new Worker();
            workers.add(w);
            w.start();
        }
    }

    public synchronized void submit(Runnable task) throws InterruptedException {
        while (queue.size() == capacity && !shutdown) {
            wait();
        }

        if (shutdown)
            throw new IllegalStateException("ThreadPool shutdown");

        queue.add(task);
        notifyAll();
    }

    private synchronized Runnable getTask() throws InterruptedException {
        while (queue.isEmpty() && !shutdown) {
            wait();
        }

        if (queue.isEmpty())
            return null;

        Runnable task = queue.poll();
        notifyAll();
        return task;
    }

    public synchronized void shutdown() {
        shutdown = true;
        notifyAll();
    }

    private class Worker extends Thread {
        public void run() {
            while (true) {
                try {
                    Runnable task = getTask();
                    if (task == null)
                        return;

                    task.run();

                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    // testing
    public static void main(String[] args) throws Exception {
        CustomThreadPool pool = new CustomThreadPool(3, 5);

        for (int i = 1; i <= 10; i++) {
            int num = i;
            pool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " running task " + num);
                try { Thread.sleep(1000); } catch (Exception e) {}
            });
        }

        Thread.sleep(5000);
        pool.shutdown();
    }
}