import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;


public class Cores {



    public static void main(String[] args) {
       //observable();
        observable2();
//        flowable();
    }

    private static void observable() {
        Observable.range(1, 1000_000_000)
                .map(x -> {
                    System.out.println(Thread.currentThread().getName() + " sender Data:" + x);
                    return x;
                })
                .observeOn(Schedulers.computation())
                .subscribe(next -> {
                    try {
                        Thread.sleep(1000L);
                        System.out.println(Thread.currentThread().getName() + " onNext: " + next);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    System.out.println(Thread.currentThread().getName() + " onError: " + error.getMessage());
                }, () -> {
                    System.out.println(Thread.currentThread().getName() + " onComplete");
                });

        //observeOn에서 Worker 쓰레드로 선언안해줘서 sender가 1개만 됐음
        //3십몇만 까지 계속 sender함
    }

    private static void observable2() {
        String[] strings = new String[]{"딸기", "소스", "치킨"};
        Observable.fromArray(strings)
                .subscribe(next -> {
                    System.out.println(Thread.currentThread().getName() + " onNext: " + next);
                }, error -> {
                    System.out.println(Thread.currentThread().getName() + " onError: " + error.getMessage());
                }, () -> {
                    System.out.println(Thread.currentThread().getName() + " onComplete");
                });
    }

    private static void flowable() {
        Flowable.range(1, 1000_000_000)
                .delay(1000, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer()
                .map(x -> {
                    System.out.println(Thread.currentThread().getName() + " sender Data:" + x);
                    return x;
                })
                .observeOn(Schedulers.computation())
                .subscribe(next -> {
                    try {
                        Thread.sleep(1000L);
                        System.out.println(Thread.currentThread().getName() + " onNext: " + next);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    System.out.println(Thread.currentThread().getName() + " onError: " + error.getMessage());
                }, () -> {
                    System.out.println(Thread.currentThread().getName() + " onComplete");
                });

        //MainThread 가 종료되지 않아야 실행이 된다.
        try {
            Thread.sleep(80000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
