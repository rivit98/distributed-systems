package common;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ThreadComposite implements Runnable {
    private final List<Thread> threadList;

    public ThreadComposite(Thread... threads) {
        threadList = Arrays.stream(threads).collect(Collectors.toList());
    }

    @Override
    public void run() {
        threadList.forEach(Thread::start);
        threadList.forEach(this::join);
    }

    private void join(Thread t) {
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
