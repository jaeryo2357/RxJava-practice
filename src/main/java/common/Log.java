package common;

public class Log {
    public static void it(Object obj) {
        long time = System.currentTimeMillis() - CommonUtils.startTime;
        System.out.println(Thread.currentThread().getName() + " | " + time + " | " + "value = " + obj);

    }

}
