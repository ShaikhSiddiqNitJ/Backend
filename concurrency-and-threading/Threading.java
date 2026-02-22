class MyThread implements Runnable {
    public void run() {
        for(int i=0; i<5; i++) {
            System.out.println("sid");
        }
    }
}

class Threading {
    public static void main(String[] args) {
        System.out.println("Hello World siddiq bro");
        MyThread mt = new MyThread();
        Thread t = new Thread(mt);
        t.start();
    }
}
