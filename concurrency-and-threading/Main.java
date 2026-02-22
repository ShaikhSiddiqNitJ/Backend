//class MyThread extends Thread{
//   public void  run()
//    {
//        for(int i=0;i<5;i++)
//        {
//            System.out.println("sid");
//        }
//    }
//}

// Pehle se bani hui Parent class
class BaseService {
    void log(String message) {
        System.out.println("[LOG]: " + message);
    }
}

// 1. Parent class ko extend kiya
// 2. Runnable ko implement kiya (Concurrency ke liye)
class EmailTask extends BaseService implements Runnable {
    private String email;

    public EmailTask(String email) {
        this.email = email;
    }

    @Override
    public void run() {
        // Parent class ka method use kar rahe hain
        log("Sending email to: " + email);

        try {
            Thread.sleep(2000); // Maan lo email bhejne mein time lag raha hai
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log("Email sent successfully to: " + email);
    }
}

public class Main {
    public static void main(String[] args) {
        EmailTask task1 = new EmailTask("user@example.com");

        // Task ko Thread mein daal kar start kiya
        Thread t1 = new Thread(task1);
        t1.start();

        System.out.println("Main thread is free to do other work...");
    }
}