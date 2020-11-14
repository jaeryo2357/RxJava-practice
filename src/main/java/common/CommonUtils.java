package common;

public class CommonUtils {
    public static long startTime;

    public static void exampleStart() {
        startTime = System.currentTimeMillis();
    }

    public static void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getShape(String obj) {
        if (obj == null || obj.equals("")) return "NO-SHAPE";
        if (obj.endsWith("-H")) return "HEXAGON";
        if (obj.endsWith("-O")) return "OCTAGON";
        if (obj.endsWith("-R")) return  "RECTANGLE";
        if (obj.endsWith("-T")) return "TRIANGLE";
        if (obj.endsWith("ㅁ")) return "DIAMOND";
        return "BALL";
    }
}
