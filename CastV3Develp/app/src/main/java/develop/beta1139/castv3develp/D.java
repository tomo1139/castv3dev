package develop.beta1139.castv3develp;

import android.util.Log;

public class D {

    public static final String TAG = "dbg";

    public static void sleep(long milliSeconds) {
        D.p("sleep start");
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        D.p("sleep end");
    }

    public static void p(Object arg1) {
        StackTraceElement[] e = new Throwable().getStackTrace();
        String[] classNames = e[1].getClassName().split("\\.");
        String className = classNames[classNames.length-1];
        String classNameAndMethodName = className + " " + e[1].getMethodName() + "() " + "line:" + e[1].getLineNumber();
        Log.e(TAG, classNameAndMethodName + " >>> " + arg1);
    }

    public static void printStackTrace() {
        Log.e(TAG, "\n========== printStackTrace ==========");
        StackTraceElement[] e = new Throwable().getStackTrace();
        String s;
        for( int i=e.length-1; i>0; i-- ) {
            String[] classNames = e[i].getClassName().split("\\.");
            String className = classNames[classNames.length-1];

            s = "[" + e[i].getFileName() + " l." + e[i].getLineNumber() + "] "
                + e[i].getClassName() + " "
                + e[i].getMethodName() + "()";
            Log.e(TAG, s);
        }
        Log.e(TAG, "=====================================\n\n");
    }

    private static long time = 0;
    public static void onTimeStamp() {
        long t = System.currentTimeMillis();
        Log.e(TAG, t + " : " + (time == 0 ? 0 : t - time));
        time = t;
    }
}
